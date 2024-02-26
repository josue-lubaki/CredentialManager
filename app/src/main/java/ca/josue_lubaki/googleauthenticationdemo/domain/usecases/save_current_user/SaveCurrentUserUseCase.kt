package ca.josue_lubaki.googleauthenticationdemo.domain.usecases.save_current_user

import ca.josue_lubaki.google_auth.model.GoogleUser
import ca.josue_lubaki.googleauthenticationdemo.data.repository.DatastoreRepository

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

class SaveCurrentUserUseCase(
    private val dataStoreRepository: DatastoreRepository
) {
    suspend operator fun invoke(user: GoogleUser){
        return try {
            dataStoreRepository.saveCurrentUser(user)
        } catch (e: Exception) {
            throw e
        }
    }
}