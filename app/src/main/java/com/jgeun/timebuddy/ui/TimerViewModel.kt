package com.jgeun.timebuddy.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TimerViewModel : ViewModel() {

	private val _myAngle = MutableStateFlow(0f)
	val myAngle = _myAngle.asStateFlow()
	private val minuteAngle = mutableListOf<Float>(360f)

	fun addMinuteAngle(angle: Float) {
		minuteAngle.add(angle)
	}

	fun setAngle(angle: Float) {
		val closestGreaterAngle = minuteAngle.filter { it > angle }.minOrNull()
		val closestSmallerAngle = minuteAngle.filter { it < angle }.maxOrNull()

		_myAngle.value = when {
			closestGreaterAngle != null && closestSmallerAngle != null -> {
				if ((closestGreaterAngle - angle) < (angle - closestSmallerAngle)) {
					closestGreaterAngle
				} else {
					closestSmallerAngle
				}
			}

			closestGreaterAngle != null -> closestGreaterAngle
			closestSmallerAngle != null -> closestSmallerAngle
			else -> 0f
		}
	}

	fun getTimerStr(angle: Float, type: TimeType): String {
		val unit = when (minuteAngle.indexOf(angle)) {
			-1 -> 0
			0 -> 60
			else -> minuteAngle.indexOf(angle) - 1
		}
		return "$unit:00".padStart(5, '0')
	}
}