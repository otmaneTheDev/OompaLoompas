package com.otmanethedev.oompaloopa.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otmanethedev.oompaloompa.info.domain.models.OompaLoompa
import com.otmanethedev.oompaloompa.info.domain.usecases.GetOompaLoompaByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getOompaLoompaByIdUseCase: GetOompaLoompaByIdUseCase
) : ViewModel() {

    sealed class DetailUiState {
        object Idle : DetailUiState()
        object Loading : DetailUiState()
        class Error(val error: Throwable) : DetailUiState()
        class Success(val oompaLoompa: OompaLoompa) : DetailUiState()
    }

    sealed class DetailEvent {
        class GetById(val id: Int) : DetailEvent()
    }

    private val _uiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState.Idle)
    val uiState: StateFlow<DetailUiState> get() = _uiState.asStateFlow()

    fun handleEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.GetById -> fetchOompaLoompaDetailById(event.id)
        }
    }

    private fun fetchOompaLoompaDetailById(id: Int) {
        Log.e("XXXX", "fetchOompaLoompaDetailById: $id", );
        viewModelScope.launch {
            getOompaLoompaByIdUseCase(id).onSuccess {
                _uiState.value = DetailUiState.Success(it)
            }.onFailure {
                Log.e("XXXX", "fetchOompaLoompaDetailById: $it", );
                _uiState.value = DetailUiState.Error(it)
            }
        }
    }
}