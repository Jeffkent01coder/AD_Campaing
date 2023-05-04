package com.example.adcampaing.feature_post.domain.use_case

import com.example.adcampaing.feature_post.domain.repository.PostRepository
import javax.inject.Inject

class LoadPost @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke()=
        repository.loadPostFromFirebase()
}