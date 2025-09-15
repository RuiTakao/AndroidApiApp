package com.takaobrog.androidapiapp.presentation.todo.todo_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.takaobrog.androidapiapp.R
import com.takaobrog.androidapiapp.domain.model.Todo
import com.takaobrog.androidapiapp.databinding.FragmentTodoListBinding
import com.takaobrog.androidapiapp.presentation.todo.component.dialog.TodoAlertDialog
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
        val swipe = binding.swipe
        val nav = findNavController()

        nav.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("reload")
            ?.observe(viewLifecycleOwner) {
                if (it) {
                    viewModel.load()
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

        adapter.setOnTodoCellCheckDoneListener(
            object : TodoListAdapter.OnTodoCellCheckDoneListener {
                override fun onItemCheck(todo: Todo, isDone: Boolean) {
                    println(todo)
                    println(isDone)
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
            swipe.isRefreshing = false
        }

        viewModel.dialogEvent.observe(viewLifecycleOwner) {
            val dialog = it.getContentIfNotHandled() ?: return@observe
            showDialog(dialog)
        }

        viewModel.reloading.observe(viewLifecycleOwner) {
            swipe.isRefreshing = it
        }

        swipe.setOnRefreshListener {
            viewModel.reloading()
        }

        return binding.root
    }

    private fun showDialog(dialog: TodoAlertDialog) =
        MaterialAlertDialogBuilder(requireContext()).setTitle(dialog.title)
            .setMessage(dialog.message)
            .setPositiveButton(dialog.positiveText) { _, _ ->
                viewModel.reloading()
            }
            .show()

    override fun onDestroy() {
        super.onDestroy()
        binding.recyclerView.adapter = null
        _binding = null
    }
}