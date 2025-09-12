package com.takaobrog.todo.presentation.todo_add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.takaobrog.todo.databinding.FragmentTodoAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoAddFragment : Fragment() {

    private var _binding: FragmentTodoAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoAddBinding.inflate(inflater, container, false)

        return binding.root
    }
}