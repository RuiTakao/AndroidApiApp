package com.takaobrog.androidapiapp.presentation.todo.todo_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takaobrog.androidapiapp.data.repository.DeviceDataRepositoryImpl
import com.takaobrog.androidapiapp.domain.model.todo.Todo
import com.takaobrog.androidapiapp.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val deviceDataStoreRepository: DeviceDataRepositoryImpl,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val todoId: Int = checkNotNull(savedStateHandle["todoId"])

    private val _todo = MutableLiveData<Todo?>()
    val todo: LiveData<Todo?> = _todo

    init {
        fetchTodo(todoId)
    }

    fun fetchTodo(id: Int) {
        viewModelScope.launch {
            println("detail ${deviceDataStoreRepository.deviceId()}")
            val result = withContext(Dispatchers.IO) { repository.getTodo(id) }
            if (result.isSuccess) {
                val data = result.getOrNull()
                withContext(Dispatchers.Main) { _todo.value = data }
            } else {
                Log.e("TodoDetailViewModel", "Todo get failure")
            }
        }
    }
}