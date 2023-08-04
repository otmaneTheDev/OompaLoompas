package com.otmanethedev.oompaloompa.info.domain.repository

import com.otmanethedev.oompaloompa.info.domain.models.OompaLoompa
import com.otmanethedev.oompaloompa.info.domain.models.OompaLoompasInfo

interface OompaLoompasRepository {
    suspend fun getOompaLoompasByPage(page: Int): Result<OompaLoompasInfo>
    suspend fun getOompaLoompaById(id: Int): Result<OompaLoompa>
}