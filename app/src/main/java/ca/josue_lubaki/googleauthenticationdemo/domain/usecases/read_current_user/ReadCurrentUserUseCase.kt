package ca.josue_lubaki.googleauthenticationdemo.domain.usecases.read_current_user

import ca.josue_lubaki.google_auth.model.GoogleUser
import ca.josue_lubaki.googleauthenticationdemo.data.repository.DatastoreRepository
import kotlinx.coroutines.flow.Flow

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

class ReadCurrentUserUseCase(
    private val dataStoreRepository: DatastoreRepository
) {
    suspend operator fun invoke() : Flow<GoogleUser> {
        return try {
            dataStoreRepository.readCurrentUser()
        } catch (e: Exception) {
            throw e
        }
    }
}