package com.jgeun.timebuddy.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ClockStyle(
	val hourHandWidth: Dp = 5.dp, // 시침 두께
	val minuteHandWidth: Dp = 5.dp, // 분침 두께
	val secondHandWidth: Dp = 3.dp, // 초침 두께
	val minuteHandLength: Dp = 120.dp, // 분침 길이
	val secondHandLength: Dp = 120.dp, // 초침 길이
	val textColor: Color = Color.Black, // 1~12 텍스트 색상
	val textSize: Dp = 20.dp, // 1~12 텍스트 크기
	val hourGradationWidth: Dp = 4.dp, // 시간 눈금 두께
	val minuteGradationWidth: Dp = 2.dp, // 분 눈금 두께
	val hourGradationColor: Color = Color.Black, // 시간 눈금 색상
	val minuteGradationColor: Color = Color.Black, // 분 눈금 색상
	val hourGradationLength: Dp = 20.dp, // 시간 눈금 길이
	val minuteGradationLength: Dp = 10.dp, // 분 눈금 길이
	val hourGradationTextLength: Dp = 20.dp, // 시간 눈금 길이
	val minuteGradationTextLength: Dp = 10.dp, // 분 눈금 길이
)