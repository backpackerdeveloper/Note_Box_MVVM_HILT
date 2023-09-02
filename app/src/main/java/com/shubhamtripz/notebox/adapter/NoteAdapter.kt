package com.shubhamtripz.notebox.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shubhamtripz.notebox.data.entity.Note
import com.shubhamtripz.notebox.databinding.ItemNotesBinding
import java.text.SimpleDateFormat

class NoteAdapter(private val mNotes: List<Note>, private val listener: OnNoteClickListener): RecyclerView.Adapter<NoteAdapter.viewHolder>() {

    interface OnNoteClickListener {
        fun onNoteclick(note: Note)
        fun onNoteLongclick(note: Note)
    }

    inner class viewHolder(private val binding: ItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION){
                        val note = mNotes[position]
                        listener.onNoteclick(note)
                    }
                }

                root.setOnLongClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION){
                        val note = mNotes[position]
                        listener.onNoteLongclick(note)
                    }
                    true
                }
            }

        }

        fun bind(note: Note) {
            binding.apply {
                titleNote.text = note.title
                contentNote.text = note.content
                val formatter = SimpleDateFormat("dd/MM/yyyy")
                dateNote.text = formatter.format(note.date)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mNotes.size
    }


    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        with(mNotes[position]) {
            holder.bind(this)
        }

    }
}