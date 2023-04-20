package utils

expect class Geocoder {

    companion object {

        suspend fun encodeLocation(address: String): Pair<Double, Double>

        suspend fun decodeLocation(latitude: Double, longitude: Double): String

    }

}