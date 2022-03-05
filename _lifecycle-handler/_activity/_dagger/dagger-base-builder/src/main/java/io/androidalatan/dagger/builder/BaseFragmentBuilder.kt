package io.androidalatan.dagger.builder

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.android.AndroidInjector

@Suppress("DEPRECATION")
abstract class BaseFragmentBuilder<T : Fragment> : AndroidInjector.Builder<T>() {

    override fun seedInstance(instance: T) {
        seedFragment(instance)
        seedBaseFragment(instance)
    }

    @BindsInstance
    abstract fun seedFragment(instance: T)

    @BindsInstance
    abstract fun seedBaseFragment(instance: Fragment)

}