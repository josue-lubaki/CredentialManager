package ca.josue_lubaki.googleauthenticationdemo.presentation.sign_in

/**
 * created by Josue Lubaki
 * date : 2024-02-24
 * version : 1.0.0
 */

sealed class SignInEvent {
    data object SignIn : SignInEvent()
}