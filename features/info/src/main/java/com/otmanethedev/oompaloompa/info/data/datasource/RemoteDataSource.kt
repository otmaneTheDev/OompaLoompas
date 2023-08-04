package com.otmanethedev.oompaloompa.info.data.datasource

import com.otmanethedev.oompaloompa.info.data.models.OompaLoompaDto
import com.otmanethedev.oompaloompa.info.data.models.OompaLoompasListDto

interface RemoteDataSource {
    suspend fun getOompaLoompasByPage(page: Int): OompaLoompasListDto
    suspend fun getOompaLoompaById(id: Int): OompaLoompaDto
}
