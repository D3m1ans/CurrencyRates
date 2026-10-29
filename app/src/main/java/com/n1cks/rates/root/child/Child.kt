package com.n1cks.rates.root.child

import com.n1cks.features.rates.component.RatesComponent

sealed class Child {
    data class RatesChild(val component: RatesComponent): Child()
}