package io.androidalatan.lifecycle.handler.sample

import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import io.androidalatan.backkey.flow.handler.onBackPressedAsFlow
import io.androidalatan.backkey.handler.api.BackKeyHandlerStream
import io.androidalatan.bundle.collector.api.BundleCollectorStream
import io.androidalatan.bundle.collector.flow.adapter.intentData
import io.androidalatan.coroutine.dispatcher.api.DispatcherProvider
import io.androidalatan.databinding.observables.ObservableString
import io.androidalatan.lifecycle.handler.annotations.async.CreatedToDestroy
import io.androidalatan.lifecycle.handler.annotations.async.ResumedToPause
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.api.LifecycleSource
import io.androidalatan.lifecycle.handler.sample.adapter.PersonAdapter
import io.androidalatan.lifecycle.handler.sample.bottomsheet.SampleBottomSheetDialogFragment
import io.androidalatan.lifecycle.handler.sample.model.Person
import io.androidalatan.lifecycle.handler.sample.prefs.SamplePrefs
import io.androidalatan.request.permission.api.PermissionResult
import io.androidalatan.request.permission.api.PermissionStream
import io.androidalatan.request.permission.api.exception.PermissionGrantException
import io.androidalatan.request.permission.flow.handler.requestAsFlow
import io.androidalatan.result.handler.api.ResultInfo
import io.androidalatan.result.handler.api.ResultStream
import io.androidalatan.result.handler.flow.adapter.resultInfoAsFlow
import io.androidalatan.router.api.Router
import io.androidalatan.view.event.api.ViewInteractionStream
import io.androidalatan.view.event.api.view.OnSizeChangeEvent
import io.androidalatan.view.event.legacy.flow.asFlow
import io.androidalatan.view.event.legacy.flow.view.onClickAsFlow
import io.androidalatan.view.event.legacy.flow.view.onSizeChangeAsFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class SampleViewModel(
    lifecycleSource: LifecycleSource,
    private val activity: FragmentActivity,
    private val dispatcherProvider: DispatcherProvider,
    val adapter: PersonAdapter,
    private val samplePrefs: SamplePrefs,
) : LifecycleListener(lifecycleSource) {

    val text = ObservableString()
    val bundleText = ObservableString()
    val locationGrantedText = ObservableString("Try...")
    val countDownTextCreateToDestroy = ObservableString()
    val countDownTextResumeToPause = ObservableString()
    val countDownTextResumeToPauseFlow = ObservableString("Count : 0")

    val personText = ObservableString("")
    val personName = ObservableString("")

    @ResumedToPause
    fun updateTextView(viewInteractionStream: ViewInteractionStream): Flow<Long> {
        var count = 0
        return viewInteractionStream.asFlow()
            .flatMapLatest {
                it.find(R.id.text)
                    .onClickAsFlow()
                    .onEach {
                        text.set("${count++}")
                    }
            }
            .onStart {
                text.set("Ready bro!!")
            }
            .catch { Log.d(this::class.java.simpleName, "updateTextView: ${it.localizedMessage}") }
    }

    @ResumedToPause
    fun updateTestViewSize(viewInteractionStream: ViewInteractionStream): Flow<OnSizeChangeEvent.ViewSize> {
        return viewInteractionStream.asFlow()
            .flatMapLatest {
                it.find(R.id.text)
                    .onSizeChangeAsFlow()
            }
            .onEach { (width, height) ->
                Log.i(this::class.java.simpleName, "View Size [$width, $height]")
            }
    }

    @ResumedToPause
    fun observeClick(viewInteractionStream: ViewInteractionStream): Flow<Long> {
        return viewInteractionStream.asFlow()
            .flatMapLatest {
                it.find(R.id.button)
                    .onClickAsFlow()
                    .onEach {
                        SampleBottomSheetDialogFragment().show(activity.supportFragmentManager, "SampleBottomSheetDialogFragment")
                    }
                    .flowOn(dispatcherProvider.main())
            }
    }

    @CreatedToDestroy
    fun observeNamePref(): Flow<String> {
        return samplePrefs.getName()
            .onEach {
                personText.set(it)
            }
    }

    @ResumedToPause
    fun observeClickName(viewInteractionStream: ViewInteractionStream): Flow<Boolean> {
        return viewInteractionStream.asFlow()
            .flatMapLatest { view ->
                view.find(R.id.btn_pref_set_object)
                    .onClickAsFlow()
                    .flatMapLatest {
                        samplePrefs.setName(personName.get())
                    }
            }
    }

    @ResumedToPause
    fun observeClickClear(viewInteractionStream: ViewInteractionStream): Flow<Boolean> {
        return viewInteractionStream.asFlow()
            .flatMapLatest { view ->
                view.find(R.id.btn_pref_clear)
                    .onClickAsFlow()
                    .flatMapLatest { samplePrefs.clear() }
            }
    }

    @CreatedToDestroy
    fun observeResult(resultStream: ResultStream): Flow<ResultInfo> {
        return resultStream.resultInfoAsFlow(REQ_CODE)
            .onEach {
                Toast.makeText(activity, "Got Result: ${it.resultCode()}", Toast.LENGTH_LONG)
                    .show()
            }
            .flowOn(dispatcherProvider.main())
    }

    @CreatedToDestroy
    fun countDownCreateToDestroy(): Flow<Int> {
        return (0..100)
            .asFlow()
            .flowOn(dispatcherProvider.main())
            .onEach { value ->
                countDownTextCreateToDestroy.set("$value")
                delay(1000)
            }
    }

    @ResumedToPause
    fun countDownResumeToPause(): Flow<Int> {
        return (0..10)
            .asFlow()
            .flowOn(dispatcherProvider.default())
            .map { value -> 10 - value }
            .flowOn(dispatcherProvider.main())
            .onEach { value ->
                countDownTextResumeToPause.set("$value")
                delay(1000)
            }
    }

    @CreatedToDestroy
    fun savedInstances(bundleCollectorStream: BundleCollectorStream): Flow<Boolean> {
        return bundleCollectorStream.intentData()
            .onEach { bundleData ->
                val count: Int = (bundleData.getIntOrNull("count") ?: 0) + 1
                bundleData.setInt("count", count)
                bundleText.set("$count")
            }
            .map { true }

    }

    @CreatedToDestroy
    fun setPersonData(): Flow<List<Person>> {
        return flowOf("Steve", "John", "Jonathan", "Mary", "Maria")
            .map { Person(it) }
            .let {  source ->
                flow {
                    val people = mutableListOf<Person>()
                    source.collect { person ->
                        people.add(person)
                    }
                    emit(people.toList())
                }
            }
            .onEach {
                adapter.setData(it)
            }
            .flowOn(dispatcherProvider.main())
    }

    @ResumedToPause
    fun observeBackey(backKeyHandlerStream: BackKeyHandlerStream, router: Router): Flow<Long> {
        return backKeyHandlerStream.onBackPressedAsFlow()
            .onEach {
                // do something
                router.end()
            }
    }

    @ResumedToPause
    fun flowBackey(backKeyHandlerStream: BackKeyHandlerStream): Flow<Long> {
        return backKeyHandlerStream.onBackPressedAsFlow()
            .onEach {
                // do something
            }
    }

    @CreatedToDestroy
    fun locationPermission(
        viewInteractionStream: ViewInteractionStream,
        permissionStream: PermissionStream
    ): Flow<PermissionResult> {
        return viewInteractionStream.asFlow()
            .flatMapLatest {
                it.find(R.id.invoke_permission)
                    .onClickAsFlow()
                    .flatMapLatest {
                        permissionStream.requestAsFlow(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 19823) { builder ->
                            builder.title("Permission")
                                .message("Location...")
                                .confirmButtonText("Confirm")
                                .build()
                        }
                            .onEach { result ->
                                val allGranted = result.result.all { it.second }
                                if (allGranted) {
                                    locationGrantedText.set("Granted!!")
                                } else {
                                    locationGrantedText.set("Failed to Grant")
                                }
                            }
                            .catch { t ->
                                if (t is PermissionGrantException) {
                                    locationGrantedText.set(t.message)
                                    emit(t.origin)
                                } else {
                                    throw t
                                }
                            }
                    }
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @ResumedToPause
    fun flowCounterEvent(viewInteractionStream: ViewInteractionStream): Flow<Long> {
        return viewInteractionStream.asFlow()
            .flatMapLatest { view ->
                var flowCount = 0
                view.find(R.id.btn_flow_count)
                    .onClickAsFlow()
                    .onEach {
                        countDownTextResumeToPauseFlow.set("Count : ${++flowCount}")
                    }
            }

    }

    companion object {
        const val REQ_CODE = 1002
    }
}