package io.androidalatan.lifecycle.handler.compose.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import io.androidalatan.backkey.handler.ActivityBackKeyObserver
import io.androidalatan.backkey.handler.BackKeyHandlerStreamImpl
import io.androidalatan.backkey.handler.api.BackKeyHandlerStream
import io.androidalatan.bundle.BundleDataImpl
import io.androidalatan.bundle.IntentDataImpl
import io.androidalatan.bundle.collector.BundleCollectorStreamImpl
import io.androidalatan.bundle.collector.api.BundleCollectorStream
import io.androidalatan.component.view.compose.ComposeKeyboardControllerImpl
import io.androidalatan.component.view.compose.ComposeViewInteractionTriggerImpl
import io.androidalatan.component.view.compose.api.ComposeKeyboardController
import io.androidalatan.compose.dialog.ComposeAlertDialogBuilderFactoryImpl
import io.androidalatan.compose.dialog.api.ComposeAlertDialogBuilderFactory
import io.androidalatan.compose.holder.lifecycle.activator.LifecycleActivator
import io.androidalatan.coroutine.dispatcher.api.DispatcherProvider
import io.androidalatan.lifecycle.handler.activity.LifecycleNotifierImpl
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.api.LifecycleNotifier
import io.androidalatan.lifecycle.handler.api.LifecycleSource
import io.androidalatan.lifecycle.handler.compose.activity.localowners.LocalComposeEventTriggerOwner
import io.androidalatan.lifecycle.handler.compose.activity.localowners.LocalComposeKeyboardControllerOwner
import io.androidalatan.lifecycle.handler.compose.cache.ComposeCacheProvider
import io.androidalatan.lifecycle.handler.compose.cache.LocalComposeComposeCacheOwner
import io.androidalatan.lifecycle.handler.compose.cache.composeCacheProvider
import io.androidalatan.lifecycle.handler.compose.util.LocalLifecycleNotifierOwner
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
import io.androidalatan.view.event.api.ViewInteractionStream
import io.androidalatan.view.event.compose.ViewInteractionControllerImpl
import io.androidalatan.view.event.impl.ViewInteractionStreamImpl

abstract class ComposeLifecycleActivity private constructor(
    private val lazyProvider: LazyProvider<FragmentActivity>,
    private val bundleCollectorStream: BundleCollectorStreamImpl = BundleCollectorStreamImpl(),
    private val resultStream: ResultStreamImpl = ResultStreamImpl(),
    private val activityRouter: ActivityRouter = ActivityRouter(lazyProvider),
    private val permissionStream: PermissionStreamImpl = PermissionStreamImpl(
        PermissionInvokerImpl(lazyProvider),
        PermissionExplanationBuilderFactoryImpl(lazyProvider)
    ),
    private val backKeyHandlerStreamImpl: BackKeyHandlerStreamImpl = BackKeyHandlerStreamImpl(),
    private val composeKeyboardController: ComposeKeyboardControllerImpl = ComposeKeyboardControllerImpl(),
    private val viewInteractionStream: ViewInteractionStreamImpl = ViewInteractionStreamImpl(),
) : AppCompatActivity(), LifecycleSource,
    BundleCollectorStream by bundleCollectorStream,
    ResultStream by resultStream,
    Router by activityRouter,
    PermissionStream by permissionStream,
    BackKeyHandlerStream by backKeyHandlerStreamImpl,
    ViewInteractionStream by viewInteractionStream,
    ComposeCacheProvider by composeCacheProvider() {

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
                BackKeyHandlerStream::class.java to backKeyHandlerStreamImpl,
                ComposeKeyboardController::class.java to composeKeyboardController,
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

    val composeAlertDialogBuilderFactory: ComposeAlertDialogBuilderFactory = ComposeAlertDialogBuilderFactoryImpl()

    private val lifecycleNotifier: LifecycleNotifier = LifecycleNotifierImpl(invokeManager)

    private val composeViewInteractionTrigger = ComposeViewInteractionTriggerImpl()

    /**
     * If your activity needs to register listener dynamically, e.g) Tab, you should not override this or keep this false
     */
    protected open val activateAllListenersWhenInit: Boolean = true

    private val lifecycleActivator = LifecycleActivator()

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        lazyProvider.set(this)
        super.onCreate(savedInstanceState)

        viewInteractionStream.setViewController(ViewInteractionControllerImpl(composeViewInteractionTrigger))
        backKeyHandlerStreamImpl.setBackKeyObserver(ActivityBackKeyObserver(this))

        val activityIntent = intent ?: Intent()
        val bundle = Bundle()
        activityIntent.extras?.let { bundle.putAll(it) }
        savedInstanceState?.let { bundle.putAll(it) } // let savedInstance override bundle
        bundleCollectorStream.updateIntent(IntentDataImpl(activityIntent, BundleDataImpl(bundle)))

        val composeCache = composeCache()

        setContent {
            LocalSoftwareKeyboardController.current?.let {
                composeKeyboardController.setKeyboardController(it)
            }

            CompositionLocalProvider(
                LocalComposeEventTriggerOwner provides composeViewInteractionTrigger,
                LocalLifecycleNotifierOwner provides lifecycleNotifier,
                LocalComposeKeyboardControllerOwner provides composeKeyboardController,
                LocalComposeComposeCacheOwner provides composeCache,
            ) {
                LifecycleHandle {
                    (composeAlertDialogBuilderFactory as ComposeAlertDialogBuilderFactoryImpl).activate()
                    lifecycleActivator.activateAllIfNeeded(activateAllListenersWhenInit)
                    contentView()
                }
            }
        }

        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    composeCache.clear()
                }
            }
        })
    }

    @SuppressLint("ComposableNaming")
    @Composable
    abstract fun contentView()

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            val mergedBundle = Bundle()
            mergedBundle.putAll(
                bundleCollectorStream.getCurrentBundle()
                    .rawBundle()
            )
            intent.extras.let { mergedBundle.putAll(it) } // let newIntent override bundle
            bundleCollectorStream.updateIntent(IntentDataImpl(intent, BundleDataImpl(mergedBundle)))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        (bundleCollectorStream.getCurrentBundle() as? BundleDataImpl)
            ?.rawBundle()
            ?.let { outState.putAll(it) }
        super.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        resultStream.newResultInfo(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionStream.onResult(requestCode, permissions, grantResults)
    }

    override fun add(listener: LifecycleListener) {
        lifecycleActivator.add(listener)
    }

    override fun remove(listener: LifecycleListener) {
        lifecycleActivator.remove(listener)
    }
}