//package com.example.adcampaing.feature_auth.presentation.viewModel
//
//import androidx.compose.runtime.State
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.adcampaing.feature_user.domain.model.User
//import com.example.adcampaing.feature_user.domain.use_cases.UserUseCases
//import com.google.firebase.auth.FirebaseAuth
//import com.example.adcampaing.core.utils.Resource
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class UserViewModel @Inject constructor(
//    private val auth: FirebaseAuth,
//    private val userUseCases: UserUseCases
//) : ViewModel() {
//
//    private val userId = auth.currentUser?.uid
//    private val _getUserData = mutableStateOf<Resource<User?>>(Resource.Success(null))
//    val getUserData: State<Resource<User?>> = _getUserData
//
//    private val _setUserData = mutableStateOf<Resource<Boolean>>(Resource.Success(false))
//    val setUserData: State<Resource<Boolean>> = _setUserData
//
//    fun getUserInfo() {
//        if (userId != null) {
//            viewModelScope.launch {
//                userUseCases.getUserDetailsUseCase(userId = userId).collect {
//                    _getUserData.value = it
//                }
//            }
//        }
//    }
//
//    fun setUserInfo(
//        name: String,
//        userName: String,
//        bio: String,
//        webUrl: String,
//        ) {
//        viewModelScope.launch {
//            userUseCases.setUserDetailsUseCase(
//                name = name,
//                userName = userName,
//                bio = bio,
//                webUrl = webUrl,
//
//            )
//                .collect {
//                    _setUserData.value = it
//                }
//        }
//    }
//}