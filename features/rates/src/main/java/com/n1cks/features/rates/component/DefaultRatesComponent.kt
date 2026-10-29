package com.n1cks.features.rates.component

import com.arkivanov.decompose.ComponentContext
import com.n1cks.core.domain.usecase.ObserveRatesUseCases
import com.n1cks.core.domain.usecase.RefreshRatesUseCase

class DefaultRatesComponent(
    componentContext: ComponentContext,
    private val observeRatesUseCases: ObserveRatesUseCases,
    private val refreshRatesUseCase: RefreshRatesUseCase
): RatesComponent, ComponentContext by componentContext {
}