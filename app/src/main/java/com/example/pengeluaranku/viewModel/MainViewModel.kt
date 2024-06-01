package com.example.pengeluaranku.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pengeluaranku.database.Expense
import com.example.pengeluaranku.database.ExpenseRepository
import com.example.pengeluaranku.database.Income
import com.example.pengeluaranku.database.IncomeRepository
import com.example.pengeluaranku.database.PengeluarankuDatabase

class MainViewModel(application: Application):ViewModel() {

    val allExepense: LiveData<List<Expense>>
    private val expenseRepository: ExpenseRepository

    val allIncome: LiveData<List<Income>>
    private val incomeRepository:IncomeRepository

    init {
        val pengeluarankuDatabase= PengeluarankuDatabase.getInstance(application)
        val expenseDao = pengeluarankuDatabase.expenseDao()
        expenseRepository = ExpenseRepository(expenseDao)
        allExepense = expenseRepository.allExpense

        val incomeDao = pengeluarankuDatabase.incomeDao()
        incomeRepository = IncomeRepository(incomeDao)
        allIncome = incomeRepository.allIncome
    }

    fun insertExpense(expense: Expense){
        expenseRepository.insertExpense(expense)
    }

    fun updateExpense(expenseId: Int, expense: Int, type: String, date: String, notes: String){
        expenseRepository.updateExpense(expenseId, expense, type, date, notes)
    }

    fun deleteExpense(id:Int){
        expenseRepository.deleteExpense(id)
    }

    fun insertIncome(income: Income){
        incomeRepository.insertIncome(income)
    }

    fun updateIncome(incomeId: Int, income: Int,date: String, notes: String){
        incomeRepository.updateIncome(incomeId, income, date, notes)
    }

    fun deleteIncome(id: Int){
        incomeRepository.deleteIncome(id)
    }
}