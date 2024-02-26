package ca.josue_lubaki.googleauthenticationdemo.domain.repository

import ca.josue_lubaki.google_auth.model.GoogleUser
import kotlinx.coroutines.flow.Flow

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */


interface DataStoreOperations {
    suspend fun saveCurrentUserToken(token : String)
    suspend fun readCurrentUserToken() : Flow<String>

    suspend fun saveCurrentUser(user : GoogleUser)

    suspend fun readCurrentUser() : Flow<GoogleUser>

    suspend fun clearAll()
}