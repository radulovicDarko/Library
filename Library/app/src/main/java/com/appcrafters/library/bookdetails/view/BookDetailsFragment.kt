package com.appcrafters.library.bookdetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.appcrafters.library.R
import com.appcrafters.library.base.data.ApiServiceProvider
import com.appcrafters.library.base.data.BookDataSource
import com.appcrafters.library.base.functional.ViewModelFactoryUtil
import com.appcrafters.library.base.model.BookDetails
import com.appcrafters.library.bookdetails.viewmodel.BookDetailsViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_book_details.*

class BookDetailsFragment : Fragment() {

    var bookId: Long = -1
    private lateinit var _viewModel: BookDetailsViewModel

    companion object {

        private const val BOOK_ID_KEY = "BOOK_ID"

        fun newInstance(bookId: Long): BookDetailsFragment {

            return BookDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(BOOK_ID_KEY, bookId)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bookId = arguments?.getLong(BOOK_ID_KEY) ?: -1
        _viewModel = ViewModelProvider(this, ViewModelFactoryUtil.viewModelFactory {
            BookDetailsViewModel(BookDataSource(ApiServiceProvider.bookApiService))
        }).get(BookDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindFromViewModel()
        _viewModel.getGameById(bookId)
    }

    private fun bindFromViewModel() {
        _viewModel.state.observe(viewLifecycleOwner) { state ->

            bookDetailsProgressBar.isVisible = state is BookDetailsViewState.Proccessing

            when (state) {
                is BookDetailsViewState.DataReceived -> {
                    setUpView(state.book)
                }
                is BookDetailsViewState.ErrorReceived -> {
                    showError(state.message)
                }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun loadStars(rating: Int) {
        val filledStar = R.drawable.filledstar
        val emptyStar = R.drawable.emptystar

        when (rating) {
            1 -> {
                Glide.with(this).load(filledStar).into(star1Img)
                Glide.with(this).load(emptyStar).into(star2Img)
                Glide.with(this).load(emptyStar).into(star3Img)
                Glide.with(this).load(emptyStar).into(star4Img)
                Glide.with(this).load(emptyStar).into(star5Img)
            }
            2 -> {
                Glide.with(this).load(filledStar).into(star1Img)
                Glide.with(this).load(filledStar).into(star2Img)
                Glide.with(this).load(emptyStar).into(star3Img)
                Glide.with(this).load(emptyStar).into(star4Img)
                Glide.with(this).load(emptyStar).into(star5Img)
            }
            3 -> {
                Glide.with(this).load(filledStar).into(star1Img)
                Glide.with(this).load(filledStar).into(star2Img)
                Glide.with(this).load(filledStar).into(star3Img)
                Glide.with(this).load(emptyStar).into(star4Img)
                Glide.with(this).load(emptyStar).into(star5Img)
            }
            4 -> {
                Glide.with(this).load(filledStar).into(star1Img)
                Glide.with(this).load(filledStar).into(star2Img)
                Glide.with(this).load(filledStar).into(star3Img)
                Glide.with(this).load(filledStar).into(star4Img)
                Glide.with(this).load(emptyStar).into(star5Img)
            }
            5 -> {
                Glide.with(this).load(filledStar).into(star1Img)
                Glide.with(this).load(filledStar).into(star2Img)
                Glide.with(this).load(filledStar).into(star3Img)
                Glide.with(this).load(filledStar).into(star4Img)
                Glide.with(this).load(filledStar).into(star5Img)
            }
        }
    }

    private fun setUpView(book: BookDetails) {
        Glide.with(this).load(book.cover).into(coverImg)

        nameTxt.text = book.name

        var authors: String = ""
        val iterator: Iterator<String> = book.authors.iterator()
        while (iterator.hasNext()) {
            authors += iterator.next()
            if (iterator.hasNext()) {
                authors += "\n"
            }
        }
        authorsTxt.text = authors

        loadStars(book.rating)

        authorsTxt.text = authors
        synopsisLabelTxt.text = "Synopsis"
        synopsisTxt.text = book.synopsis
        publishedOnTxt.text = "Published on " + book.published_date + " - " + book.pages + " pages"
        urlTxt.text = book.url
    }
}