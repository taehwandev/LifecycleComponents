package io.androidalatan.request.permission.api

fun interface PermissionExplanationBuilderFactory {
    fun explanationBuilder(): PermissionExplanationBuilder
}