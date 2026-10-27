package com.n1cks.core.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RateDto(
    @SerialName("CharCode") val code: String,
    @SerialName("Name") val name: String,
    @SerialName("Nominal") val nominal: Int,
    @SerialName("Value") val value: Double,
    @SerialName("Previous") val previous: Double,
)