package com.n1cks.core.data.remote.response

import com.n1cks.core.data.remote.dto.RateDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatesResponse(
    @SerialName("Date") val date: String,
    @SerialName("Valute") val valute: Map<String, RateDto>,
)