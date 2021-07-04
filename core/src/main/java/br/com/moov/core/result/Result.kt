/*
 * Copyright 2021 Alex Almeida Tavella
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.moov.core.result

sealed class Result<out Data, out Error> {
    data class Ok<out Data>(val value: Data) : Result<Data, Nothing>()
    data class Err<out Error>(val error: Error) : Result<Nothing, Error>()

    fun isSuccess(): Boolean {
        return this is Ok
    }

    fun isError(): Boolean {
        return !isSuccess()
    }

    fun getOrNull(): Data? {
        return when (this) {
            is Err -> null
            is Ok -> value
        }
    }

    fun errorOrNull(): Error? {
        return when (this) {
            is Err -> error
            is Ok -> null
        }
    }

    fun <Data2> map(transform: (Data) -> Data2): Result<Data2, Error> {
        return when (this) {
            is Ok -> Ok(transform(value))
            is Err -> this
        }
    }

    fun <Error2> mapError(transform: (Error) -> Error2): Result<Data, Error2> {
        return when (this) {
            is Ok -> this
            is Err -> Err(transform(error))
        }
    }

    fun onSuccess(action: (Data) -> Unit): Result<Data, Error> {
        if (this is Ok) action(value)

        return this
    }

    fun onError(action: (Error) -> Unit): Result<Data, Error> {
        if (this is Err) action(error)

        return this
    }
}
