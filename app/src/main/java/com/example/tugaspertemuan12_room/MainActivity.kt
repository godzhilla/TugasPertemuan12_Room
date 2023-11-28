package com.example.tugaspertemuan12_room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugaspertemuan12_room.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.AbstractExecutorService
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        with(binding){
            addBtn.setOnClickListener{
                val intent = Intent(this@MainActivity, AddData::class.java)
                startActivity(intent)
            }

            mainRv.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
        observeNotes()
        getAllNotes()
    }

    companion object {
        val firestore = FirebaseFirestore.getInstance()
        val  noteCollectionRef = firestore.collection("note")
        val noteListLiveData : MutableLiveData<List<Note>> by lazy {
            MutableLiveData<List<Note>>()
        }

        fun insertNote(note: Note){
            noteCollectionRef.add(note)
        }

        fun updateNote(note: Note){
            noteCollectionRef.document(note.id).set(note)
        }

        fun deleteNote(note: Note){
            noteCollectionRef.document(note.id).delete()
        }
    }

    fun getAllNotes() {
        noteCollectionRef.addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(this, "Error listening to characters data", Toast.LENGTH_SHORT).show()
            }
            val notes = arrayListOf<Note>()
            value?.forEach { documentReference ->
                notes.add(
                    Note(
                        documentReference.id,
                        documentReference.get("title").toString(),
                        documentReference.get("deadline").toString(),
                        documentReference.get("description").toString()
                    )
                )
                if (notes.isNotEmpty()) {
                    noteListLiveData.postValue(notes)
                } else {
                    Toast.makeText(this, "Gagal menambahkan to do", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun observeNotes() {
        noteListLiveData.observe(this) {
            note ->
            var listNote = note.toMutableList()
            binding.mainRv.adapter = NoteAdapter(listNote, {note ->
                val intent = Intent(this, AddData::class.java)
                intent.putExtra("ID", note.id )
                startActivity(intent)
            }, {note ->
                deleteNote(note)
            })
        }
    }

}