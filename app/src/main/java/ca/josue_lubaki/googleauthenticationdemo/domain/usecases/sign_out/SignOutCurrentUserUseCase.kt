package ca.josue_lubaki.googleauthenticationdemo.domain.usecases.sign_out

import ca.josue_lubaki.googleauthenticationdemo.data.repository.DatastoreRepository

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

class SignOutCurrentUserUseCase (
    private val datastoreRepository: DatastoreRepository
){
    suspend operator fun invoke() {
        try {
            datastoreRepository.clearAll()
        }
        catch (e: Exception) {
            throw e
        }
    }
}