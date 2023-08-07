package com.otmanethedev.oompaloompa.info.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.otmanethedev.oompaloompa.info.data.datasource.OompaLoompasPagingSource
import com.otmanethedev.oompaloompa.info.domain.models.OompaLoompa
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetOompaLoompasUseCase @Inject constructor(
    private val oompaLoompasPagingSource: OompaLoompasPagingSource,
) {

    fun getOompaLoompas(): Flow<PagingData<OompaLoompa>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                oompaLoompasPagingSource
            }
        ).flow
    }

    companion object {
        const val PAGE_SIZE = 25
    }
}
