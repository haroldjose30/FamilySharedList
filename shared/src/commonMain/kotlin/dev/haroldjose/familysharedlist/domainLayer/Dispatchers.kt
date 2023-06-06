package dev.haroldjose.familysharedlist.domainLayer

import kotlin.coroutines.CoroutineContext

expect val uiDispatcher: CoroutineContext

expect val defaultDispatcher: CoroutineContext
