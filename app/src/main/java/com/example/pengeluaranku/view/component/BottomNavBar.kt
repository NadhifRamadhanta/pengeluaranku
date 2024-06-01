package com.example.pengeluaranku.view.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pengeluaranku.R
import com.example.pengeluaranku.database.Expense
import com.example.pengeluaranku.database.Income
import com.example.pengeluaranku.view.AboutPage
import com.example.pengeluaranku.view.Dashboard
import com.example.pengeluaranku.view.EditExpense
import com.example.pengeluaranku.view.EditIncome
import com.example.pengeluaranku.view.ExpensePage
import com.example.pengeluaranku.view.IncomePage
import com.example.pengeluaranku.view.InputExpense
import com.example.pengeluaranku.view.InputIncome
import com.example.pengeluaranku.viewModel.MainViewModel

sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String) {
    object Dashboard : BottomNavItem("Dashboard", R.drawable.outline_home_24, "dashboard")
    object Expense : BottomNavItem("Expense", R.drawable.expense, "expense")
    object Income : BottomNavItem("Income", R.drawable.income, "income")
    object About : BottomNavItem("About", R.drawable.outline_info_24, "about")
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    expenseList: List<Expense>,
    incomeList: List<Income>,
    viewModel: MainViewModel
) {
    NavHost(navController, startDestination = BottomNavItem.Dashboard.screen_route) {
        composable(BottomNavItem.Dashboard.screen_route) {
            Dashboard(
                expenseList = expenseList,
                incomeList = incomeList,
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(BottomNavItem.Expense.screen_route) {
            ExpensePage(navController, expenseList = expenseList, viewModel = viewModel)
        }
        composable(BottomNavItem.Income.screen_route) {
            IncomePage(navController, incomeList = incomeList, viewModel = viewModel)
        }
        composable(BottomNavItem.About.screen_route) {
            AboutPage()
        }
        composable("inputExpense") {
            InputExpense(viewModel = viewModel, navController = navController)
        }
        composable("inputIncome") {
            InputIncome(viewModel = viewModel,navController = navController)
        }
        composable("editExpense/{expenseId}") { backStackEntry ->
            val expenseId = backStackEntry.arguments?.getString("expenseId")
            val dataExpense = expenseList.find { it.id == expenseId?.toInt() }
            EditExpense(viewModel = viewModel, expense = dataExpense!!, navController = navController)
        }
        composable("editIncome/{incomeId}") { backStackEntry ->
            val incomeId = backStackEntry.arguments?.getString("incomeId")
            val dataExpense = incomeList.find { it.id == incomeId?.toInt() }
            EditIncome(
                viewModel = viewModel,
                income = dataExpense!!,
                navController = navController
            )
        }

    }
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Expense,
        BottomNavItem.Income,
        BottomNavItem.About
    )
    androidx.compose.material.BottomNavigation(
        backgroundColor = Color(0xFF758BFD),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                selected = currentRoute == item.screen_route,
                label = {
                    Text(
                        text = item.title,
                        fontSize = 12.sp
                    )
                },
                selectedContentColor = Color(0xFFAEB8FE),
                unselectedContentColor = Color.White,
                alwaysShowLabel = true,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}