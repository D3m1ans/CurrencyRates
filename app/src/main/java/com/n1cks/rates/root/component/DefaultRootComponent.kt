package com.n1cks.rates.root.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.n1cks.core.domain.usecase.ObserveRatesUseCases
import com.n1cks.core.domain.usecase.RefreshRatesUseCase
import com.n1cks.features.rates.component.DefaultRatesComponent
import com.n1cks.rates.root.child.Child
import com.n1cks.rates.root.config.RootConfig

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val observeRatesUseCases: ObserveRatesUseCases,
    private val refreshRatesUseCase: RefreshRatesUseCase,
): RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<RootConfig>()

    override val stack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        initialConfiguration = RootConfig.Rates,
        serializer = RootConfig.serializer(),
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(config: RootConfig, context: ComponentContext): Child =
        when(config) {
            is RootConfig.Rates -> Child.RatesChild(
                DefaultRatesComponent(
                    context,
                    observeRatesUseCases,
                    refreshRatesUseCase
                )
            )
        }
}