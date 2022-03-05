package io.androidalatan.router.api.journey

sealed interface Journey {
    fun end()
    fun visit(): Journey
}