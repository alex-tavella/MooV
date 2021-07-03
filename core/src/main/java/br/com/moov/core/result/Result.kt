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
}

inline infix fun <Data, Error, Data2> Result<Data, Error>.map(
    transform: (Data) -> Data2
): Result<Data2, Error> {
    return when (this) {
        is Result.Ok -> Result.Ok(transform(value))
        is Result.Err -> this
    }
}

inline infix fun <Data, Error, Error2> Result<Data, Error>.mapError(
    transform: (Error) -> Error2
): Result<Data, Error2> {
    return when (this) {
        is Result.Ok -> this
        is Result.Err -> Result.Err(transform(error))
    }
}

inline fun <Data, Error, Target> Result<Data, Error>.mapBoth(
    success: (Data) -> Target,
    failure: (Error) -> Target
): Target {
    return when (this) {
        is Result.Ok -> success(value)
        is Result.Err -> failure(error)
    }
}
