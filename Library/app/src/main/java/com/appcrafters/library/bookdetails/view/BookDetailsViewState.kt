package com.appcrafters.library.bookdetails.view

import com.appcrafters.library.base.model.BookDetails

sealed class BookDetailsViewState {
    object Proccessing: BookDetailsViewState()
    data class DataReceived(val book: BookDetails) : BookDetailsViewState()
    data class ErrorReceived(val message: String) : BookDetailsViewState()
}