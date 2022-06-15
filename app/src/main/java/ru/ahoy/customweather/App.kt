package ru.ahoy.customweather

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.ahoy.customweather.di.modules.allModules

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            startKoin {
                androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
                androidContext(this@App)
                modules(allModules)
            }
        }
    }
}