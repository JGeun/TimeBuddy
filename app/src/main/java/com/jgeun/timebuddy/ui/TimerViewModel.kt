package com.jgeun.timebuddy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {

	private var myTimerJob: Job? = null

	private val _myAngle = MutableStateFlow(0f)
	val myAngle = _myAngle.asStateFlow()

	private val _isTimeTextShow = MutableStateFlow(false)
	val isTimeTextShow = _isTimeTextShow.asStateFlow()

	private val timerAngleList = mutableListOf<Float>()

	private var timerShowJob: Job? = null

	private val _isTimerActive = MutableStateFlow(false)
	val isTimerActive = _isTimerActive.asStateFlow()

	private val _isTimerPause = MutableStateFlow(false)
	val isTimerPause = _isTimerPause.asStateFlow()

	init {
		initTimerAngle()
	}

	private fun initTimerAngle() {
		val timerAngleCount = 120

		repeat(timerAngleCount) { index ->
			val timerAngleInDegree = (index.toFloat() * 360 / timerAngleCount)
			timerAngleList.add(timerAngleInDegree)
		}
		timerAngleList.add(360f)
	}

	fun setAngle(angle: Float) {
		if (isTimerActive.value) return

		val closestGreaterAngle = timerAngleList.filter { it > angle }.minOrNull()
		val closestSmallerAngle = timerAngleList.filter { it < angle }.maxOrNull()

		setTimerShowJob()

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
		getIndexOfAngle(_myAngle.value)
	}

	private fun setTimerShowJob() {
		timerShowJob?.cancel()

		_isTimeTextShow.value = true
		timerShowJob = viewModelScope.launch {
			delay(1000L)
			_isTimeTextShow.value = false
		}
	}

	fun getTimerStr(type: TimeType): String {
		val angle = myAngle.value
		return getTimeWithAngle(angle)
	}

	private fun getTimeWithAngle(angle: Float): String {
		val time = angle/3*30 // 3도: 30초 -> 30도 300초
		val first = time.toInt()/60
		val second = time.toInt() % 60

		val firstStr = first.toString().padStart(2, '0')
		val secondStr = second.toString().padStart(2, '0')
		return "$firstStr:$secondStr"
	}

	private fun getIndexOfAngle(angle: Float): Int {
		return timerAngleList.indexOf(angle)
	}

	fun startTimer() {
		_isTimerActive.value = true

		if (myAngle.value == 0f) return

		val timerIndex = getIndexOfAngle(myAngle.value)
		if (timerIndex == -1) return

		val timeForAngle = if(timerIndex == 0) 0f else myAngle.value/(timerIndex*60)

		myTimerJob = viewModelScope.launch {
			while (myAngle.value >= 0) {
				delay(1000L)
				if (isTimerPause.value) continue
				_myAngle.value -= timeForAngle
			}
			_isTimerActive.value = false
			_myAngle.value = 0f
		}
		myTimerJob?.start()
	}

	fun cancelTimer() {
		myTimerJob?.cancel()
		_myAngle.value = 0f
		_isTimerActive.value = false
		_isTimerPause.value = false
	}

	fun pauseTimer() {
		_isTimerPause.value = !_isTimerPause.value
	}
}