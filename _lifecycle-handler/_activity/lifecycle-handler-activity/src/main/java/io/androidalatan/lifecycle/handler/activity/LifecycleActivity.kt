package io.androidalatan.lifecycle.handler.activity

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.androidalatan.backkey.handler.ActivityBackKeyObserver
import io.androidalatan.backkey.handler.BackKeyHandlerStreamImpl
import io.androidalatan.backkey.handler.api.BackKeyHandlerStream
import io.androidalatan.bundle.BundleDataImpl
import io.androidalatan.bundle.IntentDataImpl
import io.androidalatan.bundle.collector.BundleCollectorStreamImpl
import io.androidalatan.bundle.collector.api.BundleCollectorStream
import io.androidalatan.coroutine.dispatcher.api.DispatcherProvider
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.api.LifecycleNotifier
import io.androidalatan.lifecycle.handler.api.LifecycleSource
import io.androidalatan.lifecycle.handler.internal.invoke.AsyncInvokerManager
import io.androidalatan.lifecycle.handler.internal.invoke.InvokerManagerImpl
import io.androidalatan.lifecycle.handler.internal.invoke.SyncInvokerManager
import io.androidalatan.lifecycle.handler.internal.invoke.coroutine.CoroutineInvokerManagerImpl
import io.androidalatan.lifecycle.handler.invokeradapter.api.InvokeAdapterInitializer
import io.androidalatan.lifecycle.lazyprovider.LazyProvider
import io.androidalatan.request.permission.PermissionExplanationBuilderFactoryImpl
import io.androidalatan.request.permission.PermissionInvokerImpl
import io.androidalatan.request.permission.PermissionStreamImpl
import io.androidalatan.request.permission.api.PermissionStream
import io.androidalatan.result.handler.ResultStreamImpl
import io.androidalatan.result.handler.api.ResultStream
import io.androidalatan.router.ActivityRouter
import io.androidalatan.router.api.Router
import io.androidalatan.view.event.api.ViewAccessor
import io.androidalatan.view.event.api.ViewAccessorStream
import io.androidalatan.view.event.api.ViewInteractionStream
import io.androidalatan.view.event.impl.ViewInteractionStreamImpl
import io.androidalatan.view.event.legacy.ViewAccessorImpl
import io.androidalatan.view.event.legacy.ViewAccessorStreamImpl
import io.androidalatan.view.event.legacy.ViewInteractionControllerImpl

abstract class LifecycleActivity private constructor(
    private val lazyProvider: LazyProvider<FragmentActivity>,
    private val viewAccessorStream: ViewAccessorStreamImpl = ViewAccessorStreamImpl(),
    private val bundleCollectorStream: BundleCollectorStreamImpl = BundleCollectorStreamImpl(),
    private val resultStream: ResultStreamImpl = ResultStreamImpl(),
    private val activityRouter: ActivityRouter = ActivityRouter(lazyProvider),
    private val permissionStream: PermissionStreamImpl = PermissionStreamImpl(
        PermissionInvokerImpl(lazyProvider),
        PermissionExplanationBuilderFactoryImpl(lazyProvider)
    ),
    private val backKeyHandlerStreamImpl: BackKeyHandlerStreamImpl = BackKeyHandlerStreamImpl(),
    private val viewInteractionStream: ViewInteractionStreamImpl = ViewInteractionStreamImpl()
) : AppCompatActivity(), LifecycleSource,
    ViewAccessorStream by viewAccessorStream,
    BundleCollectorStream by bundleCollectorStream,
    ResultStream by resultStream,
    Router by activityRouter,
    PermissionStream by permissionStream,
    BackKeyHandlerStream by backKeyHandlerStreamImpl,
    ViewInteractionStream by viewInteractionStream {

    constructor() : this(LazyProvider<FragmentActivity>())

    private val invokeManager = InvokerManagerImpl(
        syncInvokerManager = SyncInvokerManager(
            coroutineInvokerManager = CoroutineInvokerManagerImpl(DispatcherProvider())
        ),
        asyncInvokerManager = AsyncInvokerManager(
            parameterInstances = mapOf(
                ResultStream::class.java to resultStream,
                PermissionStream::class.java to permissionStream,
                BundleCollectorStream::class.java to bundleCollectorStream,
                Router::class.java to activityRouter,
                ViewAccessorStream::class.java to viewAccessorStream,
                BackKeyHandlerStream::class.java to backKeyHandlerStreamImpl,
                ViewInteractionStream::class.java to viewInteractionStream,
            ),
            invokeAdapters = kotlin.run {
                val tempLifecycle = lifecycle
                val tempLogger = InvokeAdapterInitializer.logger()
                InvokeAdapterInitializer.factories()
                    .map {
                        it.create(tempLifecycle, tempLogger)
                    }
            }
        )
    )

    private val lifecycleNotifier: LifecycleNotifier = LifecycleNotifierImpl(invokeManager)

    @get:LayoutRes
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        lazyProvider.set(this)
        super.onCreate(savedInstanceState)
        val viewDataBinding = DataBindingUtil.setContentView<ViewDataBinding>(this, layoutId)

        val viewAccessor = ViewAccessorImpl(viewDataBinding.root)
        viewAccessorStream.setViewAccessor(viewAccessor)
        backKeyHandlerStreamImpl.setBackKeyObserver(ActivityBackKeyObserver(this))
        viewInteractionStream.setViewController(ViewInteractionControllerImpl(viewAccessor))
        viewInit(viewAccessor)

        val activityIntent = intent ?: Intent()
        val bundle = Bundle()
        activityIntent.extras?.let { bundle.putAll(it) }
        savedInstanceState?.let { bundle.putAll(it) } // let savedInstance override bundle
        bundleCollectorStream.updateIntent(IntentDataImpl(activityIntent, BundleDataImpl(bundle)))

        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                lifecycleNotifier.triggerCreated()
            }

            override fun onStart(owner: LifecycleOwner) {
                lifecycleNotifier.triggerStarted()
            }

            override fun onResume(owner: LifecycleOwner) {
                lifecycleNotifier.triggerResumed()
            }

            override fun onPause(owner: LifecycleOwner) {
                lifecycleNotifier.triggerPause()
            }

            override fun onStop(owner: LifecycleOwner) {
                lifecycleNotifier.triggerStop()
            }

            override fun onDestroy(owner: LifecycleOwner) {
                lifecycleNotifier.triggerDestroy()
            }
        })

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            val mergedBundle = Bundle()
            mergedBundle.putAll(bundleCollectorStream.getCurrentBundle().rawBundle())
            intent.extras?.let { mergedBundle.putAll(it) } // let newIntent override bundle
            bundleCollectorStream.updateIntent(IntentDataImpl(intent, BundleDataImpl(mergedBundle)))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        (bundleCollectorStream.getCurrentBundle() as? BundleDataImpl)
            ?.rawBundle()
            ?.let { outState.putAll(it) }
        super.onSaveInstanceState(outState)
    }

    abstract fun viewInit(viewAccessor: ViewAccessor)

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        resultStream.newResultInfo(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionStream.onResult(requestCode, permissions, grantResults)
    }

    override fun add(listener: LifecycleListener) {
        lifecycleNotifier.add(listener)
    }

    override fun remove(listener: LifecycleListener) {
        lifecycleNotifier.remove(listener)
    }
}