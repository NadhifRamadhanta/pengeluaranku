package com.example.pengeluaranku.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity(tableName = "expense")
class Expense {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "expenseId")
    var id: Int = 0
    var expense: Int = 0
    var type: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    var date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MMMM/yyyy")).toString()

    var notes: String = ""

    constructor()

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(expense: Int, type: String, date: String, notes: String){
        this.expense = expense
        this.type = type
        this.date = date
        this.notes = notes
    }

}