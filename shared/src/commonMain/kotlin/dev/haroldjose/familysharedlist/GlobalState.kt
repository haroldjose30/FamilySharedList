package dev.haroldjose.familysharedlist

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.native.concurrent.ThreadLocal

class GlobalState {

    @ThreadLocal
    companion object {

        var isRunningUITests = false
    }
}


val defaultLocalDateTime: LocalDateTime get() {
    val year = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
    return LocalDateTime(year, 1, 1, 0, 0, 0, 0)
}

val currentDateTime: LocalDateTime get() {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}