package ca.josue_lubaki.googleauthenticationdemo.presentation.sign_in

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ca.josue_lubaki.google_auth.model.GoogleUser
import ca.josue_lubaki.google_auth.ui.GoogleButtonUiContainer
import ca.josue_lubaki.googleauthenticationdemo.navigation.ScreenTarget
import org.koin.androidx.compose.koinViewModel

/**
 * created by Josue Lubaki
 * date : 2024-02-24
 * version : 1.0.0
 */

@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
    onNavigateToProfile : () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = state) {
        if(state.isSignedIn) {
            onNavigateToProfile()
        }
    }

    val onSaveCurrentUserAndToken = { newUser : GoogleUser, newToken : String? ->
        viewModel.onEvent(SignInEvent.OnSaveCurrentUserAndToken(newUser, newToken))
    }

    GoogleButtonUiContainer(onGoogleSignInResult = { googleUser ->
        val idToken = googleUser?.id // Send this idToken to your backend to verify
        val googleUserResult = GoogleUser(
            id = googleUser?.id,
            name = googleUser?.name,
            profilePicture = googleUser?.profilePicture
        )
        onSaveCurrentUserAndToken(googleUserResult, idToken)
    }) {
        Button(
            onClick = { this.onClick() }
        ) {
            Text("Sign-In with Google")
        }
    }
}
