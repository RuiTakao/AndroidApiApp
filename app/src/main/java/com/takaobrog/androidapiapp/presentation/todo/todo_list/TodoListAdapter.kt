package com.takaobrog.androidapiapp.presentation.todo.todo_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.takaobrog.androidapiapp.R
import com.takaobrog.androidapiapp.domain.model.todo.Todo
import com.takaobrog.androidapiapp.databinding.CellTodoListBinding

class TodoListAdapter : ListAdapter<Todo, TodoListAdapter.VH>(DIFF) {

    interface OnTodoCellClickListener {
        fun onItemClick(todo: Todo)
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

        with(holder.binding) {
            todoTitle.text = todo.title
            isDone.isChecked = todo.done
            datetime.text = todo.createdAt

            val colorRes = if (position % 2 == 0) R.color.row_even else R.color.row_odd
            val colorInt = ContextCompat.getColor(root.context, colorRes)
            root.setBackgroundColor(colorInt)

            isDone.setOnCheckedChangeListener { _, isChecked ->
                if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                    isDoneCheckListener?.onItemCheck(todo.id ?: 0, isChecked)
                }
            }

            root.setOnClickListener {
                if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(todo)
                }
            }
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Todo, newItem: Todo) = oldItem == newItem
        }
    }
}