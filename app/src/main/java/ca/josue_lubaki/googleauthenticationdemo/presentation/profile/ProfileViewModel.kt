package ca.josue_lubaki.googleauthenticationdemo.presentation.profile

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.josue_lubaki.google_auth.auth.GoogleAuthProvider
import ca.josue_lubaki.googleauthenticationdemo.domain.usecases.UseCases
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

class ProfileViewModel(
    private val useCases: UseCases,
    private val googleAuthProvider: GoogleAuthProvider,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch(dispatcher) {
            try {
                useCases.readCurrentUserUseCase().let { user ->
                    user.collect { currentUser ->
                        _state.update { it.copy(
                            user = currentUser,
                            isLoading = false
                        )}
                    }
                }
            }
            catch (e: Exception) {
                _state.update { it.copy(
                    error = e.message,
                    isLoading = false
                )}
            }
        }
    }

    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.OnSignOut -> event.reduce()
        }
    }

    private fun ProfileEvent.OnSignOut.reduce() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch(dispatcher) {
            try{
                useCases.signOutCurrentUserUseCase()
                googleAuthProvider.signOut()
                _state.update {
                    it.copy(
                        isLoading = false,
                        user = null
                    )
                }
            }
            catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }
}