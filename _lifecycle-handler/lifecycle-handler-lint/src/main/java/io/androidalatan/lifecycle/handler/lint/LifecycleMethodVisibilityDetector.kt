package io.androidalatan.lifecycle.handler.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.JavaContext
import com.intellij.psi.PsiModifier
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UMethod

@Suppress("UnstableApiUsage")
internal class LifecycleMethodVisibilityDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        return listOf(UMethod::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return LifecycleMethodVisibilityHandler(context)
    }
}

@Suppress("UnstableApiUsage")
internal class LifecycleMethodVisibilityHandler(private val context: JavaContext) : UElementHandler() {
    override fun visitMethod(node: UMethod) {
        node.uAnnotations.firstOrNull {
            it.qualifiedName?.let {
                it in LifecycleLintConsts.ANNOTATIONS
            } ?: false
        }
            ?.let {
                if (!node.hasModifierProperty(PsiModifier.PUBLIC)) {
                    context.report(
                        issue = LifecycleLintIssueRegistry.ISSUE_VISIBILITY,
                        location = context.getLocation(node),
                        message = "We recommend Async Lifecycle method is public or internal"
                    )
                }
            }
    }
}