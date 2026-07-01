package com.bitcoin.wallet.app.desktop

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.bitcoin.wallet.app.DesktopBootstrap
import com.bitcoin.wallet.app.ui.theme.WalletTheme
import com.bitcoin.wallet.app.wallet.WalletSetupScreen

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Bitcoin Wallet",
        state = rememberWindowState(width = 420.dp, height = 760.dp),
    ) {
        WalletTheme {
            WalletSetupScreen(viewModel = DesktopBootstrap.graph.walletSetupViewModel)
        }
    }
}
