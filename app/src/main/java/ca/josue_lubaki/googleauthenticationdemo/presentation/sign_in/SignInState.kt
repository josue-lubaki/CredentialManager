package ca.josue_lubaki.googleauthenticationdemo.presentation.sign_in

data class SignInState(
    val isLoading: Boolean = false,
    val isSignedIn: Boolean = false,
    val error: String? = null,
    val data : GoogleUserData = GoogleUserData()
)
