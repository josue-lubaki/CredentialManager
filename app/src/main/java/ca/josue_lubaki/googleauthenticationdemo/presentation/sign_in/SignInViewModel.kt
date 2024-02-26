package ca.josue_lubaki.googleauthenticationdemo.presentation.sign_in

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.josue_lubaki.google_auth.model.GoogleUser
import ca.josue_lubaki.googleauthenticationdemo.domain.usecases.UseCases
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * created by Josue Lubaki
 * date : 2024-02-24
 * version : 1.0.0
 */

class SignInViewModel(
    private val useCases: UseCases,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(isLoading = true) }
            useCases.readCurrentUserTokenUseCase().let {
                it.collectLatest { token ->
                    token.let { tokenValue ->
                         useCases.readCurrentUserUseCase().let { user ->
                            user.collectLatest { currentUser ->
                                val isSignedIn = tokenValue.isNotEmpty() && currentUser.id?.isNotEmpty() == true
                                Log.d("SignInViewModel", "Current user is Logged : $isSignedIn")

                                _state.update { it.copy(
                                    isLoading = false,
                                    user = currentUser,
                                    token = tokenValue,
                                    isSignedIn = isSignedIn
                                ) }
                            }
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: SignInEvent) {
        when(event) {
            is SignInEvent.OnSaveCurrentUserAndToken -> event.reduce()
        }
    }

    private fun SignInEvent.OnSaveCurrentUserAndToken.reduce() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch(dispatcher) {
            val newUser = GoogleUser(
                id = user.id,
                name = user.name,
                profilePicture = user.profilePicture
            )
            useCases.saveCurrentUserTokenUseCase(token = newToken.orEmpty())
            useCases.saveCurrentUserUseCase(user = newUser)
            _state.update { it.copy(
                isLoading = false,
                user = newUser,
                token = newToken.orEmpty(),
                isSignedIn = true
            ) }
        }
    }
}