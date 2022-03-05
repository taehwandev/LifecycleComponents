package io.androidalatan.lifecycle.handler.assertion

import io.androidalatan.lifecycle.handler.api.ChildLifecycleSource
import io.androidalatan.lifecycle.handler.api.LifecycleSource

class MockChildLifecycleSource : ChildLifecycleSource, LifecycleSource by MockLifecycleSource()