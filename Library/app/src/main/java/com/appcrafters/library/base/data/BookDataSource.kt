package com.appcrafters.library.base.data

import com.appcrafters.library.base.functional.Either
import com.appcrafters.library.base.model.Book
import com.appcrafters.library.base.model.BookDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

interface IBookDataSource {
    suspend fun getAllBooks(): Either<List<Book>>
    suspend fun getBookById(id: String): Either<BookDetails>
}

class BookDataSource(private val apiService: BookApiService) : IBookDataSource {
    companion object {
        private const val host = "hapi-books.p.rapidapi.com"
        private const val key = "ea9d745207msh8cd03420e084d49p1ac50ajsn5f5967a56706"
    }

    override suspend fun getAllBooks(): Either<List<Book>> = handleCall(apiService.getAllBooks(host,key))

    override suspend fun getBookById(id: String): Either<BookDetails> = handleCall(apiService.getBookById(host,key,id))

    private suspend fun <T> handleCall(call: Call<T>): Either<T> {
        return withContext(Dispatchers.IO) {
            val response = call.execute()

            if (response.isSuccessful) {
                Either.Success(response.body()!!)
            } else {
                Either.Error(Exception(response.message()))
            }
        }
    }
}