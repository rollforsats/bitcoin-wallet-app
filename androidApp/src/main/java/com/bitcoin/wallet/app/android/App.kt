package com.bitcoin.wallet.app.android

import android.app.Application
import com.bitcoin.wallet.app.di.AndroidAppGraph
import dev.zacsweers.metro.createGraph

class App : Application() {
    lateinit var graph: AndroidAppGraph
        private set

    override fun onCreate() {
        super.onCreate()
        graph = createGraph<AndroidAppGraph>()
    }
}
