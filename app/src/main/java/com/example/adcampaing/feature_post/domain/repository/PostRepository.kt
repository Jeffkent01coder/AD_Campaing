package com.example.adcampaing.feature_post.domain.repository

import android.net.Uri
import com.example.adcampaing.core.utils.Resource
import com.example.adcampaing.feature_post.domain.model.Post
import kotlinx.coroutines.flow.Flow


interface PostRepository {


    suspend fun uploadPictureToFirebase(url: Uri): Flow<Resource<String>>
    //    suspend fun createPostRoomToFirebase(): Flow<Resource<String>>
    suspend fun createPostToFirebase(post: Post): Flow<Resource<Boolean>>
    suspend fun loadPostFromFirebase(): Flow<Resource<Post>>
}