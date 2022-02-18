package com.appcrafters.library.base.data

import com.appcrafters.library.base.functional.Either
import com.appcrafters.library.base.model.Book
import com.appcrafters.library.base.model.BookDetails
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.openMocks
import retrofit2.Call
import retrofit2.Response

class BookDataSourceTests {

    @Mock
    lateinit var apiService: BookApiService

    @Mock
    lateinit var getBooksCall: Call<List<Book>>

    @Mock
    lateinit var getBookDetailsCall: Call<BookDetails>

    lateinit var dataSource: BookDataSource

    @Before
    fun setUp() {
        openMocks(this)
        dataSource = BookDataSource(apiService)
    }

    @Test
    fun `testGetBooks, has response, Success returned`() = runBlocking {
        val expectedBooks: List<Book> = listOf()
        val expectedResult = Either.Success(expectedBooks)

        `when`(apiService.getAllBooks(anyString(), anyString())).thenReturn(getBooksCall)
        `when`(getBooksCall.execute()).thenReturn(Response.success(expectedBooks))

        val result = dataSource.getAllBooks()

        assertEquals(expectedResult, result)
    }

    @Test
    fun `testGetBooks, has error, Error returned`() = runBlocking {
        val expectedResponseBody = ResponseBody.create(
            MediaType.parse("application/json"), ""
        )

        `when`(apiService.getAllBooks(anyString(), anyString())).thenReturn(getBooksCall)
        `when`(getBooksCall.execute()).thenReturn(Response.error(400, expectedResponseBody))


        val result = dataSource.getAllBooks()

        assertTrue(result is Either.Error)
    }

    @Test
    fun `testGetBookDetails, has response, Success returned`() = runBlocking {
        val expectedBookDetails: BookDetails =
            BookDetails(4, "test", "test", "test", arrayOf(), 3, 10, "test", "test")
        val expectedResult = Either.Success(expectedBookDetails)

        `when`(apiService.getBookById(anyString(), anyString(), anyString())).thenReturn(
            getBookDetailsCall
        )
        `when`(getBookDetailsCall.execute()).thenReturn(Response.success(expectedBookDetails))

        val result = dataSource.getBookById("56597885")

        assertEquals(expectedResult, result)
    }

    @Test
    fun `testGetBookDetails, has error, Error returned`() = runBlocking {
        val expectedResponseBody = ResponseBody.create(
            MediaType.parse("application/json"), ""
        )

        `when`(apiService.getBookById(anyString(), anyString(), anyString())).thenReturn(
            getBookDetailsCall
        )
        `when`(getBookDetailsCall.execute()).thenReturn(
            Response.error(
                400,
                expectedResponseBody
            )
        )


        val result = dataSource.getBookById("56597885")

        assertTrue(result is Either.Error)
    }
}