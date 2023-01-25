package dev.haroldjose.sharedfamilylist.domainLayer

import kotlin.coroutines.CoroutineContext

expect val uiDispatcher: CoroutineContext

expect val defaultDispatcher: CoroutineContext
