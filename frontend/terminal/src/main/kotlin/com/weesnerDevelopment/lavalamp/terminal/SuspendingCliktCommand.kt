package com.weesnerDevelopment.lavalamp.terminal

import com.github.ajalt.clikt.core.CliktCommand
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking

abstract class SuspendingCliktCommand(
    private val dispatcher: CoroutineDispatcher = onlyDispatcher
) : CliktCommand() {
    /**
     * use `suspendRun` to get coroutine scope handling for free
     */
    override fun run() {
        runBlocking(dispatcher) {
            suspendRun()
        }
    }

    abstract suspend fun suspendRun()
}
