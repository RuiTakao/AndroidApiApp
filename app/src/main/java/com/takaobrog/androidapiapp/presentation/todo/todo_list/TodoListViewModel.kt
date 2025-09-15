package com.takaobrog.androidapiapp.presentation.todo.todo_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takaobrog.androidapiapp.domain.model.Todo
import com.takaobrog.androidapiapp.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository,
) : ViewModel() {
    private val _todoList = MutableLiveData<List<Todo>>()
    val todoList: LiveData<List<Todo>> = _todoList

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            fetchTodoList()
        }
    }

    suspend fun fetchTodoList() {
        val result = withContext(Dispatchers.IO) { repository.getTodoList() }
        if (result.isSuccess) {
            val list = result.getOrNull().orEmpty()
            withContext(Dispatchers.Main) { _todoList.value = list }
        }
    }
}
