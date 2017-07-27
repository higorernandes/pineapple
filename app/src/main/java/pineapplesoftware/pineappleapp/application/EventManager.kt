package pineapplesoftware.pineappleapp.application

import android.util.EventLog
import pineapplesoftware.pineappleapp.main.model.TransactionItemDto
import pineapplesoftware.pineappleapp.main.observer.Observer
import pineapplesoftware.pineappleapp.main.observer.TransactionChangeSubject
import java.util.ArrayList

/**
 * Created by root on 2017-07-27.
 */
class EventManager private constructor(): TransactionChangeSubject
{
    //region Attributes

    private var mObservers : ArrayList<Observer> = ArrayList()

    //endregion

    //region Companion Object

    companion object {
        private var mInstance : EventManager? = null

        fun getInstance() : EventManager? {
            if (mInstance == null) {
                mInstance = EventManager()
            }

            return mInstance
        }
    }

    //endregion

    //region Overridden Methods

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
}