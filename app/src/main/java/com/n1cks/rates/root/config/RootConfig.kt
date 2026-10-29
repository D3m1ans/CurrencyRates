package com.n1cks.rates.root.config

import kotlinx.serialization.Serializable

@Serializable
sealed class RootConfig {
    @Serializable object Rates: RootConfig()
}