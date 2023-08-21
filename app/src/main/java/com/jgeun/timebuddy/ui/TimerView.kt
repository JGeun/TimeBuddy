package com.jgeun.timebuddy.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jgeun.timebuddy.R
import com.jgeun.timebuddy.model.ClockStyle


@Composable
fun TimerView(
	clockStyle: ClockStyle = ClockStyle(),
	viewModel: TimerViewModel = viewModel()
) {

	val context = LocalContext.current
	val myAngle by viewModel.myAngle.collectAsStateWithLifecycle()
	val isTimeTextShow by viewModel.isTimeTextShow.collectAsStateWithLifecycle()
	val isTimerActive by viewModel.isTimerActive.collectAsStateWithLifecycle()
	val isTimerPause by viewModel.isTimerPause.collectAsStateWithLifecycle()

	Box(
		modifier = Modifier
			.fillMaxSize()
			.padding(vertical = 60.dp),
		contentAlignment = Alignment.Center,
	) {

		ClockCanvas(
			modifier = Modifier
				.padding(40.dp)
				.fillMaxSize(),
			clockStyle = clockStyle,
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

		if (!isTimerActive) {
			AnimatedVisibility(
				visible = isTimeTextShow,
				enter = EnterTransition.None,
				exit = fadeOut()
			) {
				TimerTimeView(
					viewModel.getTimerStr(TimeType.MIN)
				)
			}
		}

		if (!isTimerActive) {
			Button(
				modifier = Modifier.align(Alignment.BottomCenter),
				onClick = {
					if (myAngle == 0f) {
						Toast.makeText(context, R.string.user_no_time_message, Toast.LENGTH_SHORT).show()
					} else {
						viewModel.startTimer()
					}
				}
			) {
				Text(text = stringResource(id = R.string.timer_view_start_btn_text))
			}
		} else {
			Row(
				modifier = Modifier.align(Alignment.BottomCenter),
			) {
				Button(
					onClick = {
						viewModel.cancelTimer()
					}
				) {
					Text(text = stringResource(id = R.string.timer_view_cancel_btn_text))
				}

				Spacer(modifier = Modifier.width(30.dp))

				Button(
					onClick = {
						viewModel.pauseTimer()
					}
				) {
					Text(text = if(isTimerPause)
						stringResource(id = R.string.timer_view_resume_btn_text)
					else
						stringResource(id = R.string.timer_view_pause_btn_text))
				}
			}
		}
	}
}

@Preview
@Composable
private fun ClockPreview() {
	Surface(color = Color.White) {
		TimerView()
	}

}