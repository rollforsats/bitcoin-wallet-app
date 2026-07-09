package com.bitcoin.wallet.app.di

import com.bitcoin.wallet.kmp.WalletFactory
import com.bitcoin.wallet.kmp.domain.Network
import com.bitcoin.wallet.kmp.wallet.OnChainWallet
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@ContributesTo(AppScope::class)
interface AppGraph {

    companion object {

        // `Dispatchers.IO` is internal on K/Native; the `kotlinx.coroutines.IO`
        // import brings in the multiplatform extension getter.
        @Provides
        @SingleIn(AppScope::class)
        @IoDispatcher
        fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

        // Wire the DI-free wallet-core library into the graph. The library exposes
        // plain factory functions (no DI annotations); this is the single seam
        // where the app constructs the on-chain wallet. SIGNET for now.
        @Provides
        @SingleIn(AppScope::class)
        fun provideOnChainWallet(): OnChainWallet =
            WalletFactory.onChainWallet(network = Network.SIGNET)
    }
}
