package com.bitcoin.wallet.app.di

import com.bitcoin.wallet.app.wallet.WalletSetupViewModel
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph

@DependencyGraph(AppScope::class)
interface DesktopAppGraph : AppGraph {
    val walletSetupViewModel: WalletSetupViewModel
}
