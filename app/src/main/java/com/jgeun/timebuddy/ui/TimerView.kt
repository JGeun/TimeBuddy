package com.jgeun.timebuddy.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jgeun.timebuddy.model.ClockStyle


@Composable
fun TimerView(
	clockStyle: ClockStyle = ClockStyle(),
	viewModel: TimerViewModel = viewModel()
) {

	val myAngle by viewModel.myAngle.collectAsStateWithLifecycle()

	Box(
		modifier = Modifier
			.fillMaxSize(),
		contentAlignment = Alignment.Center,
	) {

		ClockCanvas(
			modifier = Modifier
				.padding(40.dp)
				.fillMaxSize(),
			clockStyle = clockStyle,
			addAngle = { angle ->
				viewModel.addMinuteAngle(angle)
			}
		)

		UserClockCanvas(
			modifier = Modifier
				.padding(50.dp)
				.fillMaxSize(),
			angle = myAngle,
			setAngle = { angle ->
				viewModel.setAngle(angle)
			}
		)

		TimerTimeView(
			viewModel.getTimerStr(myAngle, TimeType.MIN)
		)
	}
}

@Preview
@Composable
private fun ClockPreview() {
	Surface(color = Color.White) {
		TimerView()
	}

}