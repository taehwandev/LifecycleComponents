package io.androidalatan.request.permission.assertions

import io.androidalatan.request.permission.api.PermissionExplanation
import io.androidalatan.request.permission.api.PermissionExplanationBuilder

class MockPermissionExplanationBuilder : PermissionExplanationBuilder() {
    override fun build(): PermissionExplanation {
        return MockPermissionExplanation()
    }
}