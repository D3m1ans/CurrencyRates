package com.n1cks.rates.root.factory

import com.arkivanov.decompose.ComponentContext
import com.n1cks.core.domain.usecase.ObserveRatesUseCases
import com.n1cks.core.domain.usecase.RefreshRatesUseCase
import com.n1cks.rates.root.component.DefaultRootComponent
import com.n1cks.rates.root.component.RootComponent
import jakarta.inject.Inject

class RootComponentFactoryImpl @Inject constructor(
    private val observeRatesUseCases: ObserveRatesUseCases,
    private val refreshRatesUseCase: RefreshRatesUseCase,
) : RootComponentFactory {
    override fun create(context: ComponentContext): RootComponent {
        return DefaultRootComponent(
            context,
            observeRatesUseCases,
            refreshRatesUseCase
        )
    }
}