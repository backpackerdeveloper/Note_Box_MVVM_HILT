package com.shubhamtripz.notebox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubhamtripz.notebox.data.dao.NoteDao
import com.shubhamtripz.notebox.data.entity.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteDao: NoteDao): ViewModel() {

    val notes = noteDao.getAllNotes()
    val noteschannel = Channel<NotesEvent>()
    val notesEvent = noteschannel.receiveAsFlow()

    fun insertNote(note: Note) = viewModelScope.launch {
        noteDao.insertNote(note)
        noteschannel.send(NotesEvent.NavigateToNotesFragment)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteDao.updateNote(note)
        noteschannel.send(NotesEvent.NavigateToNotesFragment)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteDao.deleteNote(note)
        noteschannel.send(NotesEvent.showUndoSnackBar("Deleted Successfully", note))
    }

    sealed class NotesEvent{
        data class showUndoSnackBar(val msg: String, val note: Note): NotesEvent()
        object NavigateToNotesFragment: NotesEvent()

    }
}