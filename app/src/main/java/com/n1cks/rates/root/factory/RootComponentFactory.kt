package com.n1cks.rates.root.factory

import com.arkivanov.decompose.ComponentContext
import com.n1cks.rates.root.component.RootComponent

interface RootComponentFactory {
    fun create(context: ComponentContext): RootComponent
}