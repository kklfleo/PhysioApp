package com.example.physioapp.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.physioapp.R

data class Exercise (
    @StringRes val name: Int,
    val hold: Int = 0,
    val set: Int = 1,
    val reps: Int = 10,
    val state: Boolean = false,
    val setsDone: List<Boolean> = List(set) { false }
)

val plan = listOf(
    Exercise(R.string.exercise1, 0, 3),
    Exercise(R.string.exercise2, 5, 3),
    Exercise(R.string.exercise3, 5, 3),
    Exercise(R.string.exercise4, 5, 3),
    Exercise(R.string.exercise5, 5, 3),
    Exercise(R.string.exercise6, 0, 3),
    Exercise(R.string.exercise7, 5, 3),
)