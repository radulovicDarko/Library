package com.appcrafters.library.bookdetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appcrafters.library.base.data.BookDataSource
import com.appcrafters.library.base.functional.Either
import com.appcrafters.library.bookdetails.view.BookDetailsViewState
import kotlinx.coroutines.launch

class BookDetailsViewModel(private val dataSource: BookDataSource) : ViewModel() {
    private val _state = MutableLiveData<BookDetailsViewState>()
    val state: LiveData<BookDetailsViewState>
        get() = _state

    fun getGameById(id: Long) {
        viewModelScope.launch {
            _state.postValue(BookDetailsViewState.Proccessing)

            _state.postValue(
                when (val result = dataSource.getBookById(id.toString())) {
                    is Either.Success -> BookDetailsViewState.DataReceived(result.data)
                    is Either.Error -> BookDetailsViewState.ErrorReceived(result.exception.toString())
                }
            )
        }
    }
}