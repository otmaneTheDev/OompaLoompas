package com.otmanethedev.oompaloopa.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otmanethedev.oompaloompa.info.domain.models.OompaLoompa
import com.otmanethedev.oompaloompa.info.domain.usecases.GetOompaLoompasByPageUseCase
import com.otmanethedev.oompaloopa.ui.detail.DetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getOompaLoompasByPageUseCase: GetOompaLoompasByPageUseCase
) : ViewModel() {

    sealed class ListUiState {
        object Idle : ListUiState()
        object Loading : ListUiState()
        class Error(val error: Throwable) : ListUiState()
        class Success(val page: Int, val items: List<OompaLoompa>) : ListUiState()
    }

    sealed class ListEvent {
        object LoadMoreItems : ListEvent()
    }

    private val _uiState: MutableStateFlow<ListUiState> = MutableStateFlow(ListUiState.Idle)
    val uiState: StateFlow<ListUiState> get() = _uiState.asStateFlow()

    private var currentPage: Int = ONE
    private var totalPages: Int = ONE

    init {
        fetchOompaloompasByPageUseCase(currentPage)
    }

    fun handleEvent(event: ListEvent) {
        when (event) {
            ListEvent.LoadMoreItems -> handleLoadMoreItems()
        }
    }

    private fun fetchOompaloompasByPageUseCase(page: Int) {
        viewModelScope.launch {
            _uiState.value = ListUiState.Loading
            getOompaLoompasByPageUseCase.invoke(page).onSuccess {
                _uiState.value = ListUiState.Success(it.current, it.oompaLoompas)
                currentPage = it.current
                totalPages = it.total
            }.onFailure {
                _uiState.value = ListUiState.Error(it)
            }
        }
    }

    private fun handleLoadMoreItems() {
        if (currentPage == totalPages) return
        currentPage += ONE
        fetchOompaloompasByPageUseCase(currentPage)
    }

    companion object {
        const val ONE = 1
    }
}
