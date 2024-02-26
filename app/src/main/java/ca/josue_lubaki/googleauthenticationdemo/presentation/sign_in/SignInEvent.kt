package ca.josue_lubaki.googleauthenticationdemo.presentation.sign_in

import ca.josue_lubaki.google_auth.model.GoogleUser

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

sealed class SignInEvent {
    data class OnSaveCurrentUserAndToken(val user : GoogleUser, val newToken: String?) : SignInEvent()
}