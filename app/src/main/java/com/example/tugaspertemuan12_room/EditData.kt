package com.example.tugaspertemuan12_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tugaspertemuan12_room.databinding.ActivityEditDataBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class EditData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityEditDataBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
            edtTitle.setText(intent.getStringExtra("TITLE"))
            edtDesc.setText(intent.getStringExtra("DESC"))
            edtDeadline.setText(intent.getStringExtra("DEADLINE"))
            btnSaveEdt.setOnClickListener{
                MainActivity.updateNote(
                    Note(
                        id = intent.getStringExtra("ID").toString(),
                        title = edtTitle.text.toString(),
                        description = edtDesc.text.toString(),
                        deadline = edtDeadline.text.toString()
                    )
                )
                finish()
            }
        }
    }
}