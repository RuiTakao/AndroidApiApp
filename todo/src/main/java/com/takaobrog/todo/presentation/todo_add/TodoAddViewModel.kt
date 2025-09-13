package com.takaobrog.todo.presentation.todo_add

import androidx.lifecycle.ViewModel
import com.takaobrog.todo.data.Todo
import com.takaobrog.todo.domain.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TodoAddViewModel @Inject constructor(
    private val repository: TodoRepository,
) : ViewModel() {
    val df = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPAN)

    suspend fun createTodo(title: String, content: String, deviceId: String): Result<Unit> {
        val todo = Todo(
            title = title,
            content = content,
            done = false,
            deviceId = deviceId,
            createdAt = df.format(Date()),
        )

        return repository.createTodo(todo)
    }
}