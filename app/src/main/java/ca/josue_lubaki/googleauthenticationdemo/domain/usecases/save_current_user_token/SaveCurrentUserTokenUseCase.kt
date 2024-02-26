package ca.josue_lubaki.googleauthenticationdemo.domain.usecases.save_current_user_token

import ca.josue_lubaki.googleauthenticationdemo.data.repository.DatastoreRepository

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

class SaveCurrentUserTokenUseCase(
    private val dataStoreRepository: DatastoreRepository
) {
    suspend operator fun invoke(token: String) {
        return try {
            dataStoreRepository.saveCurrentUserToken(token)
        } catch (e: Exception) {
            throw e
        }
    }
}