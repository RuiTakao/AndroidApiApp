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
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        val editTextTitle = binding.todoInputTitle
        val editTextContent = binding.todoInputContent
        val createBtn = binding.todoCreateBtn
        val validMessage = binding.validMessage

        createBtn.setOnClickListener {
            val title = editTextTitle.text.toString()
            val content = editTextContent.text.toString()
            if (!viewModel.validMessage(title = title, content = content).isEmpty()) {
                validMessage.text = viewModel.validMessage(title = title, content = content)
                validMessage.visibility = View.VISIBLE
                return@setOnClickListener
            }

            viewLifecycleOwner.lifecycleScope.launch {
                val res =
                    viewModel.createTodo(
                        title = title,
                        content = content,
                        deviceId = deviceId,
                    )
                res.onSuccess {
                    Log.d("TodoAddFragment", "todo insert success")
                    findNavController().popBackStack()
                    dialog = MaterialAlertDialogBuilder(requireContext())
                        .setTitle("登録に成功しました")
                        .setPositiveButton("OK", null)
                        .show()
                }.onFailure { e ->
                    Log.e("TodoAddFragment", "todo insert failure : $e")
                    dialog = MaterialAlertDialogBuilder(requireContext())
                        .setTitle("登録に失敗しました")
                        .setMessage("エラー：${e.message}")
                        .setPositiveButton("OK", null)
                        .show()
                }
            }
        }

        return binding.root
    }
}