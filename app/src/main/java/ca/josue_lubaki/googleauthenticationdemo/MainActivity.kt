package ca.josue_lubaki.googleauthenticationdemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ca.josue_lubaki.googleauthenticationdemo.navigation.ScreenTarget
import ca.josue_lubaki.googleauthenticationdemo.presentation.cv.WebsiteScreen
import ca.josue_lubaki.googleauthenticationdemo.presentation.profile.ProfileScreen
import ca.josue_lubaki.google_auth.model.GoogleUser
import ca.josue_lubaki.googleauthenticationdemo.navigation.SetupNavigation
import ca.josue_lubaki.googleauthenticationdemo.presentation.profile.ProfileEvent
import ca.josue_lubaki.googleauthenticationdemo.presentation.profile.ProfileViewModel
import ca.josue_lubaki.googleauthenticationdemo.presentation.sign_in.SignInEvent
import ca.josue_lubaki.googleauthenticationdemo.presentation.sign_in.SignInScreen
import ca.josue_lubaki.googleauthenticationdemo.presentation.sign_in.SignInViewModel
import ca.josue_lubaki.googleauthenticationdemo.ui.theme.GoogleAuthenticationDemoTheme
import ca.josue_lubaki.googleauthenticationdemo.utils.Constants
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.security.MessageDigest
import java.util.UUID

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoogleAuthenticationDemoTheme {
                Box(Modifier.safeDrawingPadding()) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        SetupNavigation()
                    }
                }
            }
        }
    }
}