package com.jgeun.timebuddy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimerTimeView(
	timerStr: String
) {
	Box(
		modifier = Modifier.wrapContentSize()
			.background(color = Color(0xA6777777), shape = RoundedCornerShape(4.dp))
	) {
		Text(
			modifier = Modifier
				.padding(horizontal = 12.dp, vertical = 4.dp),
			text = timerStr,
			fontSize = 25.sp,
			color = Color.White,
			fontWeight = FontWeight(500)
		)
	}


}