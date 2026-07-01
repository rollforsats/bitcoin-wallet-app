package com.bitcoin.wallet.app.wallet

import com.bitcoin.wallet.kmp.WalletFactory
import com.bitcoin.wallet.kmp.domain.Network
import com.bitcoin.wallet.kmp.domain.WalletResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Proves the wallet-core library's crypto path links and runs on the JVM/desktop
 * target — i.e. the ACINQ secp256k1 JNI native backend is on the runtime
 * classpath. This is the desktop-specific risk called out in the plan; if the
 * native lib were missing this throws UnsatisfiedLinkError instead of passing.
 */
class DesktopWalletGenerationTest {

    @Test
    fun generatesSignetWalletWithMnemonicAndAddress() {
        val wallet = WalletFactory.onChainWallet(network = Network.SIGNET)
        val result = wallet.create()

        assertTrue(result is WalletResult.Success, "expected wallet creation to succeed")
        val newWallet = result.value
        assertEquals(12, newWallet.mnemonic.words.size, "expected a 12-word mnemonic")
        assertTrue(
            newWallet.firstAddress.value.startsWith("tb1"),
            "expected a signet bech32 receive address, got ${newWallet.firstAddress.value}",
        )
    }

    /**
     * Reproduces the ViewModel's coroutine path: `viewModelScope.launch` resumes
     * on `Dispatchers.Main`, which on desktop/JVM comes from kotlinx-coroutines-swing.
     * Without that artifact this throws "Module with the Main dispatcher is missing";
     * with it, the block runs on the Swing event thread and completes.
     */
    @Test
    fun mainDispatcherIsAvailableOnDesktop() = runBlocking {
        val ran = withContext(Dispatchers.Main) { true }
        assertTrue(ran, "expected Dispatchers.Main to be available on desktop")
    }
}
