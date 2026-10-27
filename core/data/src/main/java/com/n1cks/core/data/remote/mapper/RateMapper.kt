package com.n1cks.core.data.remote.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.n1cks.core.data.remote.dto.RateDto
import com.n1cks.core.data.remote.response.RatesResponse
import com.n1cks.core.domain.model.CurrencyRate
import com.n1cks.core.domain.model.RatesSnapshot
import java.time.OffsetDateTime

@RequiresApi(Build.VERSION_CODES.O)
fun RatesResponse.toDomain() = RatesSnapshot(
    rates = valute.values.map { it.toDomain() },
    publishedAt = OffsetDateTime.parse(date).toInstant(),
)

private fun RateDto.toDomain() = CurrencyRate(code, name, nominal, value, previous)