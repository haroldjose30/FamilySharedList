package dev.haroldjose.familysharedlist

import kotlinx.coroutines.CoroutineDispatcher

internal expect val DispatcherMain: CoroutineDispatcher

internal expect val DispatcherBackground: CoroutineDispatcher