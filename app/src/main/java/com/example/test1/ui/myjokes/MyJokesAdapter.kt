package com.example.test1.ui.myjokes

import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.R
import com.example.test1.extensions.inflate
import com.example.test1.data.model.Joke

class MyJokesAdapter(
    private val deleteClickListener: (Joke) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<Joke> = emptyList()
        set(value) {
            DiffUtil.calculateDiff(diffUtilCallback(field, value)).dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(parent, deleteClickListener)
    }

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }
}

class ViewHolder(
    parent: ViewGroup,
    private val deleteClickListener: (Joke) -> Unit
) : RecyclerView.ViewHolder(parent inflate R.layout.item_my_jokes) {

    private val tvMessage = itemView.findViewById<TextView>(R.id.tv_message)
    private val btnDelete = itemView.findViewById<Button>(R.id.btn_delete)

    fun bind(item: Joke) {
        tvMessage.text = item.joke
        btnDelete.setOnClickListener {
            deleteClickListener.invoke(item)
        }
    }
}

private fun diffUtilCallback(oldElements: List<Joke>, newElements: List<Joke>) =
    object : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldElements[oldItemPosition].id == newElements[newItemPosition].id
        }

        override fun getOldListSize(): Int = oldElements.size

        override fun getNewListSize(): Int = newElements.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldElements[oldItemPosition] == newElements[newItemPosition]
        }
    }