package com.appcrafters.library.base.data

object ApiServiceProvider {
    val bookApiService = RetrofitBuilder.retrofit.create(BookApiService::class.java)
}