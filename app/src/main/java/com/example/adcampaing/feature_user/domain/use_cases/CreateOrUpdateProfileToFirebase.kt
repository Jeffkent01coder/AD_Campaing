package com.example.adcampaing.feature_user.domain.use_cases

import com.example.adcampaing.feature_user.domain.model.User
import com.example.adcampaing.feature_user.domain.repository.UserRepository

class CreateOrUpdateProfileToFirebase(
    private val profileScreenRepository: UserRepository
) {
    suspend operator fun invoke(user: User) =
        profileScreenRepository.createOrUpdateProfileToFirebase(user)
}