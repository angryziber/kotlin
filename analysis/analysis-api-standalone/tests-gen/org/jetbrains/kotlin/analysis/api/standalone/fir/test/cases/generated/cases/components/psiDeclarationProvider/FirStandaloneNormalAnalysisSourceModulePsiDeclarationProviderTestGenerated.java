/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.standalone.fir.test.cases.generated.cases.components.psiDeclarationProvider;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.analysis.api.standalone.fir.test.configurators.AnalysisApiFirStandaloneModeTestConfiguratorFactory;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiTestConfiguratorFactoryData;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiTestConfigurator;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.TestModuleKind;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.FrontendKind;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisSessionMode;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiMode;
import org.jetbrains.kotlin.analysis.api.standalone.fir.test.cases.components.psiDeclarationProvider.AbstractPsiDeclarationProviderTest;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.analysis.api.GenerateAnalysisApiTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("analysis/analysis-api/testData/standalone/source")
@TestDataPath("$PROJECT_ROOT")
public class FirStandaloneNormalAnalysisSourceModulePsiDeclarationProviderTestGenerated extends AbstractPsiDeclarationProviderTest {
    @NotNull
    @Override
    public AnalysisApiTestConfigurator getConfigurator() {
        return AnalysisApiFirStandaloneModeTestConfiguratorFactory.INSTANCE.createConfigurator(
            new AnalysisApiTestConfiguratorFactoryData(
                FrontendKind.Fir,
                TestModuleKind.Source,
                AnalysisSessionMode.Normal,
                AnalysisApiMode.Standalone
            )
        );
    }

    @Test
    public void testAllFilesPresentInSource() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/analysis-api/testData/standalone/source"), Pattern.compile("^(.+)\\.kt$"), null, true);
    }

    @Test
    @TestMetadata("capitalize_default.kt")
    public void testCapitalize_default() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/source/capitalize_default.kt");
    }

    @Test
    @TestMetadata("capitalize_locale.kt")
    public void testCapitalize_locale() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/source/capitalize_locale.kt");
    }

    @Test
    @TestMetadata("listIterator.kt")
    public void testListIterator() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/source/listIterator.kt");
    }

    @Test
    @TestMetadata("mapGetOrDefault.kt")
    public void testMapGetOrDefault() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/source/mapGetOrDefault.kt");
    }

    @Test
    @TestMetadata("mapGetOrDefault_nullable.kt")
    public void testMapGetOrDefault_nullable() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/source/mapGetOrDefault_nullable.kt");
    }

    @Test
    @TestMetadata("multipleFiles.kt")
    public void testMultipleFiles() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/source/multipleFiles.kt");
    }

    @Test
    @TestMetadata("setOf_last_vararg.kt")
    public void testSetOf_last_vararg() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/source/setOf_last_vararg.kt");
    }

    @Test
    @TestMetadata("singleFile.kt")
    public void testSingleFile() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/source/singleFile.kt");
    }

    @Test
    @TestMetadata("todo.kt")
    public void testTodo() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/source/todo.kt");
    }
}
