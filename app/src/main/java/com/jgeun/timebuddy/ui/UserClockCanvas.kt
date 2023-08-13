package com.jgeun.timebuddy.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.atan2

@Composable
fun UserClockCanvas(
	modifier: Modifier = Modifier,
	angle: Float,
	setAngle: (Float) -> Unit
) {

	val startAngle by remember { mutableStateOf(-90f) }

	var topLeftX by remember { mutableStateOf(0f) }
	var topLeftY by remember { mutableStateOf(0f) }

	var topMidX by remember { mutableStateOf(0f) }
	var topMidY by remember { mutableStateOf(0f) }

	val offsetX by remember { mutableStateOf(0f) }
	val offsetY by remember { mutableStateOf(0f) }

	var arcSize by remember { mutableStateOf(0f) }
	var maxWidth by remember { mutableStateOf(0f) }
	var maxHeight by remember { mutableStateOf(0f) }

	Canvas(
		modifier = modifier
			.pointerInput(Unit) {
				detectTapGestures {
					val sweepAngle = calculateAngle(maxWidth / 2, maxHeight / 2, topMidX, topMidY, it.x, it.y)
					setAngle(sweepAngle)
				}
			}
			.pointerInput(Unit) {
				detectDragGestures { change, _ ->
					val screenX = change.position.x + offsetX
					val screenY = change.position.y + offsetY

					val sweepAngle = calculateAngle(maxWidth / 2, maxHeight / 2, topMidX, topMidY, screenX, screenY)
					setAngle(sweepAngle)

				}
			}
	) {
		maxWidth = size.width
		maxHeight = size.height
		arcSize = maxWidth.coerceAtMost(maxHeight)
		topLeftX = (maxWidth / 2).minus(arcSize / 2)
		topLeftY = (maxHeight / 2).minus(arcSize / 2)
		topMidX = (maxWidth / 2)
		topMidY = (maxHeight / 2).minus(arcSize / 2)

		drawArc(
			color = Color.Red,
			startAngle = startAngle,
			sweepAngle = angle,
			topLeft = Offset(topLeftX, topLeftY),
			size = Size(arcSize, arcSize),
			useCenter = true,
			style = Fill,
			alpha = 1f,
		)
	}
}

private fun calculateAngle(centerX: Float, centerY: Float, x1: Float, y1: Float, x2: Float, y2: Float): Float {
	val deltaY1 = centerY - y1
	val deltaX1 = centerX - x1
	val deltaY2 = centerY - y2
	val deltaX2 = centerX - x2

	val angle1 = atan2(deltaY1.toDouble(), deltaX1.toDouble())
	val angle2 = atan2(deltaY2.toDouble(), deltaX2.toDouble())

	var angle = Math.toDegrees(angle2 - angle1).toFloat()
	if (angle < 0) {
		angle += 361f
	}

	return angle
}
