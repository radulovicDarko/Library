package com.appcrafters.library.booklist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appcrafters.library.base.data.IBookDataSource
import com.appcrafters.library.base.functional.Either
import com.appcrafters.library.booklist.view.BookListViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookListViewModel(private val dataSource: IBookDataSource) : ViewModel() {

    private val _state = MutableLiveData<BookListViewState>()
    val state: LiveData<BookListViewState>
        get() = _state

    fun getBooks() {
        viewModelScope.launch(Dispatchers.IO) {

            _state.postValue(BookListViewState.Proccessing)

            _state.postValue(
                when (val result = dataSource.getAllBooks()) {
                    is Either.Success -> BookListViewState.DataReceived(result.data)
                    is Either.Error -> BookListViewState.ErrorReceived(result.exception.toString())
                }
            )
        }
    }
}