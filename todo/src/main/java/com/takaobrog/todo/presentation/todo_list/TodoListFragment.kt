package com.takaobrog.todo.presentation.todo_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.takaobrog.todo.R
import com.takaobrog.todo.databinding.FragmentTodoListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoListFragment : Fragment() {
    private val viewModel: TodoListViewModel by viewModels()

    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        val todoListRecyclerView = binding.recyclerView
        val linearLayout = LinearLayoutManager(binding.root.context)
        val adapter = TodoListAdapter()
        val fab = binding.floatingActionButton
        val nav = findNavController()

        todoListRecyclerView.layoutManager = linearLayout
        todoListRecyclerView.adapter = adapter

        fab.setOnClickListener {
            nav.navigate(R.id.action_todoListFragment_to_todoAddFragment)
        }

        viewModel.todoList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}