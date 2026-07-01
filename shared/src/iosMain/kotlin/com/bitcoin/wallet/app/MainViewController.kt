package com.bitcoin.wallet.app

import androidx.compose.ui.window.ComposeUIViewController
import com.bitcoin.wallet.app.ui.theme.WalletTheme
import com.bitcoin.wallet.app.wallet.WalletSetupScreen

fun MainViewController() = ComposeUIViewController {
    WalletTheme {
        WalletSetupScreen(viewModel = IosBootstrap.graph.walletSetupViewModel)
    }
}
