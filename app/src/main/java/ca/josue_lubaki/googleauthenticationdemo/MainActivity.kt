package ca.josue_lubaki.googleauthenticationdemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ca.josue_lubaki.googleauthenticationdemo.navigation.ScreenTarget
import ca.josue_lubaki.googleauthenticationdemo.presentation.profile.ProfileScreen
import ca.josue_lubaki.googleauthenticationdemo.presentation.profile.ProfileViewModel
import ca.josue_lubaki.googleauthenticationdemo.presentation.sign_in.GoogleUserData
import ca.josue_lubaki.googleauthenticationdemo.presentation.sign_in.SignInScreen
import ca.josue_lubaki.googleauthenticationdemo.presentation.sign_in.SignInViewModel
import ca.josue_lubaki.googleauthenticationdemo.ui.theme.GoogleAuthenticationDemoTheme
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

class MainActivity : ComponentActivity() {

    private val signInViewModel by lazy {
        SignInViewModel(
            context = this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoogleAuthenticationDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   Column(
                       verticalArrangement = Arrangement.Center,
                       horizontalAlignment = Alignment.CenterHorizontally
                   ){
                       val navController = rememberNavController()
                       
                       NavHost(navController = navController, startDestination = ScreenTarget.SignIn.route ){
                           composable(ScreenTarget.SignIn.route) {
                               SignInScreen(
                                   viewModel = signInViewModel,
                                   onNavigateToProfile = {
                                       navController.navigate(
                                           ScreenTarget.Profile.createRoute(
                                               id = it.id,
                                               name = it.name,
                                           )
                                       )
                                   }
                               )
                           }

                           composable(
                               route = ScreenTarget.Profile.route,
                               arguments = listOf(
                                   navArgument("id") { type = NavType.StringType },
                                   navArgument("name") { type = NavType.StringType },
                               )
                           ) {
                               val viewModel : ProfileViewModel = viewModel<ProfileViewModel>()
                               val user = GoogleUserData(
                                   id = it.arguments?.getString("id"),
                                   name = it.arguments?.getString("name")
                               )
                               ProfileScreen(
                                   viewModel = viewModel,
                                   user = user
                               )
                           }
                       }
                   }
                }
            }
        }
    }
}

@Composable
fun GoogleSignInButton() {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val onClick : () -> Unit = {
        val credentialManager = CredentialManager.create(context)
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

        val googleIodOption : GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(context.getString(R.string.web_client_id))
            .setNonce(hashedNonce)
            .build()

        val request : GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIodOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )

                val credential = result.credential

                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

                Log.i("xxxx", googleIdToken)

                Toast.makeText(context, "You are signed in !", Toast.LENGTH_LONG).show()
            }  catch (e: Exception) { // GetCredentialException
                Log.e("xxxx", "Error signing in with Google", e)
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            } catch (e : GoogleIdTokenParsingException) {
                Log.e("xxxx", "Error parsing Google ID token", e)
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    Button(onClick = onClick) {
        Text("Sign in with Google")
    }
}