package com.example.adcampaing.feature_user.domain.repository

import android.net.Uri
import com.example.adcampaing.feature_user.domain.model.User
import com.example.adcampaing.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun uploadPictureToFirebase(url: Uri): Flow<Resource<String>>
    suspend fun createOrUpdateProfileToFirebase(user: User): Flow<Resource<Boolean>>
    suspend fun loadProfileFromFirebase(): Flow<Resource<User>>
}