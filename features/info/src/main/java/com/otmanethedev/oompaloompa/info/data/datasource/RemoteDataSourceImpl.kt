package com.otmanethedev.oompaloompa.info.data.datasource

import com.otmanethedev.oompaloompa.info.data.models.OompaLoompaDto
import com.otmanethedev.oompaloompa.info.data.models.OompaLoompasListDto
import com.otmanethedev.oompaloompa.info.data.service.ApiService
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) : RemoteDataSource {

    override suspend fun getOompaLoompasByPage(page: Int): OompaLoompasListDto = apiService.getOompaLoompasByPage(page)

    override suspend fun getOompaLoompaById(id: Int): OompaLoompaDto = apiService.getOompaLoompaById(id)
}
