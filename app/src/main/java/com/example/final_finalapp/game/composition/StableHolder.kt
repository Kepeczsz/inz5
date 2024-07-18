package com.example.final_finalapp.game.composition

import androidx.compose.runtime.Stable

@Stable
class StableHolder<T>(val item: T) {
    operator fun component1(): T = item
}