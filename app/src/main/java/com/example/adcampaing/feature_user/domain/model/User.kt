package com.example.adcampaing.feature_user.domain.model

data class User(
    var name: String = "",
    var userName: String = "",
    var userSurName: String = "",
    var profileUUID: String = "",
    var email: String = "",
    var userPhoneNumber: String = "",
    var password: String = "",
    var imageUrl: String = "",
    var following: List<String> = emptyList(),
    var followers: List<String> = emptyList(),
    var totalPost: String = "",
    var url: String = "",
    var webUrl: String = "",
    var bio: String = "",
)