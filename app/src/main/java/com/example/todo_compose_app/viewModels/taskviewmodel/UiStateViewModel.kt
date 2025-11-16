package com.example.todo_compose_app.viewModels.taskviewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UiStateViewModel @Inject constructor() : ViewModel() {


    private val _showBottomSheet = MutableStateFlow(false)
    val showBottomSheet :StateFlow<Boolean> = _showBottomSheet
    fun onOpenBottomSheet() {
        _showBottomSheet.value = true
    }

    fun onCloseBottomSheet() {
        _showBottomSheet.value = false
    }
}