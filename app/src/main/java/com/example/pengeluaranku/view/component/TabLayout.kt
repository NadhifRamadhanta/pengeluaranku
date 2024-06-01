package com.example.pengeluaranku.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf(
        "Expenses",
        "Income"
    )

    val scope = rememberCoroutineScope()


    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color(0xFF758BFD),
        contentColor = Color.Black,
        indicator = { _: List<TabPosition> ->
           Box{}
        },
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .padding(1.dp, 15.dp)
            .width(280.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(30))

    ) {
        list.forEachIndexed { index, text ->
            Tab(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(30))
                    .background(
                        if (pagerState.currentPage == index) Color.White else Color(0xFF758BFD)
                    ),
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = { Text(text = text, color = Color(0xff6FAAEE)) }
            )
        }

    }
}

