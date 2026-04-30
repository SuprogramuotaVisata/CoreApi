package com.suprogramuota_visata.api.data.network

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * Creates and configures the Ktor HttpClient.
 * Takes a token provider function so it can dynamically fetch the latest JWT token
 * from the AuthRepository when making requests to protected endpoints.
 */
fun createHttpClient(getToken: () -> String?): HttpClient {
    return HttpClient(CIO) {
        install(Logging) {
            level = LogLevel.ALL
        }
        
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true // Svarbu mobiliesiems klientams
            })
        }

        install(Auth) {
            bearer {
                loadTokens {
                    val token = getToken()
                    if (token != null) {
                        BearerTokens(token, "") // Refresh token nepalaikomas, bet reikalingas parametras
                    } else {
                        null
                    }
                }
            }
        }
    }
}

// Global API Constants
object ApiConstants {
    const val BASE_URL = "http://localhost:8081" // Turi būti konfigūruojama iš aplikacijos
}
