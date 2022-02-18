package com.appcrafters.library.base.model

data class Book(
    val book_id: Long,
    val name: String,
    val winning_category: String,
    val cover: String,
    val url: String,
)

data class BookDetails(
    val book_id: Long,
    val name: String,
    val cover: String,
    val url: String,
    val authors: Array<String>,
    val rating: Int,
    val pages: Int,
    val published_date: String,
    val synopsis: String,
)