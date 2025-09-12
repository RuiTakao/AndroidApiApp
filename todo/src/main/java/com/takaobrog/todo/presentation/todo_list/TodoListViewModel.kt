package com.takaobrog.todo.presentation.todo_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takaobrog.todo.data.Todo
import com.takaobrog.todo.domain.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    private val _todoList = MutableLiveData<List<Todo>>()
    val todoList: LiveData<List<Todo>> = _todoList

    init {
        fetchTodoList()
    }

    fun fetchTodoList() {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { repository.getTodos() }
                if (result.isSuccess) {
                    val list = result.getOrNull().orEmpty()
                    Log.d("success", list.toString())
                    withContext(Dispatchers.Main) { _todoList.value = list }
                } else {
                    Log.e("error", "response error")
                }
            } catch (e: IOException) {
                Log.e("error", "response error")
            }
        }
    }
}
