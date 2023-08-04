package com.otmanethedev.oompaloompa.info.data.models

import androidx.annotation.Keep

@Keep
data class FavoriteDto(
    val color: String,
    val food: String,
    val random_string: String,
    val song: String
)