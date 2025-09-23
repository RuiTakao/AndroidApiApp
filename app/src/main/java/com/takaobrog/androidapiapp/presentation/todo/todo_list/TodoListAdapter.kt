package com.takaobrog.androidapiapp.presentation.todo.todo_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.takaobrog.androidapiapp.R
import com.takaobrog.androidapiapp.databinding.CellTodoListBinding
import com.takaobrog.androidapiapp.domain.model.todo.TodoUiModel

class TodoListAdapter : ListAdapter<TodoUiModel, TodoListAdapter.VH>(DIFF) {

    interface OnTodoCellClickListener {
        fun onItemClick(todo: TodoUiModel)
    }

    interface OnTodoCellCheckDoneListener {
        fun onItemCheck(id: Int, isDone: Boolean)
    }

    private var listener: OnTodoCellClickListener? = null
    fun setOnTodoCellClickListener(l: OnTodoCellClickListener) {
        listener = l
    }

    private var isDoneCheckListener: OnTodoCellCheckDoneListener? = null
    fun setOnTodoCellCheckDoneListener(l: OnTodoCellCheckDoneListener) {
        isDoneCheckListener = l
    }

    class VH(val binding: CellTodoListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding =
            CellTodoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val todo = getItem(position)

        holder.binding.apply {
            todoTitle.text = todo.title
            datetime.text = todo.datetime

            val colorRes = if (position % 2 == 0) R.color.row_even else R.color.row_odd
            val colorInt = ContextCompat.getColor(root.context, colorRes)
            root.setBackgroundColor(colorInt)

            root.setOnClickListener {
                if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(todo)
                }
            }
        }

        holder.binding.isDone.apply {
            setOnCheckedChangeListener(null)
            isChecked = todo.done
            setOnCheckedChangeListener { _, isChecked ->
                if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                    isDoneCheckListener?.onItemCheck(todo.id, isChecked)
                }
            }
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<TodoUiModel>() {
            override fun areItemsTheSame(oldItem: TodoUiModel, newItem: TodoUiModel) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: TodoUiModel, newItem: TodoUiModel) = oldItem == newItem
        }
    }
}