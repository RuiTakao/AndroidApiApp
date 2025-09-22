package com.takaobrog.androidapiapp.presentation.todo.todo_add

import androidx.lifecycle.ViewModel
import com.takaobrog.androidapiapp.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoAddViewModel @Inject constructor(
    private val repository: TodoRepository,
) : ViewModel() {
    suspend fun createTodo(title: String, content: String): Result<Unit> {
        return repository.create(title = title, content = content)
    }

    fun validMessage(title: String, content: String): String {
        var message = ""
        var titleMessage = ""
        var contentMessage = ""
        if (title.isEmpty()) {
            titleMessage = "タイトルを入力してください"
        }
        if (title.length >= 10) {
            titleMessage = "タイトルは10文字以下で入力してください"
        }
        if (content.isEmpty()) {
            contentMessage = "内容を入力してください"
        }
        if (content.length >= 140) {
            contentMessage = "内容は140文字以下で入力してください"
        }
        if (!titleMessage.isEmpty() || !contentMessage.isEmpty()) {
            message = titleMessage
            if (!message.isEmpty()) {
                message += "\n"
            }
            message += contentMessage
        }
        return message
    }
}