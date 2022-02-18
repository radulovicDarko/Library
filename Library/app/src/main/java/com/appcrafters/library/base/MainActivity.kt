package com.appcrafters.library.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.appcrafters.library.R
import com.appcrafters.library.booklist.view.BookListFragment
import com.appcrafters.library.bookdetails.view.BookDetailsFragment
import kotlinx.android.synthetic.main.fragment_book_list.*

class MainActivity : AppCompatActivity(), ICoordinator {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        showFragment(BookListFragment(), true)
    }

    private fun showFragment(fragment: Fragment, addAsRoot: Boolean = false) {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        if (!addAsRoot) transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun showDetailsFragment(bookId: Long) {
        showFragment(BookDetailsFragment.newInstance(bookId))
    }

}