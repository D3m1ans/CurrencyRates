package com.n1cks.core.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.n1cks.core.data.local.entity.RatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RatesDao {
    @Query("SELECT * FROM rates")
    fun observeRates(): Flow<List<RatesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(rates: List<RatesEntity>)

    @Query("DELETE FROM rates")
    suspend fun clearAll()

    @Query("SELECT publishedAtEpoch FROM rates LIMIT 1")
    suspend fun getLastPublishedAt(): Long?
}