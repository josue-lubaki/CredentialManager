package ca.josue_lubaki.google_auth.di

import androidx.credentials.CredentialManager
import ca.josue_lubaki.google_auth.BuildConfig
import ca.josue_lubaki.google_auth.R
import ca.josue_lubaki.google_auth.auth.GoogleAuthCredentials
import ca.josue_lubaki.google_auth.auth.GoogleAuthProvider
import ca.josue_lubaki.google_auth.auth.impl.GoogleAuthProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

val googleModule = module {
    single<GoogleAuthProvider> {
        GoogleAuthProviderImpl(
            credentials = get(),
            credentialManager = get()
        )
    }
    single<GoogleAuthCredentials> {
        GoogleAuthCredentials(BuildConfig.WEB_CLIENT_ID)
    }
    single<CredentialManager> {
        CredentialManager.create(androidContext())
    }
}