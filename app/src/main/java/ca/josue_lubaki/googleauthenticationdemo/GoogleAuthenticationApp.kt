package ca.josue_lubaki.googleauthenticationdemo

import android.app.Application
import ca.josue_lubaki.google_auth.di.googleModule
import ca.josue_lubaki.googleauthenticationdemo.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

class GoogleAuthenticationApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GoogleAuthenticationApp)
            modules(appModule + googleModule)
        }
    }
}