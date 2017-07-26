package pineapplesoftware.pineappleapp.main.observer

import pineapplesoftware.pineappleapp.main.model.TransactionItemDto

/**
 * Created by root on 2017-07-26.
 */
interface TransactionChangeSubject
{
    fun registerObserver(observer: Observer)
    fun removeObserver(observer: Observer)
    fun notifyObserversItemAdded(transactionItem: TransactionItemDto)
    fun notifyObserversItemEdited(index: Int)
    fun notifyObserversItemRemoved(index: Int)
}