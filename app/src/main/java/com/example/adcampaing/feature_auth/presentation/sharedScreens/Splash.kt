package com.example.adcampaing.feature_auth.presentation.sharedScreens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.adcampaing.feature_auth.presentation.viewModel.AuthViewModel
import com.example.adcampaing.navigations.Screens
import com.example.adcampaing.ui.theme.ADCampaingTheme
import kotlinx.coroutines.delay
import timber.log.Timber


@Composable
fun Splash(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val scale = remember{
        Animatable(0f)
    }
    LaunchedEffect(key1 = true){
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(3000L)

        Timber.e("${authViewModel.currentUser?.uid}")

        if (authViewModel.currentUser?.uid == null){
            navController.navigate(route = Screens.Register.route){
                popUpTo(Screens.Splash.route) {
                    inclusive = true
                }
            }


        }
        else{
            if (authViewModel.currentUser?.uid == "ycQ2tVRHxLOttJNwdfYL1lxERss2"){
                navController.navigate(route = Screens.DashBoard.route){
                    popUpTo(Screens.Splash.route) {
                        inclusive = true
                    }
                }
            }else {
                navController.navigate(route = Screens.UserHome.route) {
                    popUpTo(Screens.Splash.route) {
                        inclusive = true
                    }
                }
            }

        }
    }
    Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0XFFEAFBFF))
        ){
            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(50.dp),
                painter = painterResource(id = com.example.adcampaing.R.drawable.ad),
                contentDescription = "Logo"
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                modifier = Modifier.padding(top = 300.dp),
                text = "AD CAMPAIGNER",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                modifier = Modifier
                    .padding(top = 500.dp, start = 20.dp, end = 20.dp),
                text = "Lets create awareness to the whole world and for the whole world to make it  a better place, a safer place and make work easier through Ads ",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(6.dp))
            
            Button(onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(top = 700.dp, start = 15.dp)
                    .align(Alignment.CenterStart)
                ,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Get Started")

            }
            
        }


    }

}
