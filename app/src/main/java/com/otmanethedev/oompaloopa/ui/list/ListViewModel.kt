package com.otmanethedev.oompaloopa.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otmanethedev.oompaloompa.info.domain.models.OompaLoompa
import com.otmanethedev.oompaloompa.info.domain.usecases.GetOompaLoompasByPageUseCase
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
        class Success(val items: List<OompaLoompa>) : ListUiState()
    }

    private val _uiState: MutableStateFlow<ListUiState> = MutableStateFlow(ListUiState.Idle)
    val uiState: StateFlow<ListUiState> get() = _uiState.asStateFlow()

    private var currentPage: Int = 1

    init {
        fetchOompaloompasByPageUseCase(currentPage)
    }

    private fun fetchOompaloompasByPageUseCase(page: Int) {
        viewModelScope.launch {
            getOompaLoompasByPageUseCase.invoke(page).onSuccess {
                _uiState.value = ListUiState.Success(it.oompaLoompas)
                currentPage = it.current
            }.onFailure {
                _uiState.value = ListUiState.Error(it)
            }
        }
    }
}
