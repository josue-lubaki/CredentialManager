package ca.josue_lubaki.google_auth.auth.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import ca.josue_lubaki.google_auth.auth.GoogleAuthCredentials
import ca.josue_lubaki.google_auth.auth.GoogleAuthProvider
import ca.josue_lubaki.google_auth.auth.GoogleAuthUiProvider

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

class GoogleAuthProviderImpl(
    private val credentials: GoogleAuthCredentials,
    private val credentialManager: CredentialManager,
) : GoogleAuthProvider {

    @Composable
    override fun getUiProvider(): GoogleAuthUiProvider {
        val activityContext = LocalContext.current
        return GoogleAuthUiProviderImpl(
            activityContext = activityContext,
            credentialManager = credentialManager,
            credentials = credentials
        )
    }

    override suspend fun signOut() {
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
    }
}