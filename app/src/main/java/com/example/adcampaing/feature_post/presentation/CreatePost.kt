package com.example.adcampaing.feature_post.presentation

import android.net.Uri
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.adcampaing.core.shared_composables.ChooseProfilePicFromGallery
import com.example.adcampaing.feature_post.domain.model.Post


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreatePost(
    postViewModel: PostViewModel,
    navController: NavController,
    keyboardController: SoftwareKeyboardController
) {

    var isLoading by remember {
        mutableStateOf(false)
    }
    isLoading = postViewModel.isLoading.value
    var postDataFromFirebase by remember { mutableStateOf(Post()) }


    var postDescription by remember { mutableStateOf("") }


    var postImage by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    var updatedImage by remember {
        mutableStateOf<Uri?>(null)
    }
//    val isUserSignOut = postViewModel.isUserSignOutState.value
//    LaunchedEffect(key1 = isUserSignOut) {
//        if (isUserSignOut) {
//            navController.popBackStack()
//            navController.navigate(Screens.LoginScreen.route)
//        }
//    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .imePadding()
    ) {
        Surface(
            modifier = Modifier
                .focusable(true)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { keyboardController.hide() })
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    ChooseProfilePicFromGallery(postImage) {
                        if (it != null) {
                            postViewModel.uploadPictureToFirebase(it)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                ProfileTextField(postDescription, "post description", { postDescription = it })

                Button(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    onClick = {

                        if (postDescription != "") {
                            postViewModel.createPostToFirebase(Post(postDescription = postDescription))
                        }
//                            navController.popBackStack()
//                            navController.navigate(Screens.HomeScreen.route)
                    },
                    enabled = postDescription != ""
                ) {
                    Text(text = "Upload post", style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(50.dp))

            }
        }
    }
}

@Composable
fun ProfileTextField(
    entry: String,
    hint: String,
    onChange: (String) -> Unit = {},
//    onFocusChange: (Boolean) -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var isNameChange by remember {
        mutableStateOf(false)
    }
    var isFocusChange by remember {
        mutableStateOf(false)
    }
    var text by remember {
        mutableStateOf("")
    }
    text = entry

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        label = { Text(text = hint) },
        value = text,
        onValueChange = {
            text = it
            onChange(it)
            isNameChange = true
        },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    )
}

@Composable
fun LogOutCustomText(
    onClick: () -> Unit = {}
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
    ) {
        Text(
            text = "Log Out",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onError
        )
    }
}