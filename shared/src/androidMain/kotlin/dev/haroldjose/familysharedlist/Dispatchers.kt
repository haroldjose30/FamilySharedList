package dev.haroldjose.familysharedlist

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal actual val DispatcherMain: CoroutineDispatcher = Dispatchers.Main

internal actual val DispatcherBackground: CoroutineDispatcher = Dispatchers.Default