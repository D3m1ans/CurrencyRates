package com.n1cks.core.domain.usecase

import com.n1cks.core.domain.model.RatesSnapshot
import com.n1cks.core.domain.repository.RatesRepository
import kotlinx.coroutines.flow.Flow

class ObserveRatesUseCases(
    private val repository: RatesRepository
) {
    operator fun invoke(): Flow<RatesSnapshot?> = repository.observeRates()
}