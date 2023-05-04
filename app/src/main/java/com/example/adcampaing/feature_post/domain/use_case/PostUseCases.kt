package com.example.adcampaing.feature_post.domain.use_case

import UploadPostUseCase

data class PostUseCases(
    val createPost: CreatePost,
    val uploadPost: UploadPostUseCase,
    val loadPost: LoadPost,
//    val createPostRoomToFirebase: CreatePostRoomToFirebase
)