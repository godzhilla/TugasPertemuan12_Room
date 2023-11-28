package com.example.tugaspertemuan12_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tugaspertemuan12_room.databinding.ActivityAddDataBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AddData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityAddDataBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
            btnSaveAdd.setOnClickListener{
                MainActivity.insertNote(
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
}