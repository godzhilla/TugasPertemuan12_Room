package com.example.tugaspertemuan12_room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tugaspertemuan12_room.databinding.ItemListBinding

typealias OnClickUpdate = (Note) -> Unit
typealias OnClickDelete = (Note) -> Unit

class NoteAdapter (
    private val listNote : List<Note>, private val onClickUpdate: OnClickUpdate,private val onClickDelete: OnClickDelete
) : RecyclerView.Adapter<NoteAdapter.ItemListViewHolder>() {
    inner class ItemListViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: Note){
            with(binding){
                txtTitle.text = data.title
                txtDesc.text = data.description
                txtDeadline.text = data.deadline
                editBtn.setOnClickListener{
                    onClickUpdate(data)
                }
                deleteBtn.setOnClickListener{
                    onClickDelete(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val binding = ItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ItemListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.bind(listNote[position])
    }
}
