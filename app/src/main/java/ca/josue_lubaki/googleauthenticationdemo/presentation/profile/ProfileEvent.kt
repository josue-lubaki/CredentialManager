package ca.josue_lubaki.googleauthenticationdemo.presentation.profile

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

sealed class ProfileEvent {
    data object OnSignOut : ProfileEvent()
}