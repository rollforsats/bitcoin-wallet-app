package com.bitcoin.wallet.app.wallet

import androidx.lifecycle.viewModelScope
import com.bitcoin.wallet.app.mvi.MviViewModel
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.launch

@Inject
@SingleIn(AppScope::class)
class WalletSetupViewModel(
    private val repository: WalletRepository,
) : MviViewModel<WalletSetupState, WalletSetupAction, WalletSetupEvent>(
    initial = WalletSetupState.Idle,
) {
    override fun onAction(action: WalletSetupAction) {
        when (action) {
            WalletSetupAction.GenerateWallet -> generate()
            is WalletSetupAction.CopyAddress -> viewModelScope.launch {
                // Actual clipboard write is a platform concern (deferred);
                // for now we confirm the intent reached the ViewModel.
                emitEvent(WalletSetupEvent.AddressCopied)
            }
        }
    }

    private fun generate() {
        setState { WalletSetupState.Loading }
        viewModelScope.launch {
            when (val result = repository.generateWallet()) {
                is WalletCreateResult.Success ->
                    setState { WalletSetupState.Loaded(result.mnemonicWords, result.address) }

                is WalletCreateResult.Failure -> {
                    setState { WalletSetupState.Error(result.message) }
                    emitEvent(WalletSetupEvent.ShowError(result.message))
                }
            }
        }
    }
}
