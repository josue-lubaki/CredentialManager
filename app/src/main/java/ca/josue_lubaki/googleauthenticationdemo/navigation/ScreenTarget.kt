package ca.josue_lubaki.googleauthenticationdemo.navigation

/**
 * created by Josue Lubaki
 * date : 2024-02-24
 * version : 1.0.0
 */

sealed class ScreenTarget(val route : String) {
    data object SignIn : ScreenTarget("sign_in")
    data object Profile : ScreenTarget("profile/{id}/{name}") {
        fun createRoute(id: String?, name : String?) : String {
            return "profile/$id/$name"
        }
    }
}