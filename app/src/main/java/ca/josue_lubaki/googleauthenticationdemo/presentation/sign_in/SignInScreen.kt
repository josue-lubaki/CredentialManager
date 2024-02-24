package ca.josue_lubaki.googleauthenticationdemo.presentation.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * created by Josue Lubaki
 * date : 2024-02-24
 * version : 1.0.0
 */

@Composable
fun SignInScreen(
    viewModel : SignInViewModel,
    onNavigateToProfile : (GoogleUserData) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    
    LaunchedEffect(key1 = state.isSignedIn) {
        if(state.isSignedIn) {
            onNavigateToProfile(
                GoogleUserData(
                    state.data.id,
                    state.data.name,
                    state.data.profilePicture
                )
            )
        }
    }

    SignInContent(
        state = state,
        onSignIn = {
            viewModel.onEvent(SignInEvent.SignIn)
        }
    )

}

@Composable
fun SignInContent(
    state: SignInState,
    onSignIn: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = onSignIn) {
            Text("Sign in with Google")
        }
    }
}
