package com.example.data.body

class Result<Data>(val data: Data?, error: String? = null) {
    val error: DataError? = if (error == null) null else DataError(error)
}

class DataError(val cause: String)