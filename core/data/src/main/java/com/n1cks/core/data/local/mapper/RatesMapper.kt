package com.n1cks.core.data.local.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.n1cks.core.data.local.entity.RatesEntity
import com.n1cks.core.domain.model.CurrencyRate
import com.n1cks.core.domain.model.RatesSnapshot

fun RatesEntity.toDomain(): CurrencyRate {
    return CurrencyRate(
        code = code,
        name = name,
        nominal = nominal,
        value = value,
        previous = previous
    )
}

fun CurrencyRate.toEntity(publishedAtEpoch: Long) = RatesEntity(
    code = code,
    name = name,
    nominal = nominal,
    value = value,
    previous = previous,
    publishedAtEpoch = publishedAtEpoch
)

@RequiresApi(Build.VERSION_CODES.O)
fun RatesSnapshot.toEntities(): List<RatesEntity> {
    val publishedAtEpoch = publishedAt.toEpochMilli()
    return rates.map { rate ->
        RatesEntity(
            code = rate.code,
            name = rate.name,
            nominal = rate.nominal,
            value = rate.value,
            previous = rate.previous,
            publishedAtEpoch = publishedAtEpoch
        )
    }
}