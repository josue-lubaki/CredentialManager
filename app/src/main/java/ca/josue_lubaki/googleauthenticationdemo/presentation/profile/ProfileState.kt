package ca.josue_lubaki.googleauthenticationdemo.presentation.profile

import ca.josue_lubaki.google_auth.model.GoogleUser

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

data class ProfileState(
    val user: GoogleUser? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)