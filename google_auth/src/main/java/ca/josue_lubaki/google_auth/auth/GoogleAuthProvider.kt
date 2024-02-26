package ca.josue_lubaki.google_auth.auth

import androidx.compose.runtime.Composable

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

interface GoogleAuthProvider {
    @Composable
    fun getUiProvider(): GoogleAuthUiProvider

    suspend fun signOut()
}