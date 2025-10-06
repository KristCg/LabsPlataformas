package com.kriscg.laboratorio8.ViewModel

data class Estados<T>(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val data: T? = null
)
