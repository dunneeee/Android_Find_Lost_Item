package com.example.findlostitemapp.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findlostitemapp.ui.theme.FindLostItemAppTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> SliderShow(
    modifier: Modifier = Modifier,
    items: List<T>,
    delayTime: Long = 0L,
    isLoading: Boolean = false,
    content: @Composable (T, Int) -> Unit
) {
    val pageState =
        rememberPagerState(pageCount = { items.size })
    val isDragged by pageState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(pageState.currentPage) {
        if (delayTime > 0) {
            delay(delayTime)
            pageState.animateScrollToPage((pageState.currentPage + 1) % items.size)
        }
    }

    if(!isLoading) {
        Column(modifier.fillMaxWidth()) {
            HorizontalPager(state = pageState, verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.padding(horizontal = 8.dp)) {
                    content(items[pageState.currentPage], pageState.currentPage)
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(items.size) { index ->
                    Indicator(active = index == (if (isDragged) pageState.currentPage else pageState.targetPage))
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }else {
        Row(horizontalArrangement = Arrangement.Center, modifier = modifier.fillMaxWidth()) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun Indicator(modifier: Modifier = Modifier, active: Boolean) {
    val color =
        if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
            alpha = 0.12f
        )
    val size = if (active) 14.dp else 8.dp

    Box(
        modifier = modifier
            .size(size)
            .background(color = color, shape = CircleShape)
    )
}

@Preview
@Composable
private fun IndicatorPreview() {
    FindLostItemAppTheme {
        Surface {
            Indicator(active = true)
        }
    }
}