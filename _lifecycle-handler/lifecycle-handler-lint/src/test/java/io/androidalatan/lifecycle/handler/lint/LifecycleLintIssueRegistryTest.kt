package io.androidalatan.lifecycle.handler.lint

import com.android.tools.lint.client.api.LintClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LifecycleLintIssueRegistryTest {

    @BeforeEach
    fun setUp() {
        LintClient.clientName = LintClient.CLIENT_GRADLE
    }

    @Test
    fun getIssues() {

        assertEquals(listOf(LifecycleLintIssueRegistry.ISSUE_RETURN_TYPE, LifecycleLintIssueRegistry.ISSUE_VISIBILITY), LifecycleLintIssueRegistry()
            .issues)
    }
}