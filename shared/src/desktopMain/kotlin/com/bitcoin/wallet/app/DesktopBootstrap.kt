package com.bitcoin.wallet.app

import com.bitcoin.wallet.app.di.DesktopAppGraph
import dev.zacsweers.metro.createGraph

object DesktopBootstrap {
    val graph: DesktopAppGraph by lazy { createGraph<DesktopAppGraph>() }
}
