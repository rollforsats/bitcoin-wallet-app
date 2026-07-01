package com.bitcoin.wallet.app.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

/**
 * Minimal MVI base for the app's ViewModels. The contract is deliberately
 * strict and uniform across every screen:
 *
 *  - one [state] `StateFlow` — the single source of truth the UI renders,
 *  - one [onAction] entry point — every user intent flows through here,
 *  - one [events] `Flow` — one-shot effects (navigation, snackbars) that must
 *    NOT replay on recomposition or configuration change.
 *
 * A buffered [Channel] (not a `SharedFlow`) backs events so each effect is
 * delivered exactly once to a single collector.
 */
abstract class MviViewModel<State, Action, Event>(initial: State) : ViewModel() {

    private val _state = MutableStateFlow(initial)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _events = Channel<Event>(Channel.BUFFERED)
    val events: Flow<Event> = _events.receiveAsFlow()

    abstract fun onAction(action: Action)

    protected fun setState(reducer: State.() -> State) = _state.update(reducer)

    protected suspend fun emitEvent(event: Event) = _events.send(event)
}
