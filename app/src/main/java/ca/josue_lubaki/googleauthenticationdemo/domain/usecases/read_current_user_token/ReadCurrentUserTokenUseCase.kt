package ca.josue_lubaki.googleauthenticationdemo.domain.usecases.read_current_user_token

import ca.josue_lubaki.googleauthenticationdemo.data.repository.DatastoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

class ReadCurrentUserTokenUseCase(
    private val dataStoreRepository: DatastoreRepository
) {
    suspend operator fun invoke() : Flow<String> {
        return try {
            dataStoreRepository.readCurrentUserToken()
        } catch (e: Exception) {
            flowOf(e.message.toString())
        }
    }
}