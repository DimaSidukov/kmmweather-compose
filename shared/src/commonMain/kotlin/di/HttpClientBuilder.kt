package di

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

fun buildHttpClient() = HttpClient {
    install(ContentNegotiation) {
        json(Json{
            ignoreUnknownKeys = true
            useAlternativeNames = false
        })
    }
}