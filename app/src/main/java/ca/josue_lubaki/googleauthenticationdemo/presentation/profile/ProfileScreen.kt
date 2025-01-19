package ca.josue_lubaki.googleauthenticationdemo.presentation.profile

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ca.josue_lubaki.google_auth.model.GoogleUser
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

/**
 * created by Josue Lubaki
 * date : 2024-02-24
 * version : 1.0.0
 */

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onNavigateToWebsite: () -> Unit,
    onSignOut : () -> Unit,
    onNavigateToSignIn: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = state) {
        if (state.user?.id.isNullOrEmpty()) {
            onNavigateToSignIn()
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {

        AnimatedContent(
            targetState = state, label = "animate screen"
        ) { targetState ->
            if (targetState.error != null) {
                Text(
                    text = targetState.error,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
            else if (targetState.isLoading) { CircularProgressIndicator() }
            else {
                targetState.user?.let {
                    ProfileContent(
                        user = it,
                        onNavigateToWebsite = onNavigateToWebsite,
                        onSignOut = onSignOut
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileContent(
    user: GoogleUser,
    onNavigateToWebsite: () -> Unit,
    onSignOut : () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.profilePicture)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            loading = {
                CircularProgressIndicator()
            },
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = user.name ?: "", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            onNavigateToWebsite()
        }) {
            Text(
                text = "My website",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onSignOut() },
            colors = ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        ) {
            Text(
                text = "Sign Out",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}