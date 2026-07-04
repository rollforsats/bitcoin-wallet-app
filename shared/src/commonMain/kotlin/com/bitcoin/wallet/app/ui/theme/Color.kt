package com.bitcoin.wallet.app.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/** The Bitcoin brand orange — the single accent this app is built around. */
val BitcoinOrange = Color(0xFFF7931A)

private val Black = Color(0xFF000000)
private val NearBlack = Color(0xFF0A0A0A)
private val CardDark = Color(0xFF1A1A1A)
private val White = Color(0xFFFFFFFF)
private val ErrorOnDark = Color(0xFFFF6B6B)
private val ErrorOnOrange = Color(0xFF7A0000)

/** Mnemonic/address cards are dark in BOTH modes, so their content uses these
 *  fixed colors (not theme roles that flip and would vanish on the dark card). */
val CardContentText = White
val CardContentAccent = BitcoinOrange

/**
 * Light: Bitcoin-orange background, black text (primary and secondary), black
 * buttons with orange text. Only orange + black — no gray.
 */
val LightColors = lightColorScheme(
    primary = Black,          // button container
    onPrimary = BitcoinOrange, // button text
    primaryContainer = Black,
    onPrimaryContainer = BitcoinOrange,
    secondary = Black,
    onSecondary = BitcoinOrange,
    background = BitcoinOrange,
    onBackground = Black,      // primary text
    surface = BitcoinOrange,
    onSurface = Black,
    surfaceVariant = CardDark, // dark cards on the orange background
    onSurfaceVariant = Black,  // secondary text
    outline = Black,
    error = ErrorOnOrange,
)

/**
 * Dark: black background, Bitcoin-orange text (primary and secondary), orange
 * buttons with black text. Only orange + black — no gray.
 */
val DarkColors = darkColorScheme(
    primary = BitcoinOrange,   // button container
    onPrimary = Black,         // button text
    primaryContainer = BitcoinOrange,
    onPrimaryContainer = Black,
    secondary = BitcoinOrange,
    onSecondary = Black,
    background = NearBlack,
    onBackground = BitcoinOrange, // primary text
    surface = NearBlack,
    onSurface = BitcoinOrange,
    surfaceVariant = CardDark,
    onSurfaceVariant = BitcoinOrange, // secondary text
    outline = BitcoinOrange, // secondary/outlined button border
    error = ErrorOnDark,
)
