package com.suprogramuota_visata.api

import com.suprogramuota_visata.api.data.network.createHttpClient
import com.suprogramuota_visata.api.data.repositories.AuthRepositoryImpl
import com.suprogramuota_visata.api.data.repositories.TypeRepositoryImpl
import com.suprogramuota_visata.api.domain.repositories.AuthRepository
import com.suprogramuota_visata.api.domain.repositories.TypeRepository
import io.ktor.client.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

/**
 * The main entry point for the ApiSv library.
 * Android and Desktop applications should instantiate this class (or provide it via Dependency Injection, like Dagger Hilt or Koin).
 */
class ApiSvClient {

    // 1. Initializuojame HTTP klientą
    private val httpClient: HttpClient

    // 2. Publicuojame Domain lygio repozitorijas, kurias naudos programėlės ViewModel'iai
    val authRepository: AuthRepository
    val typeRepository: TypeRepository

    init {
        // AuthRepository sukuriamas pirmiausia, nes jo reikės HTTP klientui, kad ištrauktų JWT tokeną
        val tempAuthRepository = AuthRepositoryImpl(
            // Laikinas klientas tik Auth operacijoms, kol pagrindinis klientas nesukonfigūruotas
            HttpClient(CIO) {
                install(ContentNegotiation) {
                    json()
                }
            }
        )

        // Sukuriame pagrindinį HTTP klientą, kuris automatiškai prideda "Bearer" tokeną iš AuthRepository
        httpClient = createHttpClient { tempAuthRepository.getToken() }

        // Priskiriame tikrąsias repozitorijas, naudodami jau pilnai sukonfigūruotą pagrindinį klientą
        authRepository = AuthRepositoryImpl(httpClient)
        typeRepository = TypeRepositoryImpl(httpClient)
    }
}
