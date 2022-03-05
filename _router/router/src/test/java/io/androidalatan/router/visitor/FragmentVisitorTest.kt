package io.androidalatan.router.visitor

import android.content.Intent
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(value = [Fragment::class])
class FragmentVisitorTest {

    private val fragment: Fragment = PowerMockito.mock(Fragment::class.java)
    private val dialogFragment: DialogFragment = PowerMockito.mock(DialogFragment::class.java)
    private var activity: FragmentActivity = mock()
    private var fragmentManager: FragmentManager = mock()
    private val targetFragment = mock<Fragment>()
    private val intent: Intent = mock()
    private val visitor = FragmentVisitor(fragment)

    @Before
    fun setUp() {
        whenever(fragment.activity)
            .thenReturn(activity)

        whenever(fragment.requireActivity())
            .thenReturn(activity)

        whenever(activity.supportFragmentManager)
            .thenReturn(fragmentManager)
    }

    @Test
    fun end() {
        visitor.end()
        Mockito.verify(activity)
            .finish()
    }

    @Test
    fun replaceFragment() {
        val fragmentTransaction = Mockito.mock(FragmentTransaction::class.java)
        whenever(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction)

        visitor.replaceFragment(MOCK_CONTAINER_ID, targetFragment)

        Mockito.verify(fragmentManager)
            .beginTransaction()
        Mockito.verify(fragmentTransaction)
            .replace(MOCK_CONTAINER_ID, targetFragment)
        Mockito.verify(fragmentTransaction)
            .commit()
    }

    @Test
    fun visitFragment() {
        val fragmentTransaction = Mockito.mock(FragmentTransaction::class.java)
        whenever(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction)

        visitor.visitFragment(MOCK_CONTAINER_ID, targetFragment)

        verify(fragmentManager).beginTransaction()
        verify(fragmentTransaction).add(MOCK_CONTAINER_ID, targetFragment)
        verify(fragmentTransaction).commit()
    }

    @Test
    fun visitGroup() {
        visitor.visitGroup(arrayOf(intent))
        verify(activity).startActivities(arrayOf(intent))
    }

    @Test
    fun visitForResult() {
        val reqCode = 1203
        visitor.visitForResult(reqCode, intent)
        verify(fragment).startActivityForResult(intent, reqCode)
    }

    @Test
    fun visitActivity() {
        visitor.visitActivity(intent)
        verify(fragment).startActivity(intent)
    }

    @Test
    fun visitService() {
        visitor.visitService(intent)
        verify(activity).startService(intent)
    }

    @Test
    fun dismiss() {
        val dialogFragmentVisitor = FragmentVisitor(dialogFragment)
        dialogFragmentVisitor.dismiss()
        verify(dialogFragment).dismissAllowingStateLoss()
    }

    companion object {
        private const val MOCK_CONTAINER_ID = 1234
    }
}