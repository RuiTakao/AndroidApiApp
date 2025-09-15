package com.takaobrog.androidapiapp.presentation.todo_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.takaobrog.androidapiapp.databinding.FragmentTodoDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoDetailFragment : Fragment() {
    private var _binding: FragmentTodoDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodoDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoDetailBinding.inflate(inflater, container, false)
        val done = binding.isDone
        val title = binding.todoTitle
        val content = binding.todoContent
        val editBtn = binding.editBtn
        val deleteBtn = binding.deleteBtn

        viewModel.todo.observe(viewLifecycleOwner) {
            title.text = it?.title
            content.text = it?.content
        }

        return binding.root
    }
}