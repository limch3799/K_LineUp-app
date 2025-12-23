package com.lineup.app.ui.screens.mypick.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun MyPickMonthFilter(
    selectedMonth: Int,
    onMonthSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val months = (1..12).toList()
    val scrollState = rememberScrollState()
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    val monthPositions = remember { mutableStateMapOf<Int, Pair<Float, Float>>() }

    var previousMonth by remember { mutableStateOf(selectedMonth) }
    val isMonthChanged = previousMonth != selectedMonth

    LaunchedEffect(Unit) {
        if (selectedMonth in 8..12) {
            kotlinx.coroutines.delay(50)
            monthPositions[selectedMonth]?.let { (xPosition, _) ->
                coroutineScope.launch {
                    scrollState.scrollTo(xPosition.toInt())
                }
            }
        }
    }

    LaunchedEffect(selectedMonth) {
        if (isMonthChanged) {
            previousMonth = selectedMonth
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                months.forEach { month ->
                    MyPickMonthItem(
                        month = month,
                        isSelected = selectedMonth == month,
                        onClick = { onMonthSelected(month) },
                        onPositioned = { xPosition, width ->
                            monthPositions[month] = Pair(xPosition, width)
                        }
                    )
                }
            }

            monthPositions[selectedMonth]?.let { (xPosition, width) ->
                val scrollOffset = scrollState.value.toFloat()

                val animatedXPosition by animateFloatAsState(
                    targetValue = xPosition,
                    animationSpec = if (isMonthChanged) {
                        tween(durationMillis = 300)
                    } else {
                        tween(durationMillis = 0)
                    },
                    label = "month_indicator_position"
                )

                val animatedWidth by animateFloatAsState(
                    targetValue = width,
                    animationSpec = tween(durationMillis = 300),
                    label = "month_indicator_width"
                )

                val finalOffset = animatedXPosition - scrollOffset + with(density) { 16.dp.toPx() }

                Box(
                    modifier = Modifier
                        .offset(x = with(density) { finalOffset.toDp() })
                        .width(with(density) { animatedWidth.toDp() })
                        .height(4.dp)
                        .background(
                            color = Color(0xFF222222),
                            shape = RoundedCornerShape(2.dp)
                        )
                        .align(Alignment.BottomStart)
                )
            }
        }
    }
}

@Composable
private fun MyPickMonthItem(
    month: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    onPositioned: (xPosition: Float, width: Float) -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
            .padding(top = 8.dp, bottom = 4.dp)
            .onGloballyPositioned { coordinates ->
                val position = coordinates.positionInParent()
                onPositioned(position.x, coordinates.size.width.toFloat())
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${month}월",
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color(0xFF222222) else Color(0xFF999999)
        )
    }
}