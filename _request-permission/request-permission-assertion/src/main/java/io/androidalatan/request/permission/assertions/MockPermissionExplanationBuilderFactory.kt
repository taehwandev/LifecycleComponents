package io.androidalatan.request.permission.assertions

import io.androidalatan.request.permission.api.PermissionExplanationBuilder
import io.androidalatan.request.permission.api.PermissionExplanationBuilderFactory

class MockPermissionExplanationBuilderFactory : PermissionExplanationBuilderFactory {
    override fun explanationBuilder(): PermissionExplanationBuilder {
        return MockPermissionExplanationBuilder()
    }
}