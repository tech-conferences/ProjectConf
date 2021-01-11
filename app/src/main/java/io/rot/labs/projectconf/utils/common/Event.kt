/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.utils.common

import java.util.concurrent.atomic.AtomicBoolean

data class Event<out T>(private val content: T) {

    private var hasBeenHandled = AtomicBoolean(false)

    fun getIfNotHandled(): T? = if (hasBeenHandled.getAndSet(true)) null else content

    fun peek() = content
}
