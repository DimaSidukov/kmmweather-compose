package utils

import kotlinx.cinterop.useContents
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLGeocoder
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLPlacemark

actual class Geocoder {

    actual companion object {

        private val geocoder = CLGeocoder()

        @OptIn(ExperimentalCoroutinesApi::class)
        actual suspend fun encodeLocation(address: String): Pair<Double, Double> =
            suspendCancellableCoroutine { continuation ->
                geocoder.geocodeAddressString(address) { list, _ ->
                    (list?.first() as CLPlacemark).location?.coordinate?.useContents {
                        continuation.resume(
                            Pair(latitude, longitude),
                            null
                        )
                    }
                }
            }

        @OptIn(ExperimentalCoroutinesApi::class)
        actual suspend fun decodeLocation(latitude: Double, longitude: Double): String =
            suspendCancellableCoroutine { continuation ->
                geocoder.reverseGeocodeLocation(CLLocation(latitude, longitude)) { list, _ ->
                    (list?.first() as CLPlacemark).locality?.let { address ->
                        continuation.resume(address, null)
                    } ?: run {
                        continuation.resume("", null)
                    }
                }
            }
    }

}