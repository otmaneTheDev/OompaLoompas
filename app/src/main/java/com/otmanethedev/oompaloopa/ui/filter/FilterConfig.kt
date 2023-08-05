package com.otmanethedev.oompaloopa.ui.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FilterConfig(
    val genderOptions: List<String>,
    val professionOptions: List<String>,
    val previousSelectedGenders: List<String>? = null,
    val previousSelectedProfessions: List<String>? = null
) : Parcelable
