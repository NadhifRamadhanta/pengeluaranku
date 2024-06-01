package com.example.pengeluaranku.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pengeluaranku.database.Income
import com.example.pengeluaranku.ui.theme.PengeluarankuTheme
import com.example.pengeluaranku.viewModel.MainViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun IncomePage(navController: NavController, incomeList: List<Income>, viewModel: MainViewModel) {
    val months = arrayOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )

    val currentMonth = months.find { month ->
        LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM")).toString() == month
    }

    val (selectedMonth, setSelectedMonth) = remember { mutableStateOf(currentMonth) }
    Column {
        Header(
            Color(0xFF48D971), navController, months = months,
            selectedMonth = selectedMonth,
            onMonthSelected = setSelectedMonth
        )
        com.example.pengeluaranku.view.component.List(
            navController = navController,
            itemList = incomeList,
            selectedMonth = selectedMonth,
            viewModel = viewModel
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun IncomePagePreview() {
    PengeluarankuTheme {
        val navController = rememberNavController()
//        IncomePage(navController)
    }
}