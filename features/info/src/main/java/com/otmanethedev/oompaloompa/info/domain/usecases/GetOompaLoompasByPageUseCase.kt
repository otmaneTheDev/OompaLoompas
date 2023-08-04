package com.otmanethedev.oompaloompa.info.domain.usecases

import com.otmanethedev.oompaloompa.info.domain.repository.OompaLoompasRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetOompaLoompasByPageUseCase @Inject constructor(
    private val repository: OompaLoompasRepository
) {

    suspend operator fun invoke(page: Int) = withContext(Dispatchers.IO) {
        repository.getOompaLoompasByPage(page)
    }
}