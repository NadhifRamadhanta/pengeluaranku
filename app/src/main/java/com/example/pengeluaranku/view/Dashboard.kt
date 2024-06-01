package com.example.pengeluaranku.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pengeluaranku.R
import com.example.pengeluaranku.database.Expense
import com.example.pengeluaranku.database.Income
import com.example.pengeluaranku.ui.theme.PengeluarankuTheme
import com.example.pengeluaranku.view.component.Tabs
import com.example.pengeluaranku.viewModel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalPagerApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Dashboard(navController: NavController,expenseList: List<Expense>,incomeList: List<Income>, viewModel: MainViewModel) {

    val allExpense : Int = expenseList.sumOf { it.expense }
    val allIncome : Int = incomeList.sumOf { it.income }

    val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    currencyFormat.maximumFractionDigits = 0



    val pagerState = rememberPagerState(pageCount = 2)

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        ExpenseInfo(expense = "${currencyFormat.format(allExpense)}", income = "${currencyFormat.format(allIncome)}", fontColor = Color.White)
        Tabs(pagerState = pagerState)
        TabsContent(navController = navController,pagerState = pagerState, expenseList = expenseList, incomeList = incomeList, viewModel = viewModel)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseInfo(expense: String, income: String, fontColor: Color) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)) {
        Image(
            painter = painterResource(id = R.drawable.bg_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp))
        )
        Column(
            modifier = Modifier
                .height(300.dp)
                .clip(RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp))
                .background(Color.Black.copy(alpha = 0.2f))
                .fillMaxWidth()
                .padding(10.dp, 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.size(20.dp))
            Column(modifier = Modifier.align(alignment = Alignment.Start)) {
                Text("Income", color = fontColor, letterSpacing = 2.sp)
                Text(
                    text = income,
                    color = fontColor,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = expense,
                    fontSize = 36.sp,
                    color = fontColor,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )
                Text("Expense", color = fontColor, letterSpacing = 2.sp)
            }
            Text(
                modifier = Modifier.align(alignment = Alignment.End),
                text = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy")).toString(),
                color = fontColor,
                letterSpacing = 2.sp
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(navController: NavController,pagerState: PagerState, expenseList:List<Expense>, incomeList: List<Income>, viewModel: MainViewModel) {
    val currentMonth =
        LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM")).toString()

    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> Box(modifier = Modifier.fillMaxSize()) {
                com.example.pengeluaranku.view.component.List(navController = navController,expenseList, selectedMonth = currentMonth, viewModel = viewModel)
            }

            1 -> Box(modifier = Modifier.fillMaxSize()) {
                com.example.pengeluaranku.view.component.List(navController = navController,incomeList, selectedMonth = currentMonth,viewModel = viewModel)
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun DashboardPreview() {
    PengeluarankuTheme {
//        Dashboard()
    }
}