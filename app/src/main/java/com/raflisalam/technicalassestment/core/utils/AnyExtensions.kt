package com.raflisalam.technicalassestment.core.utils

fun String?.orEmpty(): String = this ?: ""

fun emptyString() = ""

fun Int?.orZero(): Int = this ?: 0

fun Double?.orZero(): Double = this ?: 0.0

fun Float?.orZero(): Float = this ?: 0f

fun Long?.orZero(): Long = this ?: 0

fun Boolean?.orFalse(): Boolean = this ?: false

fun String.normalizeAvatarPath(): String =
if (startsWith("/https://") || startsWith("/http://")) drop(1) else this

fun <T> T?.orDefault(default: T): T {
    return this ?: default
}
