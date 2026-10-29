package com.n1cks.rates.di

import com.n1cks.core.domain.repository.RatesRepository
import com.n1cks.core.domain.usecase.ObserveRatesUseCases
import com.n1cks.core.domain.usecase.RefreshRatesUseCase
import com.n1cks.rates.root.factory.RootComponentFactory
import com.n1cks.rates.root.factory.RootComponentFactoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideObserveRatesUseCase(repository: RatesRepository): ObserveRatesUseCases =
        ObserveRatesUseCases(repository)

    @Provides
    @Singleton
    fun provideRefreshRatesUseCase(repository: RatesRepository): RefreshRatesUseCase =
        RefreshRatesUseCase(repository)

    @Provides
    @Singleton
    fun provideRootComponentFactory(
        observeRatesUseCases: ObserveRatesUseCases,
        refreshRatesUseCase: RefreshRatesUseCase,
    ): RootComponentFactory = RootComponentFactoryImpl(
        observeRatesUseCases,
        refreshRatesUseCase,
    )
}