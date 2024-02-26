package ca.josue_lubaki.googleauthenticationdemo.data.datasource.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import ca.josue_lubaki.google_auth.model.GoogleUser
import ca.josue_lubaki.googleauthenticationdemo.domain.repository.DataStoreOperations
import ca.josue_lubaki.googleauthenticationdemo.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCE_NAME)

class DataStoreOperationsImpl(context : Context) : DataStoreOperations {

    private val dataStore : DataStore<Preferences> = context.dataStore

    private object PreferencesKeys {
        val CURRENT_USER_TOKEN = stringPreferencesKey(Constants.CURRENT_USER_TOKEN)
        val CURRENT_USER_ID = stringPreferencesKey(Constants.CURRENT_USER_ID)
        val CURRENT_USER_NAME = stringPreferencesKey(Constants.CURRENT_USER_NAME)
        val CURRENT_USER_PROFILE_PICTURE = stringPreferencesKey(Constants.CURRENT_USER_PROFILE_PICTURE)
    }

    override suspend fun saveCurrentUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CURRENT_USER_TOKEN] = token
        }
    }

    override suspend fun readCurrentUserToken(): Flow<String> {
        return dataStore.data.catch {
            if (it is IOException) {
                emit(emptyPreferences())
            }
        }.map { preferences ->
            preferences[PreferencesKeys.CURRENT_USER_TOKEN] ?: ""
        }
    }

    override suspend fun saveCurrentUser(user: GoogleUser) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CURRENT_USER_ID] = user.id as String
            preferences[PreferencesKeys.CURRENT_USER_NAME] = user.name as String
            preferences[PreferencesKeys.CURRENT_USER_PROFILE_PICTURE] = user.profilePicture as String
        }
    }

    override suspend fun readCurrentUser(): Flow<GoogleUser> {
        return dataStore.data.catch {
            if (it is IOException) {
                emit(emptyPreferences())
            }
        }.map { preferences ->
            GoogleUser(
                id = preferences[PreferencesKeys.CURRENT_USER_ID].orEmpty(),
                name = preferences[PreferencesKeys.CURRENT_USER_NAME].orEmpty(),
                profilePicture = preferences[PreferencesKeys.CURRENT_USER_PROFILE_PICTURE].orEmpty()
            )
        }
    }

    override suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}