package com.takaobrog.androidapiapp.presentation.todo.todo_add

import androidx.lifecycle.ViewModel
import com.takaobrog.androidapiapp.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoAddViewModel @Inject constructor(
    private val repository: TodoRepository,
) : ViewModel() {
    suspend fun createTodo(title: String, memo: String): Result<Unit> {
        return repository.create(title = title, memo = memo)
    }

    fun validMessage(title: String, memo: String): String {
        var message = ""
        var titleMessage = ""
        var memoMessage = ""
        if (title.isEmpty()) {
            titleMessage = "タイトルを入力してください"
        }
        if (title.length >= 10) {
            titleMessage = "タイトルは10文字以下で入力してください"
        }
        if (memo.length >= 140) {
            memoMessage = "内容は140文字以下で入力してください"
        }
        if (!titleMessage.isEmpty()) {
            message = titleMessage
            if (!message.isEmpty()) {
                message += "\n"
            }
            message += memoMessage
        }
        return message
    }
}