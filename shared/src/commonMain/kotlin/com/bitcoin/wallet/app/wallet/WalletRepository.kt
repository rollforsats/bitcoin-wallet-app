package com.bitcoin.wallet.app.wallet

import com.bitcoin.wallet.app.di.IoDispatcher
import com.bitcoin.wallet.kmp.domain.WalletError
import com.bitcoin.wallet.kmp.domain.WalletResult
import com.bitcoin.wallet.kmp.wallet.OnChainWallet
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * App-facing result of creating a wallet. Carries only non-secret display data
 * plus an error message — deliberately NOT the library's [com.bitcoin.wallet.kmp.wallet.NewWallet]
 * (whose redacted `toString` we preserve by never logging it).
 */
sealed interface WalletCreateResult {
    data class Success(val mnemonicWords: List<String>, val address: String) : WalletCreateResult
    data class Failure(val message: String) : WalletCreateResult
}

/**
 * The app's seam over the DI-free wallet-core library. Wraps the synchronous,
 * CPU-bound [OnChainWallet] calls (entropy + key derivation) and moves them off
 * the caller's thread onto the IO dispatcher.
 *
 * Secret hygiene: the mnemonic words are read exactly once here and flow only
 * into UI state — never into a log.
 */
@Inject
@SingleIn(AppScope::class)
class WalletRepository(
    private val wallet: OnChainWallet,
    @param:IoDispatcher private val io: CoroutineDispatcher,
) {
    suspend fun generateWallet(): WalletCreateResult = withContext(io) {
        when (val result = wallet.create()) {
            is WalletResult.Success -> WalletCreateResult.Success(
                mnemonicWords = result.value.mnemonic.words,
                address = result.value.firstAddress.value,
            )

            is WalletResult.Failure -> WalletCreateResult.Failure(result.error.toMessage())
        }
    }
}

private fun WalletError.toMessage(): String = when (this) {
    is WalletError.Engine -> "Couldn't create your wallet. Please try again."
    is WalletError.InvalidInput -> "Invalid wallet input: $reason"
}
