package ca.josue_lubaki.googleauthenticationdemo.presentation.sign_in

import ca.josue_lubaki.google_auth.model.GoogleUser

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

data class SignInState (
    val isSignedIn : Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val user : GoogleUser? = null,
    val token : String? = null
)