/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.objcexport

import org.jetbrains.kotlin.analysis.api.KtAnalysisSession
import org.jetbrains.kotlin.analysis.api.symbols.KtPropertySymbol
import org.jetbrains.kotlin.backend.konan.objcexport.ObjCExportPropertyName


context(KtAnalysisSession, KtObjCExportSession)
fun KtPropertySymbol.getObjCPropertyName(): ObjCExportPropertyName {
    val resolveObjCNameAnnotation = resolveObjCNameAnnotation()

    return ObjCExportPropertyName(
        objCName = resolveObjCNameAnnotation?.objCName ?: name.asString(),
        swiftName = resolveObjCNameAnnotation?.swiftName ?: name.asString()
    )
}
