package com.example.pengeluaranku.view

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pengeluaranku.database.Income
import com.example.pengeluaranku.ui.theme.PengeluarankuTheme
import com.example.pengeluaranku.view.component.LoadingDialog
import com.example.pengeluaranku.view.component.ResultDialog
import com.example.pengeluaranku.viewModel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InputIncome(viewModel: MainViewModel, navController: NavController) {

    var textNominal by remember { mutableStateOf("0") }

    val mCalendar = Calendar.getInstance()
    val selectedDate = remember { mutableStateOf(mCalendar.time) }

    var textNote by remember { mutableStateOf("") }

    val loadingDialog = remember { mutableStateOf(false) }
    val resultDialog = remember { mutableStateOf(false) }

    if (loadingDialog.value) {
        LoadingDialog(onDismiss = {}, loadingWord = "Uploading")
    }

    if (resultDialog.value) {
        ResultDialog(
            onDismiss = { navController.popBackStack() },
            resultString = "Success",
            actionString = "Upload"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp, top = 120.dp, bottom = 25.dp)
    ) {
        NominalInput(
            "Income",
            Color.Black,
            text = textNominal,
            onChangedText = { if (it.length <= 11) textNominal = it })
        InputDateTime(Color(0xFF758BFD), Color.White, selectedDate = selectedDate)
        InputNotes(
            Color(0xFF758BFD),
            Color.White,
            textNote = textNote,
            onChangedText = { textNote = it })

        ButtonSubmit(
            bgColor = Color(0xFF758BFD),
            Color.White,
            onClickButton = {
                viewModel.insertIncome(
                    Income(
                        date = SimpleDateFormat("dd/MMMM/yyyy").format(selectedDate.value),
                        income = textNominal.toInt(),
                        notes = textNote
                    )
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

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun InputIncomePreview() {
    PengeluarankuTheme {
//        InputIncome()
    }
}