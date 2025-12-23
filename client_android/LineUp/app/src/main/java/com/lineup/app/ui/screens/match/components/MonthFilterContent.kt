package com.lineup.app.ui.screens.match.components

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
fun MonthFilterContent(
    selectedMonth: Int,
    onMonthSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val months = (1..12).toList()
    val scrollState = rememberScrollState()
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    // 각 월의 위치와 너비를 저장
    val monthPositions = remember { mutableStateMapOf<Int, Pair<Float, Float>>() }

    // 이전 선택된 월을 추적
    var previousMonth by remember { mutableStateOf(selectedMonth) }
    val isMonthChanged = previousMonth != selectedMonth

    // 초기 로딩 시 8~12월이 선택되어 있으면 해당 위치로 스크롤
    LaunchedEffect(Unit) {
        if (selectedMonth in 8..12) {
            // 월 항목들이 배치될 때까지 대기
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
            // 월 목록 (스크롤 가능)
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                months.forEach { month ->
                    MonthItem(
                        month = month,
                        isSelected = selectedMonth == month,
                        onClick = { onMonthSelected(month) },
                        onPositioned = { xPosition, width ->
                            monthPositions[month] = Pair(xPosition, width)
                        }
                    )
                }
            }

            // 선택된 월의 인디케이터
            monthPositions[selectedMonth]?.let { (xPosition, width) ->
                // 스크롤 오프셋은 즉시 반영 (애니메이션 없음)
                val scrollOffset = scrollState.value.toFloat()

                // 월이 변경될 때만 애니메이션 적용
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

                // 최종 오프셋 = 애니메이션된 위치 - 스크롤 오프셋
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
private fun MonthItem(
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