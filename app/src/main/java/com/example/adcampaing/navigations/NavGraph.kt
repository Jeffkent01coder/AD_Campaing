package com.example.adcampaing.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.adcampaing.core.shared_composables.ProfileUpdate
import com.example.adcampaing.feature_auth.presentation.sharedScreens.Login
import com.example.adcampaing.feature_auth.presentation.sharedScreens.Register
import com.example.adcampaing.feature_auth.presentation.sharedScreens.Splash
import com.example.adcampaing.feature_auth.presentation.viewModel.AuthViewModel
import com.example.adcampaing.feature_post.presentation.CreatePost
import com.example.adcampaing.feature_post.presentation.PostViewModel
import com.example.adcampaing.feature_user.presentation.UserViewModel
import com.example.adcampaing.feature_user.presentation.adminScreens.AdPost
import com.example.adcampaing.feature_user.presentation.adminScreens.AdminProfile
import com.example.adcampaing.feature_user.presentation.adminScreens.Dashboard
import com.example.adcampaing.feature_user.presentation.userScreens.UserHome
import com.example.adcampaing.feature_user.presentation.userScreens.UserProfile

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    postViewModel: PostViewModel = hiltViewModel(),
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
) {
    NavHost(navController, startDestination = Screens.Splash.route) {
        composable(Screens.Splash.route) {
            Splash(navController)
        }
        composable(Screens.UserHome.route) {
            UserHome(navController,postViewModel)
        }
        composable(Screens.UserProfile.route) {
            UserProfile(navController, authViewModel, userViewModel)
        }
        composable(Screens.CreatePost.route) {
            if (keyboardController != null) {
                CreatePost(postViewModel, navController, keyboardController)
            }
        }
        composable(Screens.ProfileUpdate.route) {
            if (keyboardController != null) {
                ProfileUpdate(userViewModel, navController, keyboardController, authViewModel)
            }
        }
        composable(Screens.AddPost.route) {
            AdPost(navController, postViewModel)
        }
        composable(Screens.DashBoard.route) {
            Dashboard(navController)
        }
        composable(Screens.AdminProfile.route) {
            AdminProfile(navController, authViewModel, userViewModel)
        }
        composable(Screens.LogIn.route) {
            Login(authViewModel, navController)
        }
        composable(Screens.Register.route) {
            Register(authViewModel, navController)
        }
    }
}