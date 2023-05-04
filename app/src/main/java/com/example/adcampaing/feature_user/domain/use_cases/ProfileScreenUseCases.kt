package com.example.adcampaing.feature_user.domain.use_cases

data class ProfileScreenUseCases(
    val createOrUpdateProfileToFirebase: CreateOrUpdateProfileToFirebase,
    val loadProfileFromFirebase: LoadProfileFromFirebase,
    val uploadPictureToFirebase: UploadPictureToFirebase
)