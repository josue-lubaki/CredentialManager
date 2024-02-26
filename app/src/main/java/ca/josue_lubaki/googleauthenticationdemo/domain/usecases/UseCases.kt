package ca.josue_lubaki.googleauthenticationdemo.domain.usecases

import ca.josue_lubaki.googleauthenticationdemo.domain.usecases.read_current_user.ReadCurrentUserUseCase
import ca.josue_lubaki.googleauthenticationdemo.domain.usecases.read_current_user_token.ReadCurrentUserTokenUseCase
import ca.josue_lubaki.googleauthenticationdemo.domain.usecases.save_current_user.SaveCurrentUserUseCase
import ca.josue_lubaki.googleauthenticationdemo.domain.usecases.save_current_user_token.SaveCurrentUserTokenUseCase
import ca.josue_lubaki.googleauthenticationdemo.domain.usecases.sign_out.SignOutCurrentUserUseCase

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

data class UseCases(
    val saveCurrentUserTokenUseCase: SaveCurrentUserTokenUseCase,
    val readCurrentUserTokenUseCase: ReadCurrentUserTokenUseCase,
    val saveCurrentUserUseCase: SaveCurrentUserUseCase,
    val readCurrentUserUseCase: ReadCurrentUserUseCase,
    val signOutCurrentUserUseCase: SignOutCurrentUserUseCase
)