package com.otmanethedev.oompaloompa.info.data.models

import androidx.annotation.Keep

@Keep
data class OompaLoompasListDto(
    val current: Int,
    val results: List<OompaLoompaDto>,
    val total: Int
)

fun OompaLoompasListDto.toDomain() = com.otmanethedev.oompaloompa.info.domain.models.OompaLoompasInfo(
    current,
    results.map { it.toDomain() },
    total
)