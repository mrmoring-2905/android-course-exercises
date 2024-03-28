package com.rxmobileteam.lecture2_3.fraction

class Fraction private constructor(
  val numerator: Int,
  val denominator: Int,
) : Comparable<Fraction> {
  // Implement the decimal value of the fraction
  val decimal: Double = numerator.toDouble() / denominator

  init {
    // Check validity of numerator and denominator (throw an exception if invalid)
    if (denominator == 0) throw ArithmeticException("divide by zero is not allow!")
  }

  //region unary operators
  //"+fraction" operator
  operator fun unaryPlus(): Fraction = Fraction(1 * numerator, denominator)

  //"-fraction" operator
  operator fun unaryMinus(): Fraction = Fraction(-1 * numerator, denominator)
  //endregion

  //region plus operators
  //"fraction+fraction" operator
  operator fun plus(other: Fraction): Fraction {
    val newNumerator = numerator * other.denominator + other.numerator * denominator
    val newDenominator = denominator * other.denominator
    return Fraction(newNumerator, newDenominator)
  }

  //"fraction+number" operator
  operator fun plus(other: Int): Fraction = this + Fraction(other, 1)
  //endregion

  //region times operators
  //"fraction*fraction" operator
  operator fun times(other: Fraction): Fraction = Fraction(numerator * other.numerator, denominator * other.denominator)

  //"fraction*number" operator
  operator fun times(number: Int): Fraction = this * Fraction(number, 1)
  //endregion

  //Compare two fractions
  override fun compareTo(other: Fraction): Int {
    val subFraction = this + (-other)
    return when {
      subFraction.decimal > 0L -> 1
      subFraction.decimal < 0L -> -1
      else -> 0
    }
  }

  //region toString, hashCode, equals, copy
  //Format the fraction as a string (e.g. "1/2")
  override fun toString(): String = "$numerator/$denominator"

  // Implement hashCode
  override fun hashCode(): Int {
    var result = numerator
    result = 31 * result + denominator
    return result
  }

  //
  //Implement equals
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Fraction

    if (numerator != other.numerator) return false
    if (denominator != other.denominator) return false

    return true
  }

  //Implement copy
  fun copy(
    numerator: Int = this.numerator,
    denominator: Int = this.denominator
  ): Fraction = Fraction(numerator, denominator)

  //endregion

  companion object {
    @JvmStatic
    fun ofInt(number: Int): Fraction {
      //Returns a fraction from an integer number
      return Fraction(number, 1) // Change this
    }

    @JvmStatic
    fun of(numerator: Int, denominator: Int): Fraction {
      //Check validity of numerator and denominator
      //Simplify fraction using the greatest common divisor
      //Finally, return the fraction with the correct values
      if (denominator == 0) throw ArithmeticException("Divide by zero is not allowed !")
      val getGCD = getGreatestCommonDivisor(numerator, denominator)
      return Fraction(numerator / getGCD, denominator / getGCD) // Change this
    }
  }
}

private fun getGreatestCommonDivisor(first: Int, second: Int): Int {
  if (second == 0) return first
  return getGreatestCommonDivisor(second, first % second)
}

//return a Fraction representing "this/denominator"
infix fun Int.over(denominator: Int): Fraction = Fraction.of(this, denominator)

//region get extensions
//get the numerator, eg. "val (numerator) = Fraction.of(1, 2)"
operator fun Fraction.component1(): Int = numerator

//get the denominator, eg. "val (_, denominator) = Fraction.of(1, 2)"
operator fun Fraction.component2(): Int = denominator

//get the decimal, index must be 0 or 1
//eg. "val numerator = Fraction.of(1, 2)[0]"
//eg. "val denominator = Fraction.of(1, 2)[1]"
//eg. "val denominator = Fraction.of(1, 2)[2]" should throw an exception
operator fun Fraction.get(index: Int): Int {
  return when(index) {
    0 -> component1()
    1 -> component2()
    else -> throw IndexOutOfBoundsException("$index is out of numbers components")
  }
}
//endregion

//region to number extensions
//round to the nearest integer
fun Fraction.roundToInt(): Int = numerator / denominator

//round to the nearest long
fun Fraction.roundToLong(): Long = numerator.toLong() / denominator

//return the decimal value as a float
fun Fraction.toFloat(): Float = numerator.toFloat() / denominator

//return the decimal value as a double
fun Fraction.toDouble(): Double = decimal
//endregion

fun main() {
  //Creation
  println("1/2: ${Fraction.of(1, 2)}")
  println("2/3: ${Fraction.of(2, 3)}")
  println("8: ${Fraction.ofInt(8)}")
  println("2/4: ${2 over 4}")

  //Unary operators
  println("+2/4: ${+Fraction.of(2, 4)}")
  println("-2/6: ${-Fraction.of(2, 6)}")
  println("compareto 1/2 vs 1/3: ${Fraction.of(1, 2).compareTo(Fraction.of(1, 3))}")
  println(getGreatestCommonDivisor(4, 10))

  // Plus operators
  println("1/2 + 2/3: ${Fraction.of(1, 2) + Fraction.of(2, 3)}")
  println("1/2 + 1: ${Fraction.of(1, 2) + 1}")

  // Times operators
  println("1/2 * 2/3: ${Fraction.of(1, 2) * Fraction.of(2, 3)}")
  println("1/2 * 2: ${Fraction.of(1, 2) * 2}")

  // compareTo
  println("3/2 > 2/2: ${Fraction.of(3, 2) > Fraction.of(2, 2)}")
  println("1/2 <= 2/4: ${Fraction.of(1, 2) < Fraction.of(2, 4)}")
  println("4/6 >= 2/3: ${Fraction.of(4, 6) > Fraction.of(2, 3)}")

  // hashCode
  println("hashCode 1/2 == 2/4: ${Fraction.of(1, 2).hashCode() == Fraction.of(2, 4).hashCode()}")
  println("hashCode 1/2 == 1/2: ${Fraction.of(1, 2).hashCode() == Fraction.of(1, 2).hashCode()}")
  println("hashCode 1/3 == 3/5: ${Fraction.of(1, 3).hashCode() == Fraction.of(3, 5).hashCode()}")

  // equals
  println("1/2 == 2/4: ${Fraction.of(1, 2) == Fraction.of(2, 4)}")
  println("1/2 == 1/2: ${Fraction.of(1, 2) == Fraction.of(1, 2)}")
  println("1/3 == 3/5: ${Fraction.of(1, 3) == Fraction.of(3, 5)}")

  // Copy
  println("Copy 1/2: ${Fraction.of(1, 2).copy()}")
  println("Copy 1/2 with numerator 2: ${Fraction.of(1, 2).copy(numerator = 2)}")
  println("Copy 1/2 with denominator 3: ${Fraction.of(1, 2).copy(denominator = 3)}")
  println("Copy 1/2 with numerator 2 and denominator 3: ${Fraction.of(1, 2).copy(numerator = 2, denominator = 3)}")

  // Component1, Component2 operators
  val (numerator, denominator) = Fraction.of(1, 2)
  println("Component1, Component2 of 1/2: $numerator, $denominator")
  val (numerator2, _) = Fraction.of(10, 30)
  println("Component1 of 10/30: $numerator2")
  val (_, denominator2) = Fraction.of(10, 79)
  println("Component2 of 10/79: $denominator2")

  // Get operator
  println("Get 0 of 1/2: ${Fraction.of(1, 2)[0]}")
  println("Get 1 of 1/2: ${Fraction.of(1, 2)[1]}")
  println("Get 2 of 1/2: ${runCatching { Fraction.of(1, 2)[2] }}") // Should print "Failure(...)"

  // toInt, toLong, toFloat, toDouble
  println("toInt 1/2: ${Fraction.of(1, 2).roundToInt()}")
  println("toLong 1/2: ${Fraction.of(1, 2).roundToLong()}")
  println("toFloat 1/2: ${Fraction.of(1, 2).toFloat()}")
  println("toDouble 1/2: ${Fraction.of(1, 2).toDouble()}")

  // Range
  // Because we implemented Comparable<Fraction>, we can use Fraction in ranges
  val range = Fraction.of(1, 2)..Fraction.of(2, 3)
  println("1/2 in range 1/2..2/3: ${Fraction.of(1, 2) in range}") // "in" operator is contains
  println("2/3 in range 1/2..2/3: ${Fraction.of(2, 3) in range}")
  println("7/12 in range 1/2..2/3: ${Fraction.of(7, 12) in range}")
}
