package com.bitcoin.wallet.app

import com.bitcoin.wallet.app.di.IosAppGraph
import dev.zacsweers.metro.createGraph

object IosBootstrap {
    val graph: IosAppGraph by lazy { createGraph<IosAppGraph>() }
}
