package com.n1cks.core.domain.usecase

import com.n1cks.core.domain.repository.RatesRepository

class RefreshRatesUseCase(
    private val repository: RatesRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.refresh()
}