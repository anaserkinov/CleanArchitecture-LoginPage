package com.undefined.domain

// Created by Anaskhan on 6/21/2021.

sealed class UIState<out T> {

    class Success<out T>(val data: T? = null) : UIState<T>()

    class GenericError(
        val fieldName: String? = null,
        val message: String? = null
    ) : UIState<Nothing>()

    class ValidationError(
        val fieldName: Int,
        val message: Int,
        val value: Any? = null
    ) : UIState<Nothing>()

}