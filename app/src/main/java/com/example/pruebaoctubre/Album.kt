package com.example.pruebaoctubre

import com.squareup.moshi.Json

data class Album(
    @Json(name="userId") val userId: Int,
    @Json(name="id") val id: Int,
    @Json(name="title") val title: String
)