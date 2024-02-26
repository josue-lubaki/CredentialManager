package ca.josue_lubaki.googleauthenticationdemo.presentation.cv

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

/**
 * created by Josue Lubaki
 * date : 2024-02-25
 * version : 1.0.0
 */

@Composable
fun WebsiteScreen(url : String) {
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    }, update = {
        it.loadUrl(url)
    })
}