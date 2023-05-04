package com.example.adcampaing.di

import UploadPostUseCase
import com.example.adcampaing.feature_auth.data.AuthRepositoryImpl
import com.example.adcampaing.feature_auth.domain.repository.AuthRepository
import com.example.adcampaing.feature_post.data.repository.PostRepositoryImpl
import com.example.adcampaing.feature_post.domain.repository.PostRepository
import com.example.adcampaing.feature_post.domain.use_case.*
import com.example.adcampaing.feature_user.data.repository.UserRepositoryImpl
import com.example.adcampaing.feature_user.domain.repository.UserRepository
import com.example.adcampaing.feature_user.domain.use_cases.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun providesFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun providesFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun providesFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }


    @Provides
    @Singleton
    fun providesAuthRepository(auth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(auth)
    }

    @Provides
    @Singleton
    fun providesUserRepository(
        storage: FirebaseStorage,
        auth: AuthRepository,
        database: FirebaseDatabase
    ): UserRepository {
        return UserRepositoryImpl(
            storage = storage, auth = auth, database = database
        )
    }

    @Provides
    @Singleton
    fun providesUserUseCases(repository: UserRepository): ProfileScreenUseCases {
        return ProfileScreenUseCases(
            createOrUpdateProfileToFirebase = CreateOrUpdateProfileToFirebase(repository),
            loadProfileFromFirebase = LoadProfileFromFirebase(repository),
            uploadPictureToFirebase = UploadPictureToFirebase(repository)
        )
    }

    @Provides
    @Singleton
    fun providesPostRepository(
        fireStore: FirebaseFirestore,
        authRepository: AuthRepository,
        storage: FirebaseStorage,
        database: FirebaseDatabase
    ): PostRepository {
        return PostRepositoryImpl(
            db = fireStore,
            authRepository = authRepository,
            storage = storage,
            database = database
        )
    }

    @Provides
    @Singleton
    fun providesPostUseCases(repository: PostRepository): PostUseCases {
        return PostUseCases(
            createPost = CreatePost(repository),
            uploadPost = UploadPostUseCase(repository),
            loadPost = LoadPost(repository)
        )
    }
}

