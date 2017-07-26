package pineapplesoftware.pineappleapp.main.controller

import android.util.Log

/**
 * Created by root on 2017-07-26.
 * This is a template method to create a new expense.
 */
abstract class ExpenseItemTemplate
{
    private val TAG : String = "ExpenseItemTemplate"

    fun expenseItemTemplate() {
        Log.d(TAG, "Creating a new expense...")
    }

    abstract fun createExpense();
    abstract fun createEarning();
}