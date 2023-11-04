package com.sanmer.geomag.utils.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.Companion.now() =
    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

fun LocalDateTime.toTimeZone(timeZone: TimeZone) =
    toInstant(TimeZone.currentSystemDefault()).toLocalDateTime(timeZone)
