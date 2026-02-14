package com.example.kursovikkmp.android

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.kursovikkmp.feature.profile.ProfileEffect
import com.example.kursovikkmp.feature.profile.ProfileEvents
import com.example.kursovikkmp.feature.profile.ProfileState
import com.example.kursovikkmp.feature.profile.ProfileViewModel
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
fun ProfileScreen() {
    val viewModel: ProfileViewModel = koinViewModel()
    val state by viewModel.flowState.collectAsState()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    // Collect effects
    LaunchedEffect(Unit) {
        viewModel.effectFlow.collect { effect ->
            when (effect) {
                is ProfileEffect.ShowImageSourceDialog -> {
                    showDialog = true
                }
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap ?: return@rememberLauncherForActivityResult
        val path = saveBitmapToInternalStorage(context, bitmap)
        if (path.isNotEmpty()) {
            viewModel.pushEvent(ProfileEvents.PhotoChanged(path))
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri ?: return@rememberLauncherForActivityResult
        val path = copyUriToInternalStorage(context, uri)
        if (path.isNotEmpty()) {
            viewModel.pushEvent(ProfileEvents.PhotoChanged(path))
        }
    }

    // Show dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Select Image Source") },
            text = { Text("Choose where to get your profile photo from") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    cameraLauncher.launch(null)
                }) {
                    Text("Camera")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    galleryLauncher.launch("image/*")
                }) {
                    Text("Gallery")
                }
            }
        )
    }

    ProfileScreenView(
        state = state,
        onAvatarClick = { viewModel.pushEvent(ProfileEvents.AvatarTapped) },
        onLogoutClick = { viewModel.pushEvent(ProfileEvents.LogoutTapped) }
    )
}

@Composable
private fun ProfileScreenView(
    state: ProfileState,
    onAvatarClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineSmall
        )

        if (state.photoPath.isNotEmpty()) {
            AsyncImage(
                model = File(state.photoPath),
                contentDescription = "Profile photo",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .clickable { onAvatarClick() }
            )
        } else {
            Image(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile placeholder",
                modifier = Modifier
                    .size(120.dp)
                    .clickable { onAvatarClick() }
            )
        }

        if (state.isMockData) {
            Text(
                text = "Showing mock profile (database is empty)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ProfileRow("Name", state.fullName)
                ProfileRow("Gender", state.gender)
                ProfileRow("Birth date", state.birthDate)
                ProfileRow("Location", state.location)
                ProfileRow("Email", state.email)
                ProfileRow("Phone", state.phone)
            }
        }

        Button(
            onClick = onLogoutClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}

@Composable
private fun ProfileRow(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

private fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap): String {
    return try {
        val file = File(context.filesDir, "profile_photo_${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use { stream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        }
        file.absolutePath
    } catch (_: Throwable) {
        ""
    }
}

private fun copyUriToInternalStorage(context: Context, uri: Uri): String {
    return try {
        val file = File(context.filesDir, "profile_photo_${System.currentTimeMillis()}.jpg")
        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        } ?: return ""
        file.absolutePath
    } catch (_: Throwable) {
        ""
    }
}
