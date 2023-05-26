package com.weesnerDevelopment.lavalamp.sdk

sealed class Either<out S, out F> {
    class Success<S>(val value: S) : Either<S, Nothing>()
    class Failure<F>(val value: F) : Either<Nothing, F>()

    companion object {
        fun <S> success(value: S): Success<S> = Success(value = value)
        fun <F> failure(value: F): Failure<F> = Failure(value = value)
    }

    val isSuccessful: Boolean
        get() = this is Success

    val isFailure: Boolean
        get() = this is Failure

    fun onSuccess(success: (S) -> Unit): Either<S, F> {
        when (this) {
            is Success -> {
                success(value)
            }

            else -> {
                // fall though
            }
        }

        return this
    }

    fun onFailure(failure: (F) -> Unit): Either<S, F> {
        when (this) {
            is Failure -> {
                failure(value)
            }

            else -> {
                // fall though
            }
        }

        return this
    }
}
