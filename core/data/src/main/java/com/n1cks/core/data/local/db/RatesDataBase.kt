package com.n1cks.core.data.local.db

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.n1cks.core.data.local.dao.RatesDao
import com.n1cks.core.data.local.entity.RatesEntity

@Database(entities = [RatesEntity::class], version = 1)
abstract class RatesDatabase : RoomDatabase() {
    abstract fun ratesDao(): RatesDao
}

fun createRatesDatabase(context: Context): RatesDatabase =
    Room.databaseBuilder(
        context.applicationContext,
        RatesDatabase::class.java,
        "rates.db"
    ).build()