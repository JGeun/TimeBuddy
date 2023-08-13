package com.jgeun.timebuddy.ui

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import com.jgeun.timebuddy.model.ClockStyle
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ClockCanvas(
	modifier: Modifier,
	clockStyle: ClockStyle,
	addAngle: (Float) -> Unit,
) {

	Canvas(
		modifier = modifier
	) {
		val radius: Float = size.minDimension / 2.0f // 원의 반지름

		// 시계 눈금 그리기
		val gradationCount = 60 // 시계눈금 갯수
		repeat(gradationCount) { index ->
			val angleInDegree = (index * 360 / gradationCount).toDouble()
			val angleInRadian = Math.toRadians(angleInDegree)
			addAngle(angleInDegree.toFloat())

			val isHourGradation = index % 5 == 0
			val length = if (isHourGradation) {
				clockStyle.hourGradationLength.toPx()
			} else {
				clockStyle.minuteGradationLength.toPx()
			}

			val start = Offset(
				x = (center.x + (radius - length) * cos(angleInRadian)).toFloat(),
				y = (center.y + (radius - length) * sin(angleInRadian)).toFloat()
			)
			val end = Offset(
				x = (center.x + radius * cos(angleInRadian)).toFloat(),
				y = (center.y + radius * sin(angleInRadian)).toFloat()
			)
			val gradationColor: Color = if (isHourGradation) {
				clockStyle.hourGradationColor
			} else {
				clockStyle.minuteGradationColor
			}

			val gradationWidth: Dp = if (isHourGradation) {
				clockStyle.hourGradationWidth
			} else {
				clockStyle.minuteGradationWidth
			}

			drawLine(
				color = gradationColor,
				start = start,
				end = end,
				strokeWidth = gradationWidth.toPx()
			)

			drawContext.canvas.nativeCanvas.apply {
				// 1~12시 텍스트 그리기
				if (index % 5 == 0) {
					val hourText = (index / 5 + 5 * 3) % 12 * 5

					val textSize = clockStyle.textSize.toPx()
					val textRadius = radius + textSize
					val x = textRadius * cos(angleInRadian) + center.x
					val y = textRadius * sin(angleInRadian) + center.y + textSize / 2f

					drawText(
						"$hourText",
						x.toFloat(),
						y.toFloat(),
						Paint().apply {
							this.color = clockStyle.textColor.toArgb()
							this.textSize = textSize
							this.textAlign = Paint.Align.CENTER
						}
					)
				}
			}
		}
	}
}