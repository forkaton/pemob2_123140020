package com.forkaton.pemob2_123140020

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "pemob2_123140020",
    ) {
        App()
    }
}