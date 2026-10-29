package com.n1cks.rates.root.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.n1cks.features.rates.ui.RatesScreen
import com.n1cks.rates.root.child.Child
import com.n1cks.rates.root.component.RootComponent

@Composable
fun RootContent(
    component: RootComponent
) {
    Children(
        stack = component.stack,
        animation = stackAnimation(slide())
    ) {
        when (val child = it.instance) {
            is Child.RatesChild -> {
                RatesScreen(
                    component = child.component
                )
            }
        }
    }
}