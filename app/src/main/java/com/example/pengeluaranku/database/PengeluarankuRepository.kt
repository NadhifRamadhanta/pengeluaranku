package com.example.pengeluaranku.database

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    val allExpense: LiveData<List<Expense>> = expenseDao.getAllExpense()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertExpense(newExpense : Expense){
        coroutineScope.launch(Dispatchers.IO) {
            expenseDao.insertExpense(newExpense)
        }
    }

    fun updateExpense(expenseId: Int, expense: Int, type: String, date: String, notes: String){
        coroutineScope.launch(Dispatchers.IO) {
            expenseDao.updateExpense(expenseId, expense, type, date, notes)
        }
    }

    fun deleteExpense(id: Int){
        coroutineScope.launch(Dispatchers.IO) {
            expenseDao.deleteExpense(id)
        }
    }
}

class IncomeRepository(private val incomeDao: IncomeDao){
    val allIncome: LiveData<List<Income>> = incomeDao.getAllIncome()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertIncome(newIncome : Income){
        coroutineScope.launch(Dispatchers.IO) {
            incomeDao.insertIncome(newIncome)
        }
    }

    fun updateIncome(incomeId: Int, income: Int,date: String, notes: String){
        coroutineScope.launch(Dispatchers.IO) {
            incomeDao.updateIncome(incomeId, income, date, notes)
        }
    }

    fun deleteIncome(id: Int){
        coroutineScope.launch(Dispatchers.IO) {
            incomeDao.deleteIncome(id)
        }
    }
}