package com.n1cks.core.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.n1cks.core.data.local.dao.RatesDao
import com.n1cks.core.data.local.mapper.toDomain
import com.n1cks.core.data.local.mapper.toEntities
import com.n1cks.core.data.remote.cbr.CbrApi
import com.n1cks.core.data.remote.mapper.toDomain
import com.n1cks.core.domain.model.RatesSnapshot
import com.n1cks.core.domain.repository.RatesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.Instant

class RatesRepositoryImpl(
    private val api: CbrApi,
    private val dao: RatesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RatesRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun observeRates(): Flow<RatesSnapshot?> {
        return dao.observeRates().map { entities ->
            if (entities.isEmpty()) {
                null
            } else {
                val publishedAt = Instant.ofEpochMilli(entities.first().publishedAtEpoch)
                RatesSnapshot(
                    rates = entities.map { it.toDomain() },
                    publishedAt = publishedAt
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun refresh(): Result<Unit> = withContext(ioDispatcher) {
        runCatching {
            val response = api.getDailyRates()
            val snapshot = response.toDomain()
            val entities = snapshot.toEntities()

            dao.clearAll()
            dao.insertRates(entities)
        }
    }
}