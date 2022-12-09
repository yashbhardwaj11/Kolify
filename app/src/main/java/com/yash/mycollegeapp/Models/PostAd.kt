package com.yash.mycollegeapp.Models

data class PostAd(
    val postedBy : String? = "",
    val adImageUrl : String? ="",
    val adTitle : String? = "",
    val adDescription : String? ="",
    val adPrice : String? = "",
    val postedOn : Long? = 0L
)