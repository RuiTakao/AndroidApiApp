package com.takaobrog.androidapiapp.presentation.todo.todo_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takaobrog.androidapiapp.domain.model.todo.TodoUiModel
import com.takaobrog.androidapiapp.domain.repository.TodoRepository
import com.takaobrog.androidapiapp.presentation.todo.component.dialog.TodoAlertDialog
import com.takaobrog.androidapiapp.presentation.todo.component.dialog.TodoAlertDialogEvent
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

    private val _todo = MutableLiveData<TodoUiModel>()
    val todo: LiveData<TodoUiModel> = _todo

    private val _reloading = MutableLiveData(false)
    val reloading: LiveData<Boolean> = _reloading

    private val _dialogEvent = MutableLiveData<TodoAlertDialogEvent<TodoAlertDialog>>()
    val dialogEvent: LiveData<TodoAlertDialogEvent<TodoAlertDialog>> = _dialogEvent

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

    fun update(title: String, memo: String) {
        viewModelScope.launch {
            val res = repository.update(todoId, title, memo)
            if (res.isSuccess) {
                fetchTodo(todoId)
            } else {
                Log.e(TAG, "[update] update todo failure")
            }
        }
    }

    fun updateDone(isDone: Boolean) {
        viewModelScope.launch {
            val res = repository.updateDone(todoId, isDone)
            if (res.isFailure) {
                onApiError("Todoチェックの更新エラー")
            }
        }
    }

    suspend fun delete() {
        val res = repository.delete(todoId)
        if (res.isFailure) Log.e(TAG, "[delete] delete todo failure")
    }

    private suspend fun fetchTodo(id: Int) {
        val result = withContext(Dispatchers.IO) { repository.getTodo(id) }
        if (result.isSuccess) {
            val data = result.getOrNull()
            data?.let {
                withContext(Dispatchers.Main) { _todo.value = it }
            }
        } else {
            Log.e(TAG, "Todo get failure")
            onApiError(result.isFailure.toString())
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