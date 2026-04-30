package com.suprogramuota_visata.api.data.repositories

import com.suprogramuota_visata.api.data.network.ApiConstants
import com.suprogramuota_visata.api.domain.models.LoginRequest
import com.suprogramuota_visata.api.domain.models.RegisterRequest
import com.suprogramuota_visata.api.domain.models.TokenResponse
import com.suprogramuota_visata.api.domain.repositories.AuthRepository
import com.suprogramuota_visata.api.domain.util.ApiResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

/**
 * Concrete implementation of the AuthRepository.
 * Handles making the actual HTTP requests to the backend server.
 */
class AuthRepositoryImpl(private val client: HttpClient) : AuthRepository {
    
    private var jwtToken: String? = null

    override suspend fun register(request: RegisterRequest): ApiResult<Unit> {
        return try {
            val response = client.post("${ApiConstants.BASE_URL}/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            if (response.status == HttpStatusCode.Created) {
                ApiResult.Success(Unit)
            } else {
                ApiResult.Error("Failed to register. Status: ${response.status}")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error", e)
        }
    }

    override suspend fun login(request: LoginRequest): ApiResult<TokenResponse> {
        return try {
            val response = client.post("${ApiConstants.BASE_URL}/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                val tokenResponse = response.body<TokenResponse>()
                jwtToken = tokenResponse.token // Save token in memory
                ApiResult.Success(tokenResponse)
            } else {
                ApiResult.Error("Login failed. Check credentials.")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error", e)
        }
    }

    override fun logout() {
        jwtToken = null
    }

    override fun getToken(): String? {
        return jwtToken
    }
}
