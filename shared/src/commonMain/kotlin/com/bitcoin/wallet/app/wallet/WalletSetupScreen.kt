package com.bitcoin.wallet.app.wallet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bitcoin.wallet.app.ui.theme.CardContentAccent
import com.bitcoin.wallet.app.ui.theme.CardContentText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.LaunchedEffect

@Composable
fun WalletSetupScreen(viewModel: WalletSetupViewModel) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is WalletSetupEvent.ShowError -> snackbarHostState.showSnackbar(event.message)
                WalletSetupEvent.AddressCopied -> snackbarHostState.showSnackbar("Address copied")
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (val s = state) {
                WalletSetupState.Idle -> IntroContent(
                    onGenerate = { viewModel.onAction(WalletSetupAction.GenerateWallet) },
                )

                WalletSetupState.Loading -> LoadingContent()

                is WalletSetupState.Loaded -> WalletContent(
                    words = s.mnemonicWords,
                    address = s.address,
                    onCopyAddress = { viewModel.onAction(WalletSetupAction.CopyAddress(s.address)) },
                )

                is WalletSetupState.Error -> IntroContent(
                    errorMessage = s.message,
                    onGenerate = { viewModel.onAction(WalletSetupAction.GenerateWallet) },
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.IntroContent(
    errorMessage: String? = null,
    onGenerate: () -> Unit,
) {
    Spacer(Modifier.height(48.dp))
    Text(
        text = "Your Bitcoin wallet",
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
    )
    Spacer(Modifier.height(12.dp))
    Text(
        text = "Generate a new self-custodial wallet on signet. You'll see your recovery phrase and first receive address.",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
    )
    if (errorMessage != null) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
        )
    }
    Spacer(Modifier.weight(1f))
    PrimaryButton(text = "Generate Wallet", onClick = onGenerate)
}

@Composable
private fun ColumnScope.LoadingContent() {
    Spacer(Modifier.weight(1f))
    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    Spacer(Modifier.weight(1f))
}

@Composable
private fun WalletContent(
    words: List<String>,
    address: String,
    onCopyAddress: () -> Unit,
) {
    SectionLabel("Recovery phrase")
    Spacer(Modifier.height(4.dp))
    Text(
        text = "Write these words down in order. Anyone with them controls your funds.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Spacer(Modifier.height(16.dp))
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 360.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 4.dp),
    ) {
        itemsIndexed(words) { index, word ->
            MnemonicCell(index = index + 1, word = word)
        }
    }

    Spacer(Modifier.height(28.dp))
    SectionLabel("Receive address")
    Spacer(Modifier.height(8.dp))
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = address,
            style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace),
            color = CardContentText,
            modifier = Modifier.padding(16.dp),
        )
    }
    Spacer(Modifier.height(12.dp))
    OutlinedButton(
        onClick = onCopyAddress,
        modifier = Modifier.fillMaxWidth(),
        // Border + text: black in light, orange in dark (from colorScheme.outline).
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.outline,
        ),
    ) {
        Text("Copy address", style = MaterialTheme.typography.labelLarge)
    }
    Spacer(Modifier.height(16.dp))
}

@Composable
private fun MnemonicCell(index: Int, word: String) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp),
        ) {
            Text(
                text = index.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = CardContentAccent,
            )
            Text(
                text = word,
                style = MaterialTheme.typography.titleMedium,
                color = CardContentText,
            )
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun PrimaryButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}
