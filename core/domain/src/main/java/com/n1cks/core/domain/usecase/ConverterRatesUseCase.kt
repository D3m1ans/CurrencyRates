package com.n1cks.core.domain.usecase

import com.n1cks.core.domain.model.CurrencyRate


class ConvertRatesUseCase {
    operator fun invoke(amount: Double, rate: CurrencyRate?): Double {
        if (amount <= 0 || rate == null) return 0.0

        val pricePerUnit = rate.value / rate.nominal

        return amount * pricePerUnit
    }

    fun fromRubles(rublesAmount: Double, rate: CurrencyRate?): Double {
        if (rublesAmount <= 0 || rate == null) return 0.0

        val pricePerUnit = rate.value / rate.nominal
        if (pricePerUnit == 0.0) return 0.0

        return rublesAmount / pricePerUnit
    }
}