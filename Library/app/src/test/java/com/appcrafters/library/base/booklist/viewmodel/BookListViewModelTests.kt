package com.appcrafters.library.base.booklist.viewmodel

import androidx.lifecycle.Observer
import com.appcrafters.library.base.InstantExecutorTest
import com.appcrafters.library.base.data.IBookDataSource
import com.appcrafters.library.base.functional.Either
import com.appcrafters.library.base.model.Book
import com.appcrafters.library.booklist.view.BookListViewState
import com.appcrafters.library.booklist.view.BookListViewState.*
import com.appcrafters.library.booklist.viewmodel.BookListViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.openMocks

class BookListViewModelTests : InstantExecutorTest() {
    @Mock
    lateinit var dataSource: IBookDataSource

    @Mock
    lateinit var stateObserver: Observer<BookListViewState>

    lateinit var viewModel: BookListViewModel

    @Before
    fun setUp() {
        openMocks(this)
        viewModel = BookListViewModel(dataSource)
        viewModel.state.observeForever(stateObserver)
    }

    @Test
    fun `testGetBooks, has result, state changed to Proccessing - DataReceived`() = runBlocking {
        val expectedResult: List<Book> = listOf()

        `when`(dataSource.getAllBooks()).thenReturn(Either.Success(expectedResult))

        viewModel.getBooks()

        verify(stateObserver).onChanged(Proccessing)
        verify(stateObserver).onChanged(DataReceived(expectedResult))
    }

    @Test
    fun `test GetBooks, has error, state changed to Proccessing - ErrorReceived`() = runBlocking {
        val expectedError = Exception("test")

        `when`(dataSource.getAllBooks()).thenReturn(Either.Error(expectedError))

        viewModel.getBooks()

        verify(stateObserver).onChanged(ErrorReceived(expectedError.toString()))
    }
}