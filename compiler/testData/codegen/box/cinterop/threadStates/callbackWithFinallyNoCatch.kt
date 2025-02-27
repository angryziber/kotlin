/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */
// TARGET_BACKEND: NATIVE
// NATIVE_STANDALONE
// FREE_COMPILER_ARGS: -opt-in=kotlin.native.internal.InternalForKotlinNative
// MODULE: cinterop
// FILE: threadStates.def
language = C
---
void assertNativeThreadState();

void runCallback(void(*callback)(void)) {
    assertNativeThreadState();
    callback();
    assertNativeThreadState();
}

// FILE: threadStates.cpp
#include <stdio.h>
#include <stdlib.h>

// Implemented in the runtime for test purposes.
extern "C" bool Kotlin_Debugging_isThreadStateNative();

extern "C" void assertNativeThreadState() {
    if (!Kotlin_Debugging_isThreadStateNative()) {
        printf("Incorrect thread state. Expected native thread state.");
        abort();
    }
}

// MODULE: main(cinterop)
// FILE: main.kt
@file:OptIn(kotlin.native.runtime.NativeRuntimeApi::class, kotlinx.cinterop.ExperimentalForeignApi::class)

import kotlin.native.runtime.Debugging
import kotlin.test.*
import kotlinx.cinterop.staticCFunction
import threadStates.*

fun box(): String {
    try {
        try {
            runCallback(staticCFunction(::throwException))
        } finally {
            assertRunnableThreadState()
        }
        assertRunnableThreadState()
    } catch (_: CustomException) {}
    return "OK"
}

fun assertRunnableThreadState() {
    assertTrue(Debugging.isThreadStateRunnable)
}

class CustomException() : Exception()

fun throwException() {
    assertRunnableThreadState()
    throw CustomException()
}
