package com.takaobrog.todo.presentation.todo_add

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.takaobrog.todo.databinding.FragmentTodoAddBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodoAddFragment : Fragment() {

    private var _binding: FragmentTodoAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodoAddViewModel by viewModels()

    private var dialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val deviceId = requireActivity().intent.getStringExtra("device_id") ?: ""

        _binding = FragmentTodoAddBinding.inflate(inflater, container, false)
        val title = binding.todoInputTitle
        val content = binding.todoInputContent
        val createBtn = binding.todoCreateBtn

        createBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val res =
                    viewModel.createTodo(
                        title = title.text.toString(),
                        content = content.text.toString(),
                        deviceId = deviceId
                    )
                res.onSuccess {
                    Log.d("TodoAddFragment", "success")
                }.onFailure { e ->
                    Log.e("TodoAddFragment", "failure : $e")
                }
            }
        }

        return binding.root
    }
}