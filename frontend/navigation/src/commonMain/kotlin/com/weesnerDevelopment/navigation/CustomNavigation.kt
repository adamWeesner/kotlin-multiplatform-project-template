package com.weesnerDevelopment.navigation

import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.SlotNavigationSource
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.StackNavigationSource

/**
 * Custom navigation based on [StackNavigation] with the main thread limitations. This should only
 * be used for frontends that do not have a "main thread" i.e.: Terminal programs.
 */
internal class CustomStackNavigation<C : Any> : StackNavigation<C> {
    private val relay = Relay<StackNavigationSource.Event<C>>()

    override fun navigate(
        transformer: (stack: List<C>) -> List<C>,
        onComplete: (newStack: List<C>, oldStack: List<C>) -> Unit
    ) {
        relay.accept(StackNavigationSource.Event(transformer, onComplete))
    }

    override fun subscribe(observer: (StackNavigationSource.Event<C>) -> Unit) {
        relay.subscribe(observer)
    }

    override fun unsubscribe(observer: (StackNavigationSource.Event<C>) -> Unit) {
        relay.unsubscribe(observer)
    }
}

internal class CustomSlotNavigation<C: Any>: SlotNavigation<C> {
    private val relay = Relay<SlotNavigationSource.Event<C>>()

    override fun navigate(
        transformer: (configuration: C?) -> C?,
        onComplete: (newConfiguration: C?, oldConfiguration: C?) -> Unit,
    ) {
        relay.accept(SlotNavigationSource.Event(transformer, onComplete))
    }

    override fun subscribe(observer: (SlotNavigationSource.Event<C>) -> Unit) {
        relay.subscribe(observer)
    }

    override fun unsubscribe(observer: (SlotNavigationSource.Event<C>) -> Unit) {
        relay.unsubscribe(observer)
    }
}

private class Lock

private class Relay<T> {
    private val lock = Lock()
    private val queue = ArrayDeque<T>()
    private var isDraining = false
    private var observers = emptySet<(T) -> Unit>()

    fun accept(value: T) {
        synchronized(lock) {
            queue.addLast(value)

            if (isDraining) {
                return
            }

            isDraining = true
        }

        drainLoop()
    }

    private fun drainLoop() {
        while (true) {
            val value: T
            val observersCopy: Set<(T) -> Unit>

            synchronized(lock) {
                if (queue.isEmpty()) {
                    isDraining = false
                    return
                }

                value = queue.removeFirst()
                observersCopy = observers
            }

            observersCopy.forEach { observer ->
                observer(value)
            }
        }
    }

    fun subscribe(observer: (T) -> Unit) {
        synchronized(lock) { observers += observer }
    }

    fun unsubscribe(observer: (T) -> Unit) {
        synchronized(lock) { observers -= observer }
    }
}
