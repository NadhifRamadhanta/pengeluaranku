package com.example.pengeluaranku.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDao {
    @Insert
    fun insertExpense(expense: Expense)

    @Query("UPDATE expense SET expense = :expense, type = :type, date = :date, notes = :notes WHERE expenseId = :expenseId")
    suspend fun updateExpense(expenseId: Int, expense: Int, type: String, date: String, notes: String)


    @Query("DELETE FROM expense WHERE expenseId = :id")
    fun deleteExpense(id:Int)

    @Query("SELECT * FROM expense")
    fun getAllExpense(): LiveData<List<Expense>>
}

@Dao
interface IncomeDao{
    @Insert
    fun insertIncome(income: Income)

    @Query("UPDATE income SET income = :income, date = :date, notes = :notes WHERE incomeId = :incomeId")
    suspend fun updateIncome(incomeId: Int, income: Int, date: String, notes: String)

    @Query("DELETE FROM income WHERE incomeId = :id")
    fun deleteIncome(id:Int)

    @Query("SELECT * FROM income")
    fun getAllIncome(): LiveData<List<Income>>
}