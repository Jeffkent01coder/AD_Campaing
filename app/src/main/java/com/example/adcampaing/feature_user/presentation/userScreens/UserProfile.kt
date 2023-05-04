package com.example.adcampaing.feature_user.presentation.userScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.adcampaing.core.shared_composables.AllProfile
import com.example.adcampaing.feature_auth.presentation.viewModel.AuthViewModel
import com.example.adcampaing.feature_user.domain.model.User
import com.example.adcampaing.feature_user.presentation.UserViewModel
import com.example.adcampaing.navigations.BottomNavUserMenu
import com.example.adcampaing.navigations.Screens


@Composable
fun UserProfile(
    navController: NavController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel
) {
    var userDataFromFirebase by remember { mutableStateOf(User()) }
    userDataFromFirebase = userViewModel.userDataStateFromFirebase.value
    Scaffold(
        bottomBar = { BottomNavUserMenu(navController) },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {

                        Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = "Back")
                    }
                },
                title = {
                    Text(
                        text = "Profile",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        fontFamily = FontFamily.Monospace
                    )
                },
                backgroundColor = Color.Transparent,
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screens.ProfileUpdate.route)
                    }) {
                        Icon(imageVector = Icons.Default.EditNote, contentDescription = "Edit Profile")
                    }
                }

            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .background(Color(0XFFEAFBFF))
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .fillMaxSize()
        ) {
            item {
                AllProfile(authViewModel, navController, userDataFromFirebase.imageUrl,userViewModel)
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                ) {

                }
            }
        }
    }
}



