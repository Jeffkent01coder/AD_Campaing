package com.example.adcampaing.core.shared_composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.adcampaing.R
import com.example.adcampaing.feature_auth.presentation.viewModel.AuthViewModel
import com.example.adcampaing.feature_user.presentation.UserViewModel
import com.example.adcampaing.navigations.Screens


@Composable
fun AllProfile(
    authViewModel: AuthViewModel,
    navController: NavController,
    profile: String,
    userViewModel: UserViewModel
) {
    val userData = userViewModel.userDataStateFromFirebase.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Column(
           horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userData.imageUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(100.dp),
                contentDescription = null
            )
            Text(
                text = userData.userName,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = userData.email,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )

        }
        Spacer(modifier = Modifier.height(20.dp))
        Logout(authViewModel, navController)

    }
}


@Composable
fun Pic(
    authViewModel: AuthViewModel,
    profile: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        authViewModel.currentUser?.displayName?.let { username ->
            Text(
                text = username,
                fontWeight = FontWeight.ExtraBold
            )
        }
        authViewModel.currentUser?.email?.let {
            Text(
                text = it
            )
        }
    }
}

@Composable
fun Logout(
    authViewModel: AuthViewModel,
    navController: NavController
) {
    Button(
        onClick = {
            authViewModel.signOut()
            navController.popBackStack()
            navController.navigate(Screens.LogIn.route)
        },
        modifier = Modifier
            .fillMaxWidth(7f)
            .padding(start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF5FD8E7),
            contentColor = Color.White
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_logout_24),
            contentDescription = "Logout"
        )
        Text(
            text = "Logout",
            modifier = Modifier
                .padding(6.dp),
            color = Color(0xFFFFFFFF),
            fontSize = 18.sp
        )

    }
}

