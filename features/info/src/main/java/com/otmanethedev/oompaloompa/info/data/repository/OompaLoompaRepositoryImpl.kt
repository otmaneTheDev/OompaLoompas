package com.otmanethedev.oompaloompa.info.data.repository

import com.otmanethedev.oompaloompa.info.data.datasource.RemoteDataSource
import com.otmanethedev.oompaloompa.info.data.models.toDomain
import com.otmanethedev.oompaloompa.info.domain.repository.OompaLoompasRepository
import javax.inject.Inject

class OompaLoompaRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : OompaLoompasRepository {

    override suspend fun getOompaLoompasByPage(page: Int): Result<com.otmanethedev.oompaloompa.info.domain.models.OompaLoompasInfo> {
        return try {
            val response = remoteDataSource.getOompaLoompasByPage(page)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getOompaLoompaById(id: Int): Result<com.otmanethedev.oompaloompa.info.domain.models.OompaLoompa> {
        return try {
            val response = remoteDataSource.getOompaLoompaById(id)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}