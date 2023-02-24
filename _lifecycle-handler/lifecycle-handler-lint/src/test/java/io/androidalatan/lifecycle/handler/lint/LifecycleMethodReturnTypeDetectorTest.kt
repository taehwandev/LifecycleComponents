package io.androidalatan.lifecycle.handler.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.android.tools.lint.detector.api.Severity
import org.junit.jupiter.api.Test

@Suppress("UnstableApiUsage")
class LifecycleMethodReturnTypeDetectorTest {

    @Test
    fun `flow method`() {
        TestLintTask.lint()
            .files(
                LintDetectorTest.kotlin(TestUtils.coroutineInterface()),
                LintDetectorTest.kotlin(TestUtils.annotationClass()),
                LintDetectorTest.kotlin(
                    """
                        package other

                        class TestClass {
                        
                            @io.androidalatan.lifecycle.handler.annotations.async.StartedToStop
                            fun helloWorld(): kotlinx.coroutines.flow.Flow<Boolean> { }
                        }
            """
                )
            )
            .issues(LifecycleLintIssueRegistry.ISSUE_RETURN_TYPE)
            .allowCompilationErrors()
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }

    @Test
    fun `unit method`() {
        TestLintTask.lint()
            .files(
                LintDetectorTest.kotlin(TestUtils.annotationClass()),
                LintDetectorTest.kotlin(
                    """
                        package other
                        
                        class TestClass {
                        

                            @io.androidalatan.lifecycle.handler.annotations.async.StartedToStop
                            fun helloWorld(): kotlin.Unit { }
                        }
            """
                )
            )
            .issues(LifecycleLintIssueRegistry.ISSUE_RETURN_TYPE)
            .allowMissingSdk(true)
            .run()
            .expectCount(1, Severity.WARNING)
    }
}