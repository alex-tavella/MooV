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
