package com.example.adcampaing.navigations


sealed class Screens(val route: String) {
    object LogIn : Screens("LogIn_screen")
    object UserProfile : Screens("user_profile")
    object AddPost : Screens("AddPost_screen")
    object CreatePost : Screens("CreatePost_screen")
    object DashBoard : Screens("Dashboard_screen")
    object Splash : Screens("Splash_screen")
    object UserHome : Screens("UserHome_screen")
    object AdminProfile : Screens("AdminProfile_screen")
    object Register : Screens("Register_screen")
    object ProfileUpdate : Screens("Profile_screen")
}
