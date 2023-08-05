package com.otmanethedev.oompaloopa.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otmanethedev.oompaloopa.ui.MainViewModel.FilterEvent.Idle
import com.otmanethedev.oompaloopa.ui.filter.FilterConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    sealed class FilterEvent {
        object Idle : FilterEvent()
        class Filter(val filterConfig: FilterConfig) : FilterEvent()
    }

    private val _filterEvent: MutableStateFlow<FilterEvent> = MutableStateFlow(Idle)
    val filterEvent: StateFlow<FilterEvent> = _filterEvent.asStateFlow()

    fun handleFilterEvent(event: FilterEvent) {
        viewModelScope.launch {
            _filterEvent.value = event
        }
    }
}