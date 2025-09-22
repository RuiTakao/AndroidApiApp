package com.takaobrog.androidapiapp.presentation.todo.todo_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takaobrog.androidapiapp.domain.repository.TodoRepository
import com.takaobrog.androidapiapp.presentation.todo.component.dialog.TodoAlertDialog
import com.takaobrog.androidapiapp.presentation.todo.component.dialog.TodoAlertDialogEvent
import com.takaobrog.androidapiapp.domain.model.todo.TodoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository,
) : ViewModel() {
    private val _todo = MutableLiveData<List<TodoUiModel>>()
    val todo: LiveData<List<TodoUiModel>> = _todo

    private val _reloading = MutableLiveData(false)
    val reloading: LiveData<Boolean> = _reloading

    private val _dialogEvent = MutableLiveData<TodoAlertDialogEvent<TodoAlertDialog>>()
    val dialogEvent: LiveData<TodoAlertDialogEvent<TodoAlertDialog>> = _dialogEvent

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            fetchTodoList()
        }
    }

    fun reloading() {
        viewModelScope.launch {
            _reloading.value = false
            fetchTodoList()
        }
    }

    fun updateDone(id: Int, isDone: Boolean) {
        viewModelScope.launch {
            val res = repository.updateDone(id, isDone)
            if (res.isFailure) {
                onApiError("Todoチェックの更新エラー")
            }
        }
    }

    private suspend fun fetchTodoList() {
        val result = withContext(Dispatchers.IO) { repository.getTodoList() }
        if (result.isSuccess) {
            val list = result.getOrNull().orEmpty()
            withContext(Dispatchers.Main) { _todo.value = list }
        } else {
            onApiError("Todo一覧、読み込みエラー")
        }
    }

    private fun onApiError(msg: String) {
        _dialogEvent.value = TodoAlertDialogEvent(
            TodoAlertDialog(
                title = msg,
                // TODO 適切なメッセージに書き換え
                message = "500エラー",
            )
        )
    }
}
