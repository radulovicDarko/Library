package com.appcrafters.library.booklist.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.appcrafters.library.base.model.Book
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_book.view.*;

class BookRVViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(book: Book, onItemClicked: (Long) -> Unit) {

        Glide.with(itemView).load(book.cover).into(itemView.bookCoverImg)

        itemView.bookNameTxt.text = book.name
        itemView.categoryTxt.text = book.winning_category

        itemView.setOnClickListener {
            onItemClicked.invoke(book.book_id)
        }
    }
}