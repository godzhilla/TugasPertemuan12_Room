package com.example.tugaspertemuan12_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tugaspertemuan12_room.databinding.ActivityEditDataBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private lateinit var mNoteDao: NoteDao
private lateinit var executorService: ExecutorService


class EditData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityEditDataBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        executorService = Executors.newSingleThreadExecutor()
        val db = NoteRoomDatabase.getDatabase(this)
        mNoteDao = db!!.noteDao()!!

        with(binding){
            edtTitle.setText(intent.getStringExtra("TITLE"))
            edtDesc.setText(intent.getStringExtra("DESC"))
            edtDeadline.setText(intent.getStringExtra("DEADLINE"))
            btnSaveEdt.setOnClickListener{
                update(
                    Note(
                        id = intent.getIntExtra("ID", 0),
                        title = edtTitle.text.toString(),
                        description = edtDesc.text.toString(),
                        deadline = edtDeadline.text.toString()
                    )
                )
                finish()
            }
        }
    }

    private fun update(note: Note){
        executorService.execute{
            mNoteDao.update(note)
        }
    }
}