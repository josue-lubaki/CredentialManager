package ca.josue_lubaki.googleauthenticationdemo.navigation

import ca.josue_lubaki.googleauthenticationdemo.utils.Constants
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * created by Josue Lubaki
 * date : 2024-02-24
 * version : 1.0.0
 */

sealed class ScreenTarget(val route : String) {
    data object SignIn : ScreenTarget("sign_in")
    data object Profile : ScreenTarget("profile")
    data object Website : ScreenTarget("website")
}