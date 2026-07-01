package com.bitcoin.wallet.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * The single theme entry point. Every platform host (Android, iOS, desktop)
 * wraps its content in this so the Bitcoin-orange palette and Strike-style
 * typography are consistent everywhere.
 */
@Composable
fun WalletTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = WalletTypography,
        content = content,
    )
}
