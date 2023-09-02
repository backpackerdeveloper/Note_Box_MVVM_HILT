package com.shubhamtripz.notebox.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shubhamtripz.notebox.R
import com.shubhamtripz.notebox.data.entity.Note
import com.shubhamtripz.notebox.databinding.FragmentAddeditnotesBinding
import com.shubhamtripz.notebox.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditNoteFragment: Fragment(R.layout.fragment_addeditnotes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel by viewModels<NoteViewModel> ()
        val binding = FragmentAddeditnotesBinding.bind(requireView())
        val args: AddEditNoteFragmentArgs by navArgs()
        val note = args.note

        if (note != null){
            binding.apply {
                titleEdit.setText(note.title)
                contentEdit.setText(note.content)
                saveBtn.setOnClickListener{
                    val title = titleEdit.text.toString()
                    val content = contentEdit.text.toString()
                    val updatedNote = note.copy(title = title, content = content, date = System.currentTimeMillis())
                    viewModel.updateNote(updatedNote)
                }
            }
        } else{
            binding.apply {
                saveBtn.setOnClickListener {
                    val title = titleEdit.text.toString()
                    val content = contentEdit.text.toString()
                    val note = Note(title = title, content = content, date = System.currentTimeMillis())
                    viewModel.insertNote(note)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.notesEvent.collect{event ->
                if (event is NoteViewModel.NotesEvent.NavigateToNotesFragment){
                    val action = AddEditNoteFragmentDirections.actionAddEditNoteFragmentToNoteFragment()
                    findNavController().navigate(action)
                }

            }
        }
    }


}