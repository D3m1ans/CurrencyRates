package com.n1cks.core.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "rates")
data class RatesEntity(
    @PrimaryKey val code: String,
    val name: String,
    val nominal: Int,
    val value: Double,
    val previous: Double,
    val publishedAtEpoch: Long,
)