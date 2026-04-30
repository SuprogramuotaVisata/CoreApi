package com.suprogramuota_visata.api.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class AttributeDTO(
    val id: Int? = null,
    val name: String,
    val attributeType: String,
    val validate: Boolean = false,
    val value: String
)

@Serializable
data class TypeDTO(
    val id: Int? = null,
    val name: String,
    val groupId: Int? = null,
    val enabled: Boolean,
    val type: String,
    val attributes: List<AttributeDTO> = emptyList()
)
