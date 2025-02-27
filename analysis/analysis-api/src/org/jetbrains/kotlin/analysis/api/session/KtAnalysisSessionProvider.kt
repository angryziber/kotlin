/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.session

import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import org.jetbrains.annotations.TestOnly
import org.jetbrains.kotlin.analysis.api.KtAnalysisSession
import org.jetbrains.kotlin.analysis.api.KtAnalysisApiInternals
import org.jetbrains.kotlin.analysis.api.analyzeCopy
import org.jetbrains.kotlin.analysis.api.lifetime.impl.NoWriteActionInAnalyseCallChecker
import org.jetbrains.kotlin.analysis.api.lifetime.KtLifetimeTokenProvider
import org.jetbrains.kotlin.analysis.api.lifetime.KtLifetimeTokenFactory
import org.jetbrains.kotlin.analysis.project.structure.DanglingFileResolutionMode
import org.jetbrains.kotlin.analysis.project.structure.KtModule
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtFile

/**
 * Provides [KtAnalysisSession]s by use-site [KtElement]s or [KtModule]s.
 *
 * This provider should not be used directly.
 * Please use [analyze][org.jetbrains.kotlin.analysis.api.analyze] or [analyzeCopy][org.jetbrains.kotlin.analysis.api.analyzeCopy] instead.
 */
@OptIn(KtAnalysisApiInternals::class)
public abstract class KtAnalysisSessionProvider(public val project: Project) : Disposable {
    @KtAnalysisApiInternals
    public val tokenFactory: KtLifetimeTokenFactory by lazy(LazyThreadSafetyMode.PUBLICATION) {
        KtLifetimeTokenProvider.getService(project).getLifetimeTokenFactory()
    }

    @Suppress("LeakingThis")
    public val noWriteActionInAnalyseCallChecker: NoWriteActionInAnalyseCallChecker = NoWriteActionInAnalyseCallChecker(this)

    public abstract fun getAnalysisSession(useSiteKtElement: KtElement): KtAnalysisSession

    public abstract fun getAnalysisSessionByUseSiteKtModule(useSiteKtModule: KtModule): KtAnalysisSession

    @Deprecated(
        "On-air analysis is obsolete. Use 'analyzeCopy()' instead",
        replaceWith = ReplaceWith(
            "analyzeCopy(elementToReanalyze, DanglingFileAnalysisMode.IGNORE_SELF, action)",
            imports = [
                "org.jetbrains.kotlin.analysis.api.analyzeCopy",
                "org.jetbrains.kotlin.analysis.project.structure.DanglingFileResolutionMode"
            ]
        )
    )
    public inline fun <R> analyseInDependedAnalysisSession(
        @Suppress("unused", "UNUSED_PARAMETER") originalFile: KtFile,
        elementToReanalyze: KtElement,
        crossinline action: KtAnalysisSession.() -> R,
    ): R {
        return analyzeCopy(elementToReanalyze, DanglingFileResolutionMode.IGNORE_SELF, action)
    }

    public inline fun <R> analyse(
        useSiteKtElement: KtElement,
        action: KtAnalysisSession.() -> R,
    ): R {
        return analyse(getAnalysisSession(useSiteKtElement), action)
    }

    public inline fun <R> analyze(
        useSiteKtModule: KtModule,
        action: KtAnalysisSession.() -> R,
    ): R {
        return analyse(getAnalysisSessionByUseSiteKtModule(useSiteKtModule), action)
    }

    public inline fun <R> analyse(
        analysisSession: KtAnalysisSession,
        action: KtAnalysisSession.() -> R,
    ): R {
        noWriteActionInAnalyseCallChecker.beforeEnteringAnalysisContext()
        tokenFactory.beforeEnteringAnalysisContext(analysisSession.token)
        return try {
            analysisSession.action()
        } finally {
            tokenFactory.afterLeavingAnalysisContext(analysisSession.token)
            noWriteActionInAnalyseCallChecker.afterLeavingAnalysisContext()
        }
    }

    @TestOnly
    public abstract fun clearCaches()

    override fun dispose() {}

    public companion object {
        @KtAnalysisApiInternals
        public fun getInstance(project: Project): KtAnalysisSessionProvider =
            project.getService(KtAnalysisSessionProvider::class.java)
    }
}
