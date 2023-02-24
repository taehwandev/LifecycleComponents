package io.androidalatan.lifecycle.handler.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.JavaContext
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UMethod

@Suppress("UnstableApiUsage")
internal class LifecycleMethodReturnTypeDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        return listOf(UMethod::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return LifecycleAnnotationHandler(context)
    }
}

@Suppress("UnstableApiUsage")
internal class LifecycleAnnotationHandler(private val context: JavaContext) : UElementHandler() {
    override fun visitMethod(node: UMethod) {
        val hasLifecycleAnnotation = node.uAnnotations.any {
            it.qualifiedName?.let { it in LifecycleLintConsts.ANNOTATIONS } ?: false
        }

        if (!hasLifecycleAnnotation) return

        val correctReturnType = node.returnTypeReference?.getQualifiedName()
            ?.let { returnTypeName ->
                returnTypeName in LifecycleLintConsts.COROUTINE
            }
            ?: false

        if (!correctReturnType) {
            context.report(
                issue = LifecycleLintIssueRegistry.ISSUE_RETURN_TYPE,
                location = context.getLocation(node),
                message = "Async Annotated method should have one of these return type : Flow"
            )
        }

    }

}