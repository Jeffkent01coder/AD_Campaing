package com.example.adcampaing.feature_user.presentation

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adcampaing.feature_user.domain.model.User
import com.example.adcampaing.feature_user.domain.use_cases.ProfileScreenUseCases
import com.example.adcampaing.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val useCases: ProfileScreenUseCases
) : ViewModel() {
    var toastMessage = mutableStateOf("")
        private set

    var isLoading = mutableStateOf(false)
        private set

    var isUserSignOutState = mutableStateOf(false)
        private set

    var userDataStateFromFirebase = mutableStateOf(User())
        private set

    init {
        loadProfileFromFirebase()
    }

    //PUBLIC FUNCTIONS

    fun uploadPictureToFirebase(uri: Uri) {
        viewModelScope.launch {
            useCases.uploadPictureToFirebase(uri).collect { response ->
                when (response) {
                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                    is Resource.Success -> {
                        //Picture Uploaded
                        isLoading.value = false
                        response.data?.let { User(imageUrl = it) }?.let {
                            updateProfileToFirebase(
                                it
                            )
                        }
                    }
                    is Resource.Error -> {}
                }

            }
        }
    }

    fun updateProfileToFirebase(myUser: User) {
        viewModelScope.launch {
            useCases.createOrUpdateProfileToFirebase(myUser).collect { response ->
                when (response) {
                    is Resource.Loading -> {
                        toastMessage.value = ""
                        isLoading.value = true
                    }
                    is Resource.Success -> {
                        isLoading.value = false
                        if (response.data == true) {
                            toastMessage.value = "Profile Updated"
                        } else {
                            toastMessage.value = "Profile Saved"
                        }
                        loadProfileFromFirebase()
                    }
                    is Resource.Error -> {
                        toastMessage.value = "Update Failed"
                    }
                }
            }
        }
    }


    //PRIVATE FUNCTIONS
    private fun loadProfileFromFirebase() {
        viewModelScope.launch {
            useCases.loadProfileFromFirebase().collect { response ->
                when (response) {
                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                    is Resource.Success -> {
                        userDataStateFromFirebase.value = response.data!!
                        delay(500)
                        isLoading.value = false
                    }
                    is Resource.Error -> {}
                }
            }
        }
    }
}