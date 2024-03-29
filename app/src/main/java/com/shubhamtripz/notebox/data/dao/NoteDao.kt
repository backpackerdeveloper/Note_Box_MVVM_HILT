package com.shubhamtripz.notebox.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shubhamtripz.notebox.data.entity.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Query("select * from notes ORDER BY date desc")
    fun getAllNotes(): Flow<List<Note>>

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}