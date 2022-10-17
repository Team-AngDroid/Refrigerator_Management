package com.angdroid.refrigerator_manament.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

inline fun <T, R> R.collectFlow(
    flow: Flow<T>, crossinline block: suspend (T) -> Unit
) {
    when (this) {
        is AppCompatActivity -> {
            flow.flowWithLifecycle(lifecycle).onEach { block(it) }.launchIn(lifecycleScope)
        }
        is Fragment -> {
            flow.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { block(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
        else -> {}
    }
}

inline fun <T, R> R.collectFlowWhenStarted(
    flow: Flow<T>, crossinline block: suspend (T) -> Unit
) = collectFlow(flow, block)