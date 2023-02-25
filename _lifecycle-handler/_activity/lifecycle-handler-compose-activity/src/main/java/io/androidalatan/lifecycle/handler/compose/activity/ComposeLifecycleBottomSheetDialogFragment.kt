package io.androidalatan.lifecycle.handler.compose.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.androidalatan.backkey.handler.ActivityBackKeyObserver
import io.androidalatan.backkey.handler.BackKeyHandlerStreamImpl
import io.androidalatan.backkey.handler.api.BackKeyHandlerStream
import io.androidalatan.bundle.BundleDataImpl
import io.androidalatan.bundle.IntentDataImpl
import io.androidalatan.bundle.collector.BundleCollectorStreamImpl
import io.androidalatan.bundle.collector.api.BundleCollectorStream
import io.androidalatan.component.view.compose.ComposeViewInteractionTriggerImpl
import io.androidalatan.coroutine.dispatcher.api.DispatcherProvider
import io.androidalatan.lifecycle.handler.activity.LifecycleNotifierImpl
import io.androidalatan.lifecycle.handler.api.ChildLifecycleSource
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.api.LifecycleNotifier
import io.androidalatan.lifecycle.handler.api.LifecycleViewModelStoreOwner
import io.androidalatan.lifecycle.handler.compose.activity.localowners.LocalComposeEventTriggerOwner
import io.androidalatan.lifecycle.handler.compose.activity.localowners.LocalLifecycleViewModelStoreOwner
import io.androidalatan.lifecycle.handler.compose.util.LocalLifecycleNotifierOwner
import io.androidalatan.lifecycle.handler.internal.invoke.AsyncInvokerManager
import io.androidalatan.lifecycle.handler.internal.invoke.InvokerManagerImpl
import io.androidalatan.lifecycle.handler.internal.invoke.SyncInvokerManager
import io.androidalatan.lifecycle.handler.internal.invoke.coroutine.CoroutineInvokerManagerImpl
import io.androidalatan.lifecycle.handler.invokeradapter.api.InvokeAdapterInitializer
import io.androidalatan.lifecycle.handler.store.LifecycleViewModelStoreOwnerImpl
import io.androidalatan.lifecycle.lazyprovider.LazyProvider
import io.androidalatan.request.permission.PermissionExplanationBuilderFactoryImpl
import io.androidalatan.request.permission.PermissionInvokerImpl
import io.androidalatan.request.permission.PermissionStreamImpl
import io.androidalatan.request.permission.api.PermissionStream
import io.androidalatan.result.handler.ResultStreamImpl
import io.androidalatan.result.handler.api.ResultStream
import io.androidalatan.router.FragmentRouter
import io.androidalatan.router.api.Router
import io.androidalatan.view.event.api.ViewInteractionStream
import io.androidalatan.view.event.compose.ViewInteractionControllerImpl
import io.androidalatan.view.event.impl.ViewInteractionStreamImpl

abstract class ComposeLifecycleBottomSheetDialogFragment(
    private val lazyProvider: LazyProvider<Fragment>,
    private val lazyActivityProvider: LazyProvider<FragmentActivity>,
    private val bundleCollectorStream: BundleCollectorStreamImpl = BundleCollectorStreamImpl(),
    private val resultStream: ResultStreamImpl = ResultStreamImpl(),
    private val fragmentRouter: FragmentRouter = FragmentRouter(lazyProvider),
    private val permissionStream: PermissionStreamImpl = PermissionStreamImpl(
        PermissionInvokerImpl(lazyActivityProvider),
        PermissionExplanationBuilderFactoryImpl(lazyActivityProvider)
    ),
    private val backKeyHandlerStreamImpl: BackKeyHandlerStreamImpl = BackKeyHandlerStreamImpl(),
    private val viewInteractionStream: ViewInteractionStreamImpl = ViewInteractionStreamImpl(),
) : BottomSheetDialogFragment(), ChildLifecycleSource,
    BundleCollectorStream by bundleCollectorStream,
    ResultStream by resultStream,
    Router by fragmentRouter,
    PermissionStream by permissionStream,
    BackKeyHandlerStream by backKeyHandlerStreamImpl,
    ViewInteractionStream by viewInteractionStream {

    constructor() : this(LazyProvider<Fragment>(), LazyProvider<FragmentActivity>())

    private val invokeManager = InvokerManagerImpl(
        syncInvokerManager = SyncInvokerManager(
            coroutineInvokerManager = CoroutineInvokerManagerImpl(DispatcherProvider())
        ),
        asyncInvokerManager = AsyncInvokerManager(
            parameterInstances = mapOf(
                BundleCollectorStream::class.java to bundleCollectorStream,
                PermissionStream::class.java to permissionStream,
                ResultStream::class.java to resultStream,
                Router::class.java to fragmentRouter,
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

    private val composeViewInteractionTrigger = ComposeViewInteractionTriggerImpl()

    private val viewModelStoreOwner: LifecycleViewModelStoreOwner = LifecycleViewModelStoreOwnerImpl()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val composeView = ComposeView(requireContext())
        composeView.setContent {
            CompositionLocalProvider(
                LocalComposeEventTriggerOwner provides composeViewInteractionTrigger,
                LocalLifecycleNotifierOwner provides lifecycleNotifier,
                LocalLifecycleViewModelStoreOwner provides viewModelStoreOwner,
            ) {
                LifecycleHandle {
                    contentView()
                }
            }
        }
        return composeView
    }

    @SuppressLint("ComposableNaming")
    @Composable
    abstract fun contentView()

    override fun onAttach(context: Context) {
        lazyProvider.set(this)
        activity?.let {
            lazyActivityProvider.set(it)
        }
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewInteractionStream.setViewController(ViewInteractionControllerImpl(composeViewInteractionTrigger))
        backKeyHandlerStreamImpl.setBackKeyObserver(ActivityBackKeyObserver(requireActivity()))

        val bundle = Bundle()
        savedInstanceState?.let { bundle.putAll(it) }
        arguments?.let { extras -> bundle.putAll(extras) }
        bundleCollectorStream.updateIntent(IntentDataImpl(Intent(), BundleDataImpl(bundle)))

    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        resultStream.newResultInfo(requestCode, resultCode, data)
    }

    @Suppress("DEPRECATION")
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