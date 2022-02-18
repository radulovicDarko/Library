package com.appcrafters.library.booklist.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.appcrafters.library.R
import com.appcrafters.library.base.model.Book

class BookRVAdapter(private val books: List<Book>, private val onItemClicked: (Long) -> Unit) :
    Adapter<BookRVViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BookRVViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
    )

    override fun onBindViewHolder(holder: BookRVViewHolder, position: Int) {
        holder.bind(books[position], onItemClicked)
    }

    override fun getItemCount() = books.size
}