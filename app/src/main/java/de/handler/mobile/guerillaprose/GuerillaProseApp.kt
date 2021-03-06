package de.handler.mobile.guerillaprose

import android.app.Application
import de.handler.mobile.guerillaprose.data.DependencyProvider
import org.koin.android.ext.android.startKoin

class GuerillaProseApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val module = DependencyProvider.createAppModule(this)
        startKoin(this, listOf(module))
    }
}