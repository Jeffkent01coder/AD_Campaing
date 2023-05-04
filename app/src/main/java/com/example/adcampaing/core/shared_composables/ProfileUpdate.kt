package com.example.adcampaing.core.shared_composables

import android.net.Uri
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.adcampaing.feature_auth.presentation.viewModel.AuthViewModel
import com.example.adcampaing.feature_user.domain.model.User
import com.example.adcampaing.feature_user.presentation.UserViewModel
import com.example.adcampaing.navigations.Screens

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfileUpdate(
    profileViewModel: UserViewModel = hiltViewModel(),
    navController: NavController,
    keyboardController: SoftwareKeyboardController,
    authViewModel: AuthViewModel,
) {

    val toastMessage = profileViewModel.toastMessage.value
//    LaunchedEffect(key1 = toastMessage) {
//        if (toastMessage != "") {
//            SnackbarController(this).showSnackbar(snackbarHostState, toastMessage, "Close")
//        }
//    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    isLoading = profileViewModel.isLoading.value
    var userDataFromFirebase by remember { mutableStateOf(User()) }
    userDataFromFirebase = profileViewModel.userDataStateFromFirebase.value

    var email by remember {
        mutableStateOf("")
    }
    email = userDataFromFirebase.email

    var name by remember { mutableStateOf("") }
    name = userDataFromFirebase.userName

    var surName by remember {
        mutableStateOf("")
    }
    surName = userDataFromFirebase.userSurName
    var bio by remember { mutableStateOf("") }
    bio = userDataFromFirebase.bio

    var phoneNumber by remember { mutableStateOf("") }
    phoneNumber = userDataFromFirebase.userPhoneNumber

    var userDataPictureUrl by remember { mutableStateOf("") }
    userDataPictureUrl = userDataFromFirebase.imageUrl

    val scrollState = rememberScrollState()

    var updatedImage by remember {
        mutableStateOf<Uri?>(null)
    }
    val isUserSignOut = profileViewModel.isUserSignOutState.value
    LaunchedEffect(key1 = isUserSignOut) {
        if (isUserSignOut) {
            navController.popBackStack()
            navController.navigate(Screens.LogIn.route)
        }
    }
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
                    .padding(horizontal = 12.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        ChooseProfilePicFromGallery(userDataPictureUrl) {
                            if (it != null) {
                                profileViewModel.uploadPictureToFirebase(it)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = email, style = MaterialTheme.typography.titleMedium)
                    ProfileTextField(
                        entry = name,
                        hint = "Full Name",
                        onChange = { name = it },
                    )
                    ProfileTextField(surName, "Surname", { surName = it })
                    ProfileTextField(bio, "About You", { bio = it })
                    ProfileTextField(
                        phoneNumber, "Phone Number", { phoneNumber = it },
                        keyboardType = KeyboardType.Phone
                    )
                    Button(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        onClick = {
                            if (name != "") {
                                profileViewModel.updateProfileToFirebase(User(userName = name))
                            }
                            if (surName != "") {
                                profileViewModel.updateProfileToFirebase(User(userSurName = surName))
                            }
                            if (bio != "") {
                                profileViewModel.updateProfileToFirebase(User(bio = bio))
                            }
                            if (phoneNumber != "") {
                                profileViewModel.updateProfileToFirebase(User(userPhoneNumber = phoneNumber))
                            }
                            navController.popBackStack()
                            if(authViewModel.currentUser?.uid == "ycQ2tVRHxLOttJNwdfYL1lxERss2"){
                                navController.navigate(Screens.AdminProfile.route)
                            }else{
                                navController.navigate(Screens.UserProfile.route)
                            }
                        },
                        enabled = updatedImage != null || name != "" || surName != "" || bio != "" || phoneNumber != ""
                    ) {
                        Text(text = "Save Profile", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}