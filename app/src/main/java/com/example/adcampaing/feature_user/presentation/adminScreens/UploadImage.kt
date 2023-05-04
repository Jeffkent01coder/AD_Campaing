package com.example.adcampaing.feature_user.presentation.adminScreens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.io.ByteArrayOutputStream

@Composable
fun UploadImage(
    navController: NavController
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { imageUri = it }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (imageUri != null) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUri!!)
                    .crossfade(true)
                    .build(),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }
        val coroutine = rememberCoroutineScope()

        if (imageUri != null) {
            var opperationalsuccesfull by remember {
                mutableStateOf(false)
            }
            Button(
                onClick = {
                    coroutine.launch {

                        uploadImageToFirebase(context, imageUri!!)
                    }
                    opperationalsuccesfull=true
                    if (opperationalsuccesfull){
                        navController.popBackStack()
                    }
                })
            {
                Text("Upload Image")
            }
        } else {
            Button(onClick = { launcher.launch("image/*") }) {
                Text("Select Image")
            }
        }
    }
}

private suspend fun uploadImageToFirebase(context: Context, imageUri: Uri) {
    val storageRef = Firebase.storage.reference
    val imagesRef = storageRef.child("images/${imageUri.lastPathSegment}")
    val inputStream = context.contentResolver.openInputStream(imageUri)
    val buffer = ByteArrayOutputStream()

    inputStream?.use { input ->
        buffer.use { output ->
            input.copyTo(output)
        }
    }

    val data = buffer.toByteArray()
    val uploadTask = imagesRef.putBytes(data)

    uploadTask.addOnSuccessListener {
        // Image uploaded successfully
    }.addOnFailureListener {
        // Image upload failed
    }
    imagesRef.downloadUrl.addOnSuccessListener {
        Timber.tag("DownloadImage").d(it.toString())
    }
    val results = uploadTask.continueWithTask {
        imagesRef.downloadUrl
    }.await().toString()
}