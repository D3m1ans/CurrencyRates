package com.n1cks.core.domain.model

import kotlin.time.Instant

data class CurrencyRate(
    val code: String,
    val name: String,
    val nominal: Int,
    val value: Double,
    val previous: Double,
) {
    val rubPerUnit: Double get() = value / nominal
    val change: Double get() = value - previous

    fun toRub(amount: Double): Double = amount * rubPerUnit
    fun fromRub(rub: Double): Double = rub / rubPerUnit
}

data class RatesSnapShot(
    val rates: List<CurrencyRate>,
    val publishedAd: Instant
)

