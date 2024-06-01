package com.example.pengeluaranku.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pengeluaranku.database.Expense
import com.example.pengeluaranku.view.component.DropdownInput
import com.example.pengeluaranku.view.component.LoadingDialog
import com.example.pengeluaranku.view.component.ResultDialog
import com.example.pengeluaranku.viewModel.MainViewModel
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditExpense(viewModel:MainViewModel, expense: Expense = Expense(), navController:NavController) {

    var textNominal by remember { mutableStateOf("${expense.expense}") }

    val date = SimpleDateFormat("dd/MMMM/yyyy").parse(expense.date)

    val selectedDate = remember { mutableStateOf(date) }

    var textNote by remember { mutableStateOf(expense.notes) }

    val types = arrayOf(
        "Food and Beverages",
        "Entertaiment",
        "Clothing",
        "Others"
    )

    var selectedDropdownText by remember { mutableStateOf(expense.type) }

    val loadingDialog = remember { mutableStateOf(false) }
    val resultDialog = remember { mutableStateOf(false) }

    if (loadingDialog.value) {
        LoadingDialog(onDismiss = {},loadingWord ="Updating")
    }

    if(resultDialog.value){
        ResultDialog(onDismiss = {navController.popBackStack()}, resultString = "Success", actionString = "Update")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF758BFD))
            .padding(start = 25.dp, end = 25.dp, top = 90.dp, bottom = 25.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            ButtonUseReceipt()
        }

        NominalInput(
            "Expense",
            Color.White,
            text = textNominal,
            onChangedText = { if (it.length <= 11) textNominal = it })
        DropdownInput(
            types = types,
            selectedText = selectedDropdownText,
            onChangedText = { selectedDropdownText = it })
        InputDateTime(Color.White, Color.Black, selectedDate = selectedDate)
        InputNotes(Color.White, Color.Black, textNote = textNote, onChangedText = { textNote = it })

        ButtonSubmit(
            Color.White,
            Color(0xFF758BFD),
            onClickButton = {
                viewModel
                    .updateExpense(
                            expenseId = expense.id,
                            date = SimpleDateFormat("dd/MMMM/yyyy").format(selectedDate.value),
                            expense = textNominal.toInt(),
                            notes = textNote,
                            type = selectedDropdownText

                    )
                loadingDialog.value = true

                Handler(Looper.getMainLooper()).postDelayed({
                    loadingDialog.value = false
                    resultDialog.value = true
                }, 2000)
            }
        )
    }
}