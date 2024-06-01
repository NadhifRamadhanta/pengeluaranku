package com.example.pengeluaranku.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pengeluaranku.database.Expense
import com.example.pengeluaranku.ui.theme.PengeluarankuTheme
import com.example.pengeluaranku.view.component.DropdownMenu
import com.example.pengeluaranku.viewModel.MainViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpensePage(
    navController: NavController, expenseList: List<Expense>, viewModel: MainViewModel
) {
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
            Color(0xFFEF5A75),
            navController,
            months = months,
            selectedMonth = selectedMonth,
            onMonthSelected = setSelectedMonth
        )
        com.example.pengeluaranku.view.component.List(navController = navController,expenseList, selectedMonth = selectedMonth, viewModel = viewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Header(
    color: Color,
    navController: NavController,
    months: Array<String>,
    selectedMonth: String?,
    onMonthSelected: (String) -> Unit
) {
    var currentRoute by remember { mutableStateOf("") }

    DisposableEffect(navController) {
        val observer = NavController.OnDestinationChangedListener { _, destination, _ ->
            currentRoute = destination.route?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            } ?: ""
        }
        navController.addOnDestinationChangedListener(observer)

        onDispose {
            navController.removeOnDestinationChangedListener(observer)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 80.dp, 10.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DropdownMenu(
            months = months,
            selectedMonth = selectedMonth!!,
            onChangedText = onMonthSelected
        )
        Button(
            onClick = {
                if (currentRoute == "Expense") {
                    navController.navigate("inputExpense")
                } else navController.navigate("inputIncome")
            },
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .size(85.dp, 35.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = color, contentColor = Color.White
            )
        ) {
            Row {
                Text(text = "Add", fontSize = 14.sp, letterSpacing = 2.sp)
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun ExpensePagePreview() {
    PengeluarankuTheme {
        val navController = rememberNavController()
//        ExpensePage(navController)
    }
}