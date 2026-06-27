package com.takaobrog.androidapiapp.presentation.todo.todo_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.takaobrog.androidapiapp.databinding.DialogTodoEditBinding
import com.takaobrog.androidapiapp.databinding.FragmentTodoDetailBinding
import com.takaobrog.androidapiapp.presentation.todo.component.dialog.TodoAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        val datetime = binding.datetime
        val done = binding.isDone
        val title = binding.todoTitle
        val memo = binding.todoMemo
        val editBtn = binding.editBtn
        val deleteBtn = binding.deleteBtn
        val swipe = binding.swipe
        title.text = ""
        memo.text = ""

        viewModel.todo.observe(viewLifecycleOwner) {
            done.isChecked = it.done
            datetime.text = it.datetime
            title.text = it.title
            memo.text = it.memo
            val dialogTitle = it.title
            deleteBtn.setOnClickListener {
                showDeleteDialog(dialogTitle)
            }
            swipe.isRefreshing = false
        }

        editBtn.setOnClickListener {
            showEditDialog()
        }

        done.setOnCheckedChangeListener(null)
        done.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateDone(isDone = isChecked)
        }

        viewModel.reloading.observe(viewLifecycleOwner) {
            swipe.isRefreshing = it
        }

        viewModel.dialogEvent.observe(viewLifecycleOwner) {
            val dialog = it.getContentIfNotHandled() ?: return@observe
            showErrorDialog(dialog)
        }

        swipe.setOnRefreshListener {
            viewModel.reloading()
        }

        return binding.root
    }

    private fun showEditDialog() {
        val dialogBinding = DialogTodoEditBinding.inflate(layoutInflater, null, false)
        val title = dialogBinding.editTitle
        val memo = dialogBinding.editMemo
        viewModel.todo.observe(viewLifecycleOwner) {
            title.setText(it.title)
            memo.setText(it.memo)
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
                        val memo = memo.text?.toString()?.trim().orEmpty()
                        viewModel.update(title = title,memo = memo)
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
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewModel.delete()
                            findNavController().previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("reload", true)
                            findNavController().popBackStack()
                            dismiss()
                            Toast.makeText(requireContext(), "削除しました", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                show()
            }
    }

    private fun showErrorDialog(dialog: TodoAlertDialog) =
        MaterialAlertDialogBuilder(requireContext()).setTitle(dialog.title)
            .setMessage(dialog.message)
            .setPositiveButton(dialog.positiveText) { _, _ ->
                findNavController().previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("reload", true)
                findNavController().popBackStack()
            }
            .show()

    override fun onDestroyView() {
        dialog = null
        super.onDestroyView()
    }
}