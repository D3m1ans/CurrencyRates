package com.n1cks.core.data.di

import android.content.Context
import com.n1cks.core.data.local.dao.RatesDao
import com.n1cks.core.data.local.db.RatesDatabase
import com.n1cks.core.data.local.db.createRatesDatabase
import com.n1cks.core.data.remote.cbr.CbrApi
import com.n1cks.core.data.remote.cbr.createCbrApi
import com.n1cks.core.data.repository.RatesRepositoryImpl
import com.n1cks.core.domain.repository.RatesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideCbrApi(): CbrApi = createCbrApi()

    @Provides
    @Singleton
    fun provideRatesDataBase(@ApplicationContext context: Context): RatesDatabase =
        createRatesDatabase(context)

    @Provides
    @Singleton
    fun provideRatesDao(db: RatesDatabase): RatesDao = db.ratesDao()

    @Provides
    @Singleton
    fun provideRatesRepository(
        api: CbrApi,
        dao: RatesDao
    ) : RatesRepository = RatesRepositoryImpl(api, dao)
}