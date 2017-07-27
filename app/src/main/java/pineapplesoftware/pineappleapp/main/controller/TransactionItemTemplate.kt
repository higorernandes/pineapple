package pineapplesoftware.pineappleapp.main.controller

import android.util.Log
import pineapplesoftware.pineappleapp.main.model.TransactionItemDto

/**
 * Created by root on 2017-07-26.
 * This is a template method to create a new expense.
 */
abstract class TransactionItemTemplate
{
    private val TAG : String = "ExpenseItemTemplate"

    fun expenseItemTemplate(expenseType: TransactionItemDto.ExpenseType) {
        Log.d(TAG, "Creating a new expense...")
        var transaction : TransactionItemDto? = null
        if (expenseType == TransactionItemDto.ExpenseType.EXPENSE) {
            transaction = createExpense()
        } else {
            transaction = createEarning()
        }

        createTransaction(transaction)
//        saveTransactionToLocalDb()
//        saveTransactionToRemoteServer()
//        returnToPreviousActivity()
    }

    abstract fun createExpense() : TransactionItemDto
    abstract fun createEarning() : TransactionItemDto

    fun createTransaction(transaction : TransactionItemDto) {

    }
}