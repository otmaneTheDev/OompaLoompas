package com.otmanethedev.oompaloopa.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.otmanethedev.oompaloompa.info.domain.models.OompaLoompa
import com.otmanethedev.oompaloompa.info.domain.usecases.GetOompaLoompasUseCase
import com.otmanethedev.oompaloopa.ui.filter.FilterConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine

@HiltViewModel
class ListViewModel @Inject constructor(
    getOompaLoompasUseCase: GetOompaLoompasUseCase
) : ViewModel() {

    private val _filterConfig = MutableStateFlow(FilterConfig(listOf(), listOf()))
    private val filterConfig: StateFlow<FilterConfig> = _filterConfig

    private val oompaLoompas: Flow<PagingData<OompaLoompa>> = getOompaLoompasUseCase.getOompaLoompas().cachedIn(viewModelScope)

    val filteredOompaLoompas: Flow<PagingData<OompaLoompa>> = combine(oompaLoompas, filterConfig) { oompaLoompas, filterConfig ->
        oompaLoompas.filter {
            filterByGenderAndProfession(it, filterConfig)
        }
    }.cachedIn(viewModelScope)

    fun applyFilterConfig(filterConfig: FilterConfig) {
        _filterConfig.value = filterConfig
    }

    private fun filterByGenderAndProfession(item: OompaLoompa, filterConfig: FilterConfig): Boolean {
        val isInProfessions = item.profession in filterConfig.professionOptions || filterConfig.professionOptions.isEmpty()
        val isInGender = item.gender in filterConfig.genderOptions || filterConfig.genderOptions.isEmpty()

        return isInGender && isInProfessions
    }
}
