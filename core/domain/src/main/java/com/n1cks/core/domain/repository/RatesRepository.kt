package com.n1cks.core.domain.repository

import com.n1cks.core.domain.model.RatesSnapShot
import kotlinx.coroutines.flow.Flow

interface RatesRepository {
    fun observeRates(): Flow<RatesSnapShot?>
    suspend fun refresh(): Result<Unit>
}