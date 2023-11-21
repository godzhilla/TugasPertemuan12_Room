package com.example.tugaspertemuan12_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tugaspertemuan12_room.databinding.ActivityAddDataBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private lateinit var mNoteDao: NoteDao
private lateinit var executorService: ExecutorService


class AddData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityAddDataBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        executorService = Executors.newSingleThreadExecutor() //buat memanggel method get
        val db = NoteRoomDatabase.getDatabase(this)
        mNoteDao = db!!.noteDao()!!

        with(binding){
            btnSaveAdd.setOnClickListener{
                insert(
                    Note(
                        title = addTitle.text.toString(),
                        description = addDesc.text.toString(),
                        deadline = addDeadline.text.toString()
                    )
                )
                finish()
            }
        }

    }

    private fun insert(note: Note){
        executorService.execute{
            mNoteDao.insert(note)
        }
    }
}