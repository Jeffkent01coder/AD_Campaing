package com.example.adcampaing.feature_auth.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.example.adcampaing.core.utils.Resource

interface AuthRepository {
    val currentUser : FirebaseUser?
    suspend fun login(email:String,password:String): Resource<FirebaseUser>?
    suspend fun signup(email:String,password:String,userName:String): Resource<FirebaseUser>?
    suspend fun logout()
}