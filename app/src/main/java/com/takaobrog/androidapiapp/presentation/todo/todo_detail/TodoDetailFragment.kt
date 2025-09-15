package com.takaobrog.androidapiapp.presentation.todo.todo_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.takaobrog.androidapiapp.databinding.DialogTodoEditBinding
import com.takaobrog.androidapiapp.databinding.FragmentTodoDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoDetailFragment : Fragment() {
    private var _binding: FragmentTodoDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodoDetailViewModel by viewModels()

    private var dialog: AlertDialog? = null

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
            val dialogTitle = it?.title
            title.text = it?.title
            content.text = it?.content
            deleteBtn.setOnClickListener {
                showDeleteDialog(dialogTitle)
            }
        }

        editBtn.setOnClickListener {
            showEditDialog()
        }

        return binding.root
    }

    private fun showEditDialog() {
        val dialogBinding = DialogTodoEditBinding.inflate(layoutInflater, null, false)
        val title = dialogBinding.editTitle
        val content = dialogBinding.editContent
        viewModel.todo.observe(viewLifecycleOwner) {
            title.setText(it?.title)
            content.setText(it?.content)
        }
        dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("編集")
            .setView(dialogBinding.root)
            .setNegativeButton("キャンセル", null)
            .setPositiveButton("OK", null)
            .create()
            .apply {
                setOnShowListener {
                    val positive = getButton(AlertDialog.BUTTON_POSITIVE)
                    positive.setOnClickListener {
                        val title = title.text?.toString()?.trim().orEmpty()
                        val content = content.text?.toString()?.trim().orEmpty()
                        viewModel.update(title, content)
                        dismiss()
                        Toast.makeText(requireContext(), "送信しました", Toast.LENGTH_SHORT).show()
                    }
                }
                show()
            }
    }

    private fun showDeleteDialog(dialogTitle: String?) {
        dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("${dialogTitle}を削除しますか？")
            .setNegativeButton("キャンセル", null)
            .setPositiveButton("削除", null)
            .create()
            .apply {
                setOnShowListener {
                    val positive = getButton(AlertDialog.BUTTON_POSITIVE)
                    positive.setOnClickListener {
                        findNavController().previousBackStackEntry?.savedStateHandle?.set("refresh", true)
                        findNavController().popBackStack()
                        dismiss()
                        Toast.makeText(requireContext(), "削除しました", Toast.LENGTH_SHORT).show()
                        viewModel.delete()
                    }
                }
                show()
            }
    }

    override fun onDestroyView() {
        dialog = null
        super.onDestroyView()
    }
}