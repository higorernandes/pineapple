package pineapplesoftware.pineappleapp.main

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.activity_create_transaction.*
import kotlinx.android.synthetic.main.toolbar_main.*
import pineapplesoftware.pineappleapp.R
import pineapplesoftware.pineappleapp.application.EventManager
import pineapplesoftware.pineappleapp.main.model.TransactionItemDto
import java.util.*

class CreateTransactionActivity : AppCompatActivity(), View.OnClickListener
{
    //region Overridden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_transaction)

        setSupportActionBar(mainToolbar as Toolbar)
            toolbarTitle.text = resources.getString(R.string.create_transaction_create)
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

        val openSansFontRegular : Typeface = Typeface.createFromAsset(applicationContext.assets, "fonts/OpenSans-Regular.ttf")
        val openSansFontBold : Typeface = Typeface.createFromAsset(applicationContext.assets, "fonts/OpenSans-Bold.ttf")
        toolbarTitle.typeface = openSansFontRegular
        transactionAmountEditText.typeface = openSansFontRegular
        transactionDateEditText.typeface = openSansFontRegular
        transactionDescriptionEditText.typeface = openSansFontRegular
        transactionAmountTextInputLayout.setTypeface(openSansFontRegular)
        transactionDateTextInputLayout.setTypeface(openSansFontRegular)
        transactionDescriptionTextInputLayout.setTypeface(openSansFontRegular)
        transactionTypeEarningRadioButton.typeface = openSansFontRegular
        transactionTypeExpenseRadioButton.typeface = openSansFontRegular
        createTransactionButtonSave.typeface = openSansFontBold
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
