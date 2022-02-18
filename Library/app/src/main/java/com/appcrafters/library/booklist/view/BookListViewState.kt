package com.appcrafters.library.booklist.view

import com.appcrafters.library.base.model.Book

sealed class BookListViewState {

    object Proccessing : BookListViewState()
    data class DataReceived(val books: List<Book>) : BookListViewState()
    data class ErrorReceived(val message: String) : BookListViewState()
}