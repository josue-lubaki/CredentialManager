package ca.josue_lubaki.google_auth.auth

import ca.josue_lubaki.google_auth.model.GoogleUser

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

interface GoogleAuthUiProvider {

    /**
     * Opens Sign In with Google UI,
     * @return returns GoogleUser
     */
    suspend fun signIn(): GoogleUser?
}