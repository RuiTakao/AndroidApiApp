package com.takaobrog.androidapiapp.presentation.todo_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.takaobrog.androidapiapp.R
import com.takaobrog.androidapiapp.data.Todo
import com.takaobrog.androidapiapp.databinding.FragmentTodoListBinding
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

        nav.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("reload")
            ?.observe(viewLifecycleOwner) {
                if (it) {
                    viewModel.fetchTodoList()
                    nav.currentBackStackEntry?.savedStateHandle?.remove<Boolean>("reload")
                }
            }

        adapter.setOnTodoCellClickListener(
            object : TodoListAdapter.OnTodoCellClickListener {
                override fun onItemClick(todo: Todo) {
                    val id = todo.id ?: 0
                    val action =
                        TodoListFragmentDirections.actionTodoListFragmentToTodoDetailFragment(id)
                    findNavController().navigate(action)
                }
            }
        )

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