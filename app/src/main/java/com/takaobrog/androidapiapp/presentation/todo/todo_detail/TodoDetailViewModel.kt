package com.takaobrog.androidapiapp.presentation.todo.todo_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takaobrog.androidapiapp.domain.model.todo.GetTodoResponse
import com.takaobrog.androidapiapp.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private val TAG = TodoDetailViewModel::class.simpleName

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val todoId: Int = checkNotNull(savedStateHandle["todoId"])

    private val _Get_todoResponse = MutableLiveData<GetTodoResponse?>()
    val getTodoResponse: LiveData<GetTodoResponse?> = _Get_todoResponse

    private val _reloading = MutableLiveData(false)
    val reloading: LiveData<Boolean> = _reloading

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            fetchTodo(todoId)
        }
    }

    fun reloading() {
        viewModelScope.launch {
            fetchTodo(todoId)
        }
    }

    fun update(title: String, content: String) {
        viewModelScope.launch {
            val res = repository.update(todoId, title, content)
            if (res.isSuccess) {
                fetchTodo(todoId)
            } else {
                Log.e(TAG, "[update] update todo failure")
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            val res = repository.delete(todoId)
            if (res.isFailure) Log.e(TAG, "[delete] delete todo failure")
        }
    }

    private suspend fun fetchTodo(id: Int) {
        val result = withContext(Dispatchers.IO) { repository.getTodo(id) }
        if (result.isSuccess) {
            val data = result.getOrNull()
            withContext(Dispatchers.Main) { _Get_todoResponse.value = data }
        } else {
            Log.e("TodoDetailViewModel", "Todo get failure")
        }
    }
}