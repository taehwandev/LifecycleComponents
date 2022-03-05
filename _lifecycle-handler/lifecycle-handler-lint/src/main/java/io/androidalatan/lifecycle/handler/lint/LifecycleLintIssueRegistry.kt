package io.androidalatan.lifecycle.handler.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity

@Suppress("UnstableApiUsage")
class LifecycleLintIssueRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf(ISSUE_RETURN_TYPE, ISSUE_VISIBILITY)

    companion object {
        internal val ISSUE_RETURN_TYPE = Issue.create(
            id = "Lifecycle-Annotation-ReturnType",
            briefDescription = "Async Lifecycle Annotation should have return type",
            explanation = "RxJava, Flow return type",
            category = Category.MESSAGES,
            priority = 5,
            severity = Severity.WARNING,
            implementation = Implementation(LifecycleMethodReturnTypeDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )

        internal val ISSUE_VISIBILITY = Issue.create(
            id = "Lifecycle-Annotation-Visibility",
            briefDescription = "Async Lifecycle Annotation recommend to be public visibility",
            explanation = "public scoped",
            category = Category.MESSAGES,
            priority = 5,
            severity = Severity.INFORMATIONAL,
            implementation = Implementation(LifecycleMethodVisibilityDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }
}