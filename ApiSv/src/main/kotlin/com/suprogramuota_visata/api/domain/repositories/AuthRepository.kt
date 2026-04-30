package com.suprogramuota_visata.api.domain.repositories

import com.suprogramuota_visata.api.domain.models.LoginRequest
import com.suprogramuota_visata.api.domain.models.RegisterRequest
import com.suprogramuota_visata.api.domain.models.TokenResponse
import com.suprogramuota_visata.api.domain.util.ApiResult

/**
 * Domain interface for Authentication.
 * ViewModels should depend on this interface, not the concrete implementation.
 * (Dependency Inversion Principle)
 */
interface AuthRepository {
    suspend fun register(request: RegisterRequest): ApiResult<Unit>
    suspend fun login(request: LoginRequest): ApiResult<TokenResponse>
    fun logout()
    fun getToken(): String?
}
