package com.appcrafters.library.base.data

import com.appcrafters.library.base.model.Book
import com.appcrafters.library.base.model.BookDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface BookApiService {

    @GET("top/2020")
    fun getAllBooks(@Header("x-rapidapi-host") host: String, @Header("x-rapidapi-key") key: String): Call<List<Book>>

    @GET("book/{id}")
    fun getBookById(@Header("x-rapidapi-host") host: String, @Header("x-rapidapi-key") key: String, @Path("id") id: String): Call<BookDetails>
}