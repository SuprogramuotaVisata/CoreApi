package com.suprogramuota_visata.api.domain.repositories

import com.suprogramuota_visata.api.domain.models.TypeDTO
import com.suprogramuota_visata.api.domain.util.ApiResult

/**
 * Domain interface for Type operations.
 */
interface TypeRepository {
    suspend fun getAllByType(typeName: String): ApiResult<List<TypeDTO>>
    suspend fun getByIdAndType(id: Int, typeName: String): ApiResult<TypeDTO>
    suspend fun create(type: TypeDTO): ApiResult<TypeDTO>
    suspend fun update(type: TypeDTO): ApiResult<TypeDTO>
    suspend fun delete(id: Int, typeName: String): ApiResult<Unit>
}
