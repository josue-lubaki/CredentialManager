package ca.josue_lubaki.googleauthenticationdemo.presentation.sign_in

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.josue_lubaki.googleauthenticationdemo.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

/**
 * created by Josue Lubaki
 * date : 2024-02-24
 * version : 1.0.0
 */

class SignInViewModel(
    private val context : Context
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state : StateFlow<SignInState> = _state.asStateFlow()

    private val credentialManager = CredentialManager.create(context)
    private val rawNonce = UUID.randomUUID().toString()
    private val bytes = rawNonce.toByteArray()
    private val md = MessageDigest.getInstance("SHA-256")
    private val digest = md.digest(bytes)
    private val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

    private val googleIodOption : GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(true)
        .setServerClientId(context.getString(R.string.web_client_id))
        .setNonce(hashedNonce)
        .build()

    private val request : GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIodOption)
        .build()

    fun onEvent(event : SignInEvent) {
        when(event) {
            is SignInEvent.SignIn -> {
                signIn()
            }
        }
    }

    private fun signIn() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )

                val credential = result.credential

                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

                _state.update {
                    it.copy(
                        isLoading = false,
                        isSignedIn = true,
                        data = GoogleUserData(
                            id = googleIdTokenCredential.id,
                            name = googleIdTokenCredential.displayName,
                            profilePicture = googleIdTokenCredential.profilePictureUri.toString()
                        )
                    )
                }

                Log.i("xxxx", googleIdToken)

                Toast.makeText(context, "You are signed in !", Toast.LENGTH_LONG).show()
            }  catch (e: Exception) { // GetCredentialException
                Log.e("xxxx", "Error signing in with Google", e)
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            } catch (e : GoogleIdTokenParsingException) {
                Log.e("xxxx", "Error parsing Google ID token", e)
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }
}