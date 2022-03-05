package io.androidalatan.lifecycle.handler.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.android.tools.lint.detector.api.Severity
import org.junit.jupiter.api.Test

@Suppress("UnstableApiUsage")
class LifecycleMethodVisibilityDetectorTest {
    @Test
    fun `private method`() {
        TestLintTask.lint()
            .files(
                LintDetectorTest.kotlin(TestUtils.annotationClass()),
                LintDetectorTest.kotlin(
                    """
                        package other

                        class TestClass {
                        

                            @io.androidalatan.lifecycle.handler.annotations.async.StartedToStop
                            private fun helloWorld() { }
                        }
            """
                )
            )
            .issues(LifecycleLintIssueRegistry.ISSUE_VISIBILITY)
            .allowMissingSdk(true)
            .run()
            .expectCount(1, Severity.INFORMATIONAL)
    }

    @Test
    fun `internal method`() {
        TestLintTask.lint()
            .files(
                LintDetectorTest.kotlin(TestUtils.annotationClass()),
                LintDetectorTest.kotlin(
                    """
                        package other

                        class TestClass {
                        

                            @io.androidalatan.lifecycle.handler.annotations.async.StartedToStop
                            internal fun helloWorld() { }
                        }
            """
                )
            )
            .issues(LifecycleLintIssueRegistry.ISSUE_VISIBILITY)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }

    @Test
    fun `public method`() {
        TestLintTask.lint()
            .files(
                LintDetectorTest.kotlin(TestUtils.annotationClass()),
                LintDetectorTest.kotlin(
                    """
                        package other
                        
                        class TestClass {
                        

                            @io.androidalatan.lifecycle.handler.annotations.async.StartedToStop
                            fun helloWorld() { }
                        }
            """
                )
            )
            .issues(LifecycleLintIssueRegistry.ISSUE_VISIBILITY)
            .allowMissingSdk(true)
            .run()
            .expectClean()
    }
}