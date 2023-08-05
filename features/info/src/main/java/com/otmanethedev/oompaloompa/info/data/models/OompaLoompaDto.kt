package com.otmanethedev.oompaloompa.info.data.models

import androidx.annotation.Keep
import com.otmanethedev.oompaloompa.info.domain.models.OompaLoompa

@Keep
data class OompaLoompaDto(
    val id: Int,
    val age: Int?,
    val country: String?,
    val email: String?,
    val favorite: FavoriteDto?,
    val first_name: String?,
    val last_name: String?,
    val gender: String?,
    val height: Int?,
    val image: String?,
    val profession: String?,
    val quota: String?,
    val description: String?
)

fun OompaLoompaDto.toDomain() = OompaLoompa(
    id,
    age,
    country,
    email,
    first_name,
    last_name,
    gender,
    height,
    image,
    profession,
    quota,
    description
)
