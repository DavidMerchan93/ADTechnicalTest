package com.davidmerchan.domain.entitie

sealed interface Resource<out R> {
    data class Success<out R>(val data: R) : Resource<R>
    data class Error(val exception: Throwable = GeneralError) : Resource<Nothing>
}
