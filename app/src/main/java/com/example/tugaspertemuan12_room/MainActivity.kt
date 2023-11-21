package com.example.tugaspertemuan12_room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugaspertemuan12_room.databinding.ActivityMainBinding
import java.util.concurrent.AbstractExecutorService
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mNoteDao: NoteDao
    private lateinit var executorService: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = NoteRoomDatabase.getDatabase(this)
        mNoteDao = db!!.noteDao()!!

        mNoteDao.allNotes.observe(this){
            binding.mainRv.apply {
                adapter = NoteAdapter(it, {note ->
                    val intent = Intent(this@MainActivity, EditData::class.java)
                    intent.putExtra("ID", note.id)
                    intent.putExtra("TITLE", note.title)
                    intent.putExtra("DESC", note.description)
                    intent.putExtra("DEADLINE", note.deadline)
                    startActivity(intent)
                }, {
                    note -> delete(note)
                })
            }
        }

        with(binding){
            addBtn.setOnClickListener{
                val intent = Intent(this@MainActivity, AddData::class.java)
                startActivity(intent)
            }

            mainRv.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    private fun delete(note: Note){
        executorService.execute{
            mNoteDao.delete(note)
        }
    }

}