package com.otmanethedev.oompaloompa.info.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OompaLoompa(
    val id: Int,
    val age: Int?,
    val country: String?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val gender: String?,
    val height: Int?,
    val image: String?,
    val profession: String?,
    val quota: String?,
    val description: String?
) : Parcelable
