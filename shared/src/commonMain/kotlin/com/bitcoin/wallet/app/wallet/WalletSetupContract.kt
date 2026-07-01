package com.bitcoin.wallet.app.wallet

/** The single state the setup screen renders. */
sealed interface WalletSetupState {
    data object Idle : WalletSetupState
    data object Loading : WalletSetupState
    data class Loaded(val mnemonicWords: List<String>, val address: String) : WalletSetupState
    data class Error(val message: String) : WalletSetupState
}

/** Every user intent on the setup screen flows through [WalletSetupViewModel.onAction]. */
sealed interface WalletSetupAction {
    data object GenerateWallet : WalletSetupAction
    data class CopyAddress(val address: String) : WalletSetupAction
}

/** One-shot effects the screen reacts to (snackbars). */
sealed interface WalletSetupEvent {
    data class ShowError(val message: String) : WalletSetupEvent
    data object AddressCopied : WalletSetupEvent
}
