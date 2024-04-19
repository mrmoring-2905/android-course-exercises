package com.rxmobileteam.lecture6

import java.util.concurrent.TimeoutException
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTimedValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

suspend fun longComputation(delay: Duration): Int {
  delay(delay)
  return Random.nextInt()
}

suspend fun timeoutComputationThrows(delay: Duration): Int {
  // Calls longComputation() and returns its result
  // If longComputation() takes more than 500 ms, throws TimeoutException
  return try {
    val result = withTimeout(500) {
      longComputation(delay)
    }
    result
  } catch (e: TimeoutException) {
    println("TimeoutException: $e")
    -1
  }

}

suspend fun timeoutComputationReturnsNull(delay: Duration): Int? {
  // Calls longComputation() and returns its result
  // If longComputation() takes more than 500 ms, returns null
  return withTimeoutOrNull(500) {
    longComputation(delay)
  }

}

fun main() = runBlocking {
  println(runCatching { measureTimedValue { longComputation(1.seconds) } })
  println(runCatching { measureTimedValue { longComputation(2.seconds) } })

  println(runCatching { measureTimedValue { timeoutComputationThrows(500.milliseconds) } })
  println(runCatching { measureTimedValue { timeoutComputationThrows(1.seconds) } })

  println(runCatching { measureTimedValue { timeoutComputationReturnsNull(500.milliseconds) } })
  println(runCatching { measureTimedValue { timeoutComputationReturnsNull(1.seconds) } })
}
