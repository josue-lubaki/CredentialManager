package ca.josue_lubaki.googleauthenticationdemo.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.josue_lubaki.googleauthenticationdemo.presentation.cv.WebsiteScreen
import ca.josue_lubaki.googleauthenticationdemo.presentation.profile.ProfileEvent
import ca.josue_lubaki.googleauthenticationdemo.presentation.profile.ProfileScreen
import ca.josue_lubaki.googleauthenticationdemo.presentation.profile.ProfileViewModel
import ca.josue_lubaki.googleauthenticationdemo.presentation.sign_in.SignInScreen
import ca.josue_lubaki.googleauthenticationdemo.presentation.sign_in.SignInViewModel
import ca.josue_lubaki.googleauthenticationdemo.utils.Constants
import org.koin.androidx.compose.koinViewModel

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

@Composable
fun SetupNavigation() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val navController = rememberNavController()
        val signInViewModel = koinViewModel<SignInViewModel>()
        val profileViewModel = koinViewModel<ProfileViewModel>()

        NavHost(navController = navController, startDestination = ScreenTarget.SignIn.route ){
            composable(ScreenTarget.SignIn.route) {
                SignInScreen(
                    viewModel = signInViewModel,
                    onNavigateToProfile = { navController.navigate(ScreenTarget.Profile.route) }
                )
            }

            composable(
                route = ScreenTarget.Profile.route,
            ) {
                ProfileScreen(
                    viewModel = profileViewModel,
                    onSignOut = {
                        profileViewModel.onEvent(ProfileEvent.OnSignOut)
                    },
                    onNavigateToWebsite = {
                        navController.navigate(ScreenTarget.Website.route)
                    },
                    onNavigateToSignIn = {
                        navController.navigate(ScreenTarget.SignIn.route)
                    }
                )
            }

            composable(
                route = ScreenTarget.Website.route
            ) {
                WebsiteScreen(Constants.WEBSITE_URL)
            }
        }
    }
}