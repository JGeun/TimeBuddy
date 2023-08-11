package com.jgeun.timebuddy.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TimerView() {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center,
	) {

		var arcSize by remember { mutableStateOf(0f) }
		var maxWidth by remember { mutableStateOf(0f) }
		var maxHeight by remember { mutableStateOf(0f) }

		var topLeftX by remember { mutableStateOf(0f) }
		var topLeftY by remember { mutableStateOf(0f) }

		val startAngle by remember { mutableStateOf(-90f) }
		var sweepAngle by remember { mutableStateOf(0f) }

		Canvas(
			modifier = Modifier
				.fillMaxSize()
				.padding(20.dp)
				.pointerInput(Unit) {
					detectDragGestures { _, dragAmount ->
						sweepAngle += (dragAmount.x / 10)
					}
				}
				.onGloballyPositioned { layoutCoordinates ->
					maxWidth = layoutCoordinates.size.width.toFloat()
					maxHeight = layoutCoordinates.size.height.toFloat()
					arcSize = maxWidth.coerceAtMost(maxHeight)
					topLeftX = (maxWidth/2).minus(arcSize/2)
					topLeftY = (maxHeight/2).minus(arcSize/2)
				},
		) {

			drawArc(
				color = Color.Green,
				startAngle = startAngle,
				sweepAngle = sweepAngle,
				topLeft = Offset(topLeftX, topLeftY),
				size = Size(arcSize, arcSize),
				useCenter = true,
				style = Fill,
				alpha = 1f
			)
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