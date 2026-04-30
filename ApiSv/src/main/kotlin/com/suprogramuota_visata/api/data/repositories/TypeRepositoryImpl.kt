package com.suprogramuota_visata.api.data.repositories

import com.suprogramuota_visata.api.data.network.ApiConstants
import com.suprogramuota_visata.api.domain.models.TypeDTO
import com.suprogramuota_visata.api.domain.repositories.TypeRepository
import com.suprogramuota_visata.api.domain.util.ApiResult
import com.suprogramuota_visata.api.domain.util.safeApiCall
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

/**
 * Concrete implementation of the TypeRepository.
 * Handles making HTTP GET/POST/PUT/DELETE requests to the backend server.
 * All requests will automatically include the JWT token (if available) thanks to the Auth plugin in HttpClient.
 */
class TypeRepositoryImpl(private val client: HttpClient) : TypeRepository {

    override suspend fun getAllByType(typeName: String): ApiResult<List<TypeDTO>> {
        return safeApiCall {
            val response = client.get("${ApiConstants.BASE_URL}/types/$typeName")
            if (response.status == HttpStatusCode.OK) {
                response.body<List<TypeDTO>>()
            } else {
                throw Exception("Failed to fetch types. Status: ${response.status}")
            }
        }
    }

    override suspend fun getByIdAndType(id: Int, typeName: String): ApiResult<TypeDTO> {
        return safeApiCall {
            val response = client.get("${ApiConstants.BASE_URL}/types/$typeName/$id")
            if (response.status == HttpStatusCode.OK) {
                response.body<TypeDTO>()
            } else {
                throw Exception("Type not found or failed to fetch. Status: ${response.status}")
            }
        }
    }

    override suspend fun create(type: TypeDTO): ApiResult<TypeDTO> {
        return safeApiCall {
            val response = client.post("${ApiConstants.BASE_URL}/types") {
                contentType(ContentType.Application.Json)
                setBody(type)
            }
            if (response.status == HttpStatusCode.Created) {
                response.body<TypeDTO>()
            } else {
                throw Exception("Failed to create type. Status: ${response.status}")
            }
        }
    }

    override suspend fun update(type: TypeDTO): ApiResult<TypeDTO> {
        return safeApiCall {
            val response = client.put("${ApiConstants.BASE_URL}/types") {
                contentType(ContentType.Application.Json)
                setBody(type)
            }
            if (response.status == HttpStatusCode.OK) {
                response.body<TypeDTO>()
            } else {
                throw Exception("Failed to update type. Status: ${response.status}")
            }
        }
    }

    override suspend fun delete(id: Int, typeName: String): ApiResult<Unit> {
        return safeApiCall {
            val response = client.delete("${ApiConstants.BASE_URL}/types/$typeName/$id")
            if (response.status == HttpStatusCode.NoContent) {
                Unit
            } else {
                throw Exception("Failed to delete type. Status: ${response.status}")
            }
        }
    }
}
