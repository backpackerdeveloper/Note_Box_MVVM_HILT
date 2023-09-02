package com.shubhamtripz.notebox.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shubhamtripz.notebox.data.dao.NoteDao
import com.shubhamtripz.notebox.data.entity.Note


@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao
}