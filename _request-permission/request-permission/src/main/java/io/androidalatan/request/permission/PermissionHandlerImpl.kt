package io.androidalatan.request.permission

import android.content.pm.PackageManager
import io.androidalatan.request.permission.api.PermissionExplanation
import io.androidalatan.request.permission.api.PermissionExplanationBuilder
import io.androidalatan.request.permission.api.PermissionExplanationBuilderFactory
import io.androidalatan.request.permission.api.PermissionHandler
import io.androidalatan.request.permission.api.PermissionResult
import io.androidalatan.request.permission.api.exception.PermissionGrantException

internal class PermissionHandlerImpl(
    private val permissionInvoker: PermissionInvoker,
    private val permissionExplanationBuilderFactory: PermissionExplanationBuilderFactory
) : PermissionHandler {

    private val failCallback = mutableMapOf<Int, (Exception) -> Unit>()
    private val successCallback = mutableMapOf<Int, (PermissionResult) -> Unit>()

    override fun request(
        permissions: Array<String>,
        reqCode: Int,
        explanation: ((PermissionExplanationBuilder) -> PermissionExplanation)?,
        fail: (Exception) -> Unit,
        success: (PermissionResult) -> Unit
    ) {
        val allGranted = permissions.all { permissionInvoker.checkPermission(it) }

        if (allGranted) {
            success(PermissionResult(reqCode, permissions.map { it to true }))
        } else {
            requestPermissions(permissions, reqCode, explanation, fail) {
                failCallback[reqCode] = fail
                successCallback[reqCode] = success
            }

        }
    }

    override fun onResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        val result = permissions.mapIndexed { index, permission ->
            permission to (grantResults[index] == PackageManager.PERMISSION_GRANTED)
        }
            .let {
                PermissionResult(requestCode, it)
            }
        val allGranted = result.result.all { it.second }

        if (allGranted) {
            successCallback.remove(requestCode)
                ?.invoke(result)
            failCallback.remove(requestCode)

        } else {
            val requiredExplanationList = result.result.filter { !permissionInvoker.shouldShowPermissionExplanation(it.first) }
                .map { it.first }
            if (requiredExplanationList.isNotEmpty()) {
                // assume deny forever
                failCallback.remove(requestCode)
                    ?.invoke(PermissionGrantException(requiredExplanationList, result))
                successCallback.remove(requestCode)
            } else {
                // once denied
                successCallback.remove(requestCode)
                    ?.invoke(result)
                failCallback.remove(requestCode)
            }
        }
    }

    private fun requestPermissions(
        permissions: Array<String>,
        reqCode: Int,
        explanation: ((PermissionExplanationBuilder) -> PermissionExplanation)?,
        fail: (Exception) -> Unit = {},
        waitTillAnswer: () -> Unit
    ) {

        fun requestPermission(permissionInvoker: PermissionInvoker, reqCode: Int) {
            permissionInvoker.requestPermission(permissions, reqCode)
        }

        val needExplain = permissions.any { permissionInvoker.shouldShowPermissionExplanation(it) }
        if (needExplain) {
            if (explanation != null) {
                val builder = permissionExplanationBuilderFactory.explanationBuilder()
                val permissionExplanation = explanation.invoke(builder)
                permissionExplanation.show {
                    requestPermission(permissionInvoker, reqCode)
                }
                waitTillAnswer()
            } else {
                fail(IllegalStateException("It requires Explanation. Please complete arugment of PermissionExplanationBuilderFactory"))
            }
        } else {
            requestPermission(permissionInvoker, reqCode)
            waitTillAnswer()
        }
    }
}