package com.example.adcampaing.feature_post.presentation

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adcampaing.core.utils.Resource
import com.example.adcampaing.feature_post.domain.model.Post
import com.example.adcampaing.feature_post.domain.use_case.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
) : ViewModel() {
    var toastMessage = mutableStateOf("")
        private set
    var isLoading = mutableStateOf(false)
        private set

    var postDataStateFromFirebase = mutableStateOf(Post())
        private set

    init {
        loadPostFromFirebase()
    }
    fun uploadPictureToFirebase(uri: Uri) {
        viewModelScope.launch {
            postUseCases.uploadPost(uri).collect {response->
                when(response){
                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                    is Resource.Success -> {
                        //Picture Uploaded
                        isLoading.value = false
                        response.data?.let { it ->
                            createPostToFirebase(Post(postImage = it))
                        }
                    }
                    is Resource.Error -> {}
                }

            }
        }
    }

    fun createPostToFirebase(post: Post) {
        viewModelScope.launch {
            postUseCases.createPost(post).collect { response ->
                when(response){
                    is Resource.Loading -> {
                        toastMessage.value = ""
                        isLoading.value = true
                    }
                    is Resource.Success -> {
                        isLoading.value = false
                        if(response.data == true){
                            toastMessage.value = "Profile Updated"
                        }else{
                            toastMessage.value = "Profile Saved"
                        }
                    }
                    is Resource.Error -> {
                        toastMessage.value = "Update Failed"
                    }
                }
            }
        }
    }


    private fun loadPostFromFirebase() {
        viewModelScope.launch {
            postUseCases.loadPost().collect { response ->
                when(response){
                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                    is Resource.Success -> {
                        postDataStateFromFirebase.value = response.data!!
                        delay(500)
                        isLoading.value = false
                    }
                    is Resource.Error -> {}
                }
            }
        }
    }

//    fun createPostRoom(){
//        viewModelScope.launch {
//            postUseCases.createPostRoomToFirebase().collect{result->
//                when(result){
//                    is Resource.Loading -> {
//                        isLoading.value = true
//                    }
//                    is Resource.Success -> {
//                        delay(500)
//                    }
//                    is Resource.Error -> {}
//                }
//
//            }
//        }
//    }
}

