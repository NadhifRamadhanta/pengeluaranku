package com.example.pengeluaranku.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "income")
class Income {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "incomeId")
    var id: Int = 0
    var income: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    var date: String = LocalDate.now().toString()

    var notes: String = ""

    constructor()

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(income: Int, date: String, notes: String){
        this.income = income
        this.date = date
        this.notes = notes
    }

}