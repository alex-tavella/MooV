/*
 * Copyright 2020 Alex Almeida Tavella
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
package br.com.moov.test

import java.util.Date
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

object DataFactory {
    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomStringList(count: Int): List<String> {
        return mutableListOf<String>().apply { repeat(count) { add(randomString()) } }
    }

    fun randomPathString(): String {
        return "/${UUID.randomUUID()}"
    }

    fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }

    fun randomInt(range: Int = 1000): Int {
        return ThreadLocalRandom.current().nextInt(0, range + 1)
    }

    fun randomLong(range: Int = 1000): Long {
        return randomInt(range).toLong()
    }

    fun randomScore(): Float {
        return ThreadLocalRandom.current().nextFloat() * 10
    }

    fun randomDateAsString(): String {
        return Date(randomLong()).toString()
    }

    fun randomUrl(): String {
        return "http://${UUID.randomUUID()}.com/"
    }
}
