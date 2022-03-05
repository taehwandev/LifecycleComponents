package io.androidalatan.compose.local.ksp

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class ComposeLocalProcessorProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return ComposeLocalProcessor(
            codeGenerator = environment.codeGenerator,
            logger = environment.logger,
            moduleName = environment.options["moduleName"] ?: "Unknown"
        )

    }

}