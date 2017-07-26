package pineapplesoftware.pineappleapp.main.observer

import pineapplesoftware.pineappleapp.main.model.TransactionItemDto

/**
 * Created by root on 2017-07-26.
 */
interface Observer
{
    fun updateDeletedItem(atIndex: Int)
    fun updateEditedItem(atIndex: Int)
    fun updateAddedItem(transactionItem: TransactionItemDto)
}