package io.androidalatan.router.api

import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
annotation class JourneyKey(val value: KClass<out JourneyGuidance>)
