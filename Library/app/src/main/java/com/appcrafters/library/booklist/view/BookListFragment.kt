package com.appcrafters.library.booklist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.appcrafters.library.R
import com.appcrafters.library.base.ICoordinator
import com.appcrafters.library.base.data.ApiServiceProvider
import com.appcrafters.library.base.data.BookDataSource
import com.appcrafters.library.base.functional.ViewModelFactoryUtil
import com.appcrafters.library.booklist.recycler.BookRVAdapter
import com.appcrafters.library.booklist.viewmodel.BookListViewModel
import com.appcrafters.library.base.model.Book
import kotlinx.android.synthetic.main.fragment_book_list.*

class BookListFragment : Fragment() {

    lateinit var viewModel: BookListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactoryUtil.viewModelFactory {
            BookListViewModel(BookDataSource(ApiServiceProvider.bookApiService))
        }).get(BookListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindFromViewModel()
        viewModel.getBooks()
    }

    private fun setUpRecyclerView(books: List<Book>) {
        bookListRV.adapter = BookRVAdapter(books) { id ->
            (activity as ICoordinator).showDetailsFragment(id)
        }
    }

    private fun bindFromViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->

            bookListProgressBar.isVisible = state is BookListViewState.Proccessing

            when (state) {
                is BookListViewState.DataReceived -> { setUpRecyclerView(state.books) }
                is BookListViewState.ErrorReceived -> showError(state.message)
            }

        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}