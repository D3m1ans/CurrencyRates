package com.n1cks.core.domain.repository

import com.n1cks.core.domain.model.RatesSnapshot
import kotlinx.coroutines.flow.Flow

interface RatesRepository {
    fun observeRates(): Flow<RatesSnapshot?>
    suspend fun refresh(): Result<Unit>
}