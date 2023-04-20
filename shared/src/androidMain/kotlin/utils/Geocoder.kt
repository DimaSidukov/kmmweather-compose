package utils

import android.os.Build
import com.example.data.local.Manager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.NullPointerException
import java.util.Locale

actual class Geocoder {

    actual companion object {

        private val geocoder = android.location.Geocoder(Manager.context!!, Locale.getDefault())

        @OptIn(ExperimentalCoroutinesApi::class)
        actual suspend fun encodeLocation(address: String): Pair<Double, Double> =
            suspendCancellableCoroutine { continuation ->
                geocoder.getFromLocationName(
                    address,
                    1
                ) {
                    continuation.resume(Pair(it[0].latitude, it[0].longitude), null)
                }
            }

        @OptIn(ExperimentalCoroutinesApi::class)
        actual suspend fun decodeLocation(latitude: Double, longitude: Double): String =
            suspendCancellableCoroutine { continuation ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(latitude, longitude, 1) {
                        try {
                            continuation.resume(it[0].locality, null)
                        } catch (e: NullPointerException) {
                            continuation.resume("", null)
                        }
                    }
                } else {
                    continuation.resume(
                        geocoder
                            .getFromLocation(latitude, longitude, 1)
                            ?.get(0)
                            ?.getAddressLine(0)
                            ?: "",
                        null
                    )
                }
            }
    }

}