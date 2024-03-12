package ru.nurdaulet.dummyproducts.utils

import kotlin.math.roundToInt

fun Double.roundTo2digits(): Double {
    return this.times(100).roundToInt().toDouble().div(100)
}
