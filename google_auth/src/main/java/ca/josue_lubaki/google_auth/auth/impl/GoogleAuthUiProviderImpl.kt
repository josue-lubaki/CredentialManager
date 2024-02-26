package ca.josue_lubaki.google_auth.auth.impl

import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import ca.josue_lubaki.google_auth.auth.GoogleAuthCredentials
import ca.josue_lubaki.google_auth.auth.GoogleAuthUiProvider
import ca.josue_lubaki.google_auth.model.GoogleUser
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

internal class GoogleAuthUiProviderImpl(
    private val activityContext: Context,
    private val credentialManager: CredentialManager,
    private val credentials: GoogleAuthCredentials,
) :
    GoogleAuthUiProvider {
    override suspend fun signIn(): GoogleUser? {
        return try {
            val credential = credentialManager.getCredential(
                context = activityContext,
                request = getCredentialRequest()
            ).credential
            getGoogleUserFromCredential(credential)
        } catch (e: GetCredentialException) {
            Log.d("GoogleAuthUiProviderImpl", "GoogleAuthUiProvider error: ${e.message}")
            null
        } catch (e: NullPointerException) {
            null
        }
    }

    private fun getGoogleUserFromCredential(credential: Credential): GoogleUser? {
        return when {
            credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL -> {
                try {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
                    GoogleUser(
                        id = googleIdTokenCredential.idToken,
                        name = googleIdTokenCredential.displayName ?: "",
                        profilePicture = googleIdTokenCredential.profilePictureUri?.toString()
                    )
                } catch (e: GoogleIdTokenParsingException) {
                    Log.d("GoogleAuthUiProviderImpl", "GoogleAuthUiProvider Received an invalid google id token response: ${e.message}")
                    null
                }
            }

            else -> null
        }
    }

    private fun getCredentialRequest(): GetCredentialRequest {
        return GetCredentialRequest.Builder()
            .addCredentialOption(getGoogleIdOption(serverClientId = credentials.serverId))
            .build()
    }

    private fun getGoogleIdOption(serverClientId: String): GetGoogleIdOption {
        return GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(true)
            .setServerClientId(serverClientId)
            .build()
    }
}