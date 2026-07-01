package com.bitcoin.wallet.app.wallet

import com.bitcoin.wallet.kmp.WalletFactory
import com.bitcoin.wallet.kmp.domain.Network
import com.bitcoin.wallet.kmp.domain.WalletResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * iOS (Kotlin/Native) counterpart of [DesktopWalletGenerationTest] — proves the
 * ACINQ secp256k1 native backend links and runs on the iOS simulator target.
 */
class IosWalletGenerationTest {

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
}
