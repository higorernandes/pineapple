package pineapplesoftware.pineappleapp.main.controller

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.style.TtsSpan
import android.view.View
import kotlinx.android.synthetic.main.activity_create_transaction.*
import pineapplesoftware.pineappleapp.R
import pineapplesoftware.pineappleapp.application.EventManager
import pineapplesoftware.pineappleapp.main.model.TransactionItemDto
import pineapplesoftware.pineappleapp.main.observer.Observer
import pineapplesoftware.pineappleapp.main.observer.TransactionChangeSubject
import java.util.*

class CreateTransactionActivity : AppCompatActivity(), View.OnClickListener
{
    //region Overridden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_transaction)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        prepareViews()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.createTransactionButtonSave -> createTransaction()
        }
    }

    //endregion

    //region Private Methods

    private fun prepareViews() {
        createTransactionButtonSave.setOnClickListener(this)
    }

    private fun createTransaction() {
        val transactionItem : TransactionItemDto = TransactionItemDto()
        transactionItem.transactionAmount = transactionAmountEditText.text.toString()
        transactionItem.transactionDescription = transactionDescriptionEditText.text.toString()
        transactionItem.transactionName = transactionDescriptionEditText.text.toString()
        transactionItem.transactionDate = Date()
        transactionItem.transactionType = if (transactionTypeEarningRadioButton.isSelected) TransactionItemDto.ExpenseType.EARNING else TransactionItemDto.ExpenseType.EXPENSE

        EventManager.getInstance()?.notifyObserversItemAdded(transactionItem)
        finish()
    }

    //endregion

    //region Companion Object

    companion object {
        fun getActivityIntent(context: Context) : Intent {
            val intent : Intent = Intent(context, CreateTransactionActivity::class.java)
            return intent
        }
    }

    //endregion
}
