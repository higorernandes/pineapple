package pineapplesoftware.pineappleapp.main.controller

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import pineapplesoftware.pineappleapp.R
import pineapplesoftware.pineappleapp.main.model.TransactionItemDto
import pineapplesoftware.pineappleapp.main.observer.Observer
import pineapplesoftware.pineappleapp.main.observer.TransactionChangeSubject

class CreateTransactionActivity : AppCompatActivity(), TransactionChangeSubject
{
    //region Attributes

    private var mObservers : ArrayList<Observer> = ArrayList()

    //endregion

    //region Overridden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_transaction)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun registerObserver(observer: Observer) {
        if (!mObservers.contains(observer)) {
            mObservers.add(observer)
        }
    }

    override fun removeObserver(observer: Observer) {
        if (mObservers.contains(observer)) {
            mObservers.remove(observer)
        }
    }

    override fun notifyObserversItemAdded(transactionItem: TransactionItemDto) {
        for (observer in mObservers) {
            // TODO: create TransactionItemDto and pass it at the below Method
            observer.updateAddedItem(transactionItem)
        }
    }

    override fun notifyObserversItemEdited(index: Int) {
        for (observer in mObservers) {
            observer.updateEditedItem(atIndex = index)
        }
    }

    override fun notifyObserversItemRemoved(index: Int) {
        for (observer in mObservers) {
            observer.updateDeletedItem(atIndex = index)
        }
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
