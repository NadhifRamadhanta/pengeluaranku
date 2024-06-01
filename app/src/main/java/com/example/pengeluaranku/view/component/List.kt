package com.example.pengeluaranku.view.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pengeluaranku.R
import com.example.pengeluaranku.database.Expense
import com.example.pengeluaranku.database.Income
import com.example.pengeluaranku.ui.theme.PengeluarankuTheme
import com.example.pengeluaranku.viewModel.MainViewModel
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun List(
    navController: NavController,
    itemList: List<Any>,
    selectedMonth: String?,
    viewModel: MainViewModel
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

    val filteredList = if (selectedMonth != null) {
        itemList.filter { item ->
            val itemDate = when (item) {
                is Expense -> LocalDate.parse(
                    item.date,
                    DateTimeFormatter.ofPattern("dd/MMMM/yyyy")
                )

                is Income -> LocalDate.parse(item.date, DateTimeFormatter.ofPattern("dd/MMMM/yyyy"))
                else -> null
            }
            itemDate?.let {
                val itemMonth = it.month
                itemMonth.getDisplayName(TextStyle.FULL, Locale.ENGLISH) == selectedMonth
            } ?: false
//            val expenseMonth =
//                LocalDate.parse(expense.date, DateTimeFormatter.ofPattern("dd/MMMM/yyyy")).month
//            expenseMonth.getDisplayName(TextStyle.FULL, Locale.ENGLISH) == selectedMonth
        }
    } else {
        itemList
    }


    if (filteredList.isNotEmpty()) {
        LazyColumn(modifier = Modifier.padding(bottom = 55.dp)) {
//            val dateDistinct = filteredList.map { it.date }.distinct().sortedDescending()
            val dateDistinct = filteredList.mapNotNull { item ->
                when (item) {
                    is Expense -> item.date
                    is Income -> item.date
                    else -> null
                }
            }.distinct().sortedDescending()

            val todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MMMM/yyyy"))
            val yesterdayDate =
                LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MMMM/yyyy"))

            dateDistinct.forEach { date ->
                item {
                    HeaderItem(item = if (date == todayDate) "Today" else if (date == yesterdayDate) "Yesterday" else date) // Display header item with the first date from the list
                }

                val itemByDate = itemList.filter { item ->
//                    it.date == date
                    when (item) {
                        is Expense -> item.date == date
                        is Income -> item.date == date
                        else -> false
                    }
                }

                items(items = itemByDate) { item ->
                    if (item is Expense) {
                        ListItemExpense(
                            item = item,
                            deleteAction = { viewModel.deleteExpense(item.id) },
                            editAction = { navController.navigate("EditExpense/${item.id}") })
                    } else if (item is Income) {
                        ListItemIncome(
                            item = item,
                            deleteAction = { viewModel.deleteIncome(item.id) },
                            editAction = { navController.navigate("EditIncome/${item.id}") })
                    }
                    Divider(
                        color = Color.LightGray,
                        thickness = 2.dp,
                        modifier = Modifier.padding(5.dp, 3.dp)
                    )

                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                text = "Data is Empty, Please Add Data",
                textAlign = TextAlign.Center,
                letterSpacing = 2.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun HeaderItem(item: String) {
    Text(
        text = item,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 16.dp, 10.dp, 16.dp),
        fontWeight = FontWeight.Light,
        fontSize = 24.sp,
        letterSpacing = 1.sp
    )
}

@Composable
fun ListItemExpense(item: Expense, deleteAction: () -> Unit, editAction: () -> Unit) {
    val showDeleteDialog = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = item.notes, fontSize = 20.sp, letterSpacing = 2.sp)
            Text(
                text = item.type,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                letterSpacing = 1.5.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val expenseAmount = item.expense.toBigDecimal().stripTrailingZeros()
            val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            currencyFormat.maximumFractionDigits = 0

            Text(
                modifier = Modifier.weight(1f),
                text = currencyFormat.format(expenseAmount),
                textAlign = TextAlign.End,
                fontSize = 20.sp,
                letterSpacing = 1.5.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(4.dp))
            Button(
                modifier = Modifier.size(35.dp),
                onClick = editAction,
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF48D971)),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_edit_24),
                    tint = Color.White,
                    contentDescription = "Edit Button"
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Button(
                modifier = Modifier.size(35.dp),
                onClick = {
                    showDeleteDialog.value = true
                },
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFEF5A75)),
                contentPadding = PaddingValues(0.dp)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    tint = Color.White,
                    contentDescription = "Delete Button"
                )
            }
            if (showDeleteDialog.value) {
                DeleteDialog(onDismiss = { showDeleteDialog.value = false }, onDelete = {
                    deleteAction()
                    showDeleteDialog.value = true
                })
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListItemIncome(item: Income, deleteAction: () -> Unit, editAction: () -> Unit) {
    val showDeleteDialog = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = item.notes, fontSize = 20.sp, letterSpacing = 2.sp)
            Text(
                text = item.date,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                letterSpacing = 1.5.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val incomeAmount = item.income.toBigDecimal().stripTrailingZeros()
            val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            currencyFormat.maximumFractionDigits = 0

            Text(
                modifier = Modifier.weight(1f),
                text = currencyFormat.format(incomeAmount),
                textAlign = TextAlign.End,
                fontSize = 20.sp,
                letterSpacing = 2.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(4.dp))
            Button(
                modifier = Modifier.size(35.dp),
                onClick = editAction,
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF48D971)),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_edit_24),
                    tint = Color.White,
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Button(
                modifier = Modifier.size(35.dp),
                onClick = { showDeleteDialog.value = true },
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFEF5A75)),
                contentPadding = PaddingValues(0.dp)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    tint = Color.White,
                    contentDescription = ""
                )
            }
            if (showDeleteDialog.value) {
                DeleteDialog(onDismiss = { showDeleteDialog.value = false }, onDelete = {
                    deleteAction()
                    showDeleteDialog.value = true
                })
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun DashboardPreview() {
    PengeluarankuTheme {
        val expenseList: List<Expense> = arrayListOf(
            Expense(expense = 20, type = "test", date = "04", notes = "testets"),
            Expense(expense = 20, type = "test", date = "04", notes = "testets"),
            Expense(expense = 20, type = "test", date = "04", notes = "testets")
        )
//        List(navController = ,expenseList, "June", MainViewModel(Application()))
    }
}
