package ca.josue_lubaki.googleauthenticationdemo.data.repository.impl

import ca.josue_lubaki.google_auth.model.GoogleUser
import ca.josue_lubaki.googleauthenticationdemo.data.repository.DatastoreRepository
import ca.josue_lubaki.googleauthenticationdemo.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

class DatastoreRepositoryImpl(
    private val dataStore : DataStoreOperations
) : DatastoreRepository {
    override suspend fun saveCurrentUserToken(token : String) = dataStore.saveCurrentUserToken(token)
    override suspend fun readCurrentUserToken() : Flow<String> = dataStore.readCurrentUserToken()
    override suspend fun saveCurrentUser(user: GoogleUser) = dataStore.saveCurrentUser(user)
    override suspend fun readCurrentUser(): Flow<GoogleUser> = dataStore.readCurrentUser()
    override suspend fun clearAll() = dataStore.clearAll()
}