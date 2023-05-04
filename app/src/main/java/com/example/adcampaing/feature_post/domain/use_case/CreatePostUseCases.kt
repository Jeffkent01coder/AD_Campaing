package com.example.adcampaing.feature_post.domain.use_case

import com.example.adcampaing.feature_post.domain.model.Post
import com.example.adcampaing.feature_post.domain.repository.PostRepository
import javax.inject.Inject

class CreatePost @Inject constructor(
private val repository: PostRepository
) {
    suspend operator fun invoke(post: Post) = repository.createPostToFirebase(post)
}