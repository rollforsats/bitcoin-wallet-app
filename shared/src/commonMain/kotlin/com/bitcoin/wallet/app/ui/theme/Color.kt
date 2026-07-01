package com.bitcoin.wallet.app.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/** The Bitcoin brand orange — the single accent this app is built around. */
val BitcoinOrange = Color(0xFFF7931A)
private val OrangePressed = Color(0xFFE07E00)

private val Black = Color(0xFF000000)
private val White = Color(0xFFFFFFFF)
private val NearBlack = Color(0xFF0A0A0A)
private val DarkSurface = Color(0xFF141414)
private val LightMuted = Color(0xFF6B6B6B)
private val DarkMuted = Color(0xFFB0B0B0)

/**
 * Light: Bitcoin orange on white, black text. Strike-style restraint with an
 * orange-forward accent — primary actions and emphasis are orange; everything
 * else stays high-contrast monochrome.
 */
val LightColors = lightColorScheme(
    primary = BitcoinOrange,
    onPrimary = Black,
    primaryContainer = BitcoinOrange,
    onPrimaryContainer = Black,
    secondary = OrangePressed,
    onSecondary = White,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    surfaceVariant = Color(0xFFF2F2F2),
    onSurfaceVariant = LightMuted,
    outline = Color(0xFFD9D9D9),
)

/** Dark: black background, white text, Bitcoin orange accent. */
val DarkColors = darkColorScheme(
    primary = BitcoinOrange,
    onPrimary = Black,
    primaryContainer = BitcoinOrange,
    onPrimaryContainer = Black,
    secondary = OrangePressed,
    onSecondary = White,
    background = NearBlack,
    onBackground = White,
    surface = DarkSurface,
    onSurface = White,
    surfaceVariant = Color(0xFF1F1F1F),
    onSurfaceVariant = DarkMuted,
    outline = Color(0xFF333333),
)
