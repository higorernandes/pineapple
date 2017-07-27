package pineapplesoftware.pineappleapp.main.controller

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*

import pineapplesoftware.pineappleapp.R
import pineapplesoftware.pineappleapp.application.EventManager
import pineapplesoftware.pineappleapp.main.adapter.TransactionsListArrayAdapter
import pineapplesoftware.pineappleapp.main.model.AccountDto
import pineapplesoftware.pineappleapp.main.model.TransactionItemDto
import pineapplesoftware.pineappleapp.main.observer.Observer
import pineapplesoftware.pineappleapp.main.observer.TransactionChangeSubject
import java.util.*

class MainFragment : Fragment(), Observer
{
    //region Attributes

    private val TAG : String = "MainFragment"

    private var mTransactionChangeSubject : TransactionChangeSubject? = null
    private var mObjects : ArrayList<TransactionItemDto> = ArrayList<TransactionItemDto>()

    //endregion

    //region Overridden Methods

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Registering the current class as an observer for the list changes.
        EventManager.getInstance()?.registerObserver(this)

        loadData()
        prepareViews()
    }

    override fun updateDeletedItem(atIndex: Int) {

    }

    override fun updateEditedItem(atIndex: Int) {

    }

    override fun updateAddedItem(transactionItem: TransactionItemDto) {
        mObjects.add(transactionItem)

        activity.runOnUiThread() {
            mainExpensesRecyclerView.adapter.notifyDataSetChanged()
        }
    }

    //endregion

    //region Private Methods

    private fun loadData() {
        mObjects.add(TransactionItemDto(1, "Compras Mercado", "Mercado da Gente", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto(), ""))
        mObjects.add(TransactionItemDto(1, "Compras Shopping", "Riachuelo", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto(), ""))
        mObjects.add(TransactionItemDto(1, "Padaria", "Compra de pães", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto(), ""))
        mObjects.add(TransactionItemDto(1, "Posto", "Gasolina", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto(), ""))
        mObjects.add(TransactionItemDto(1, "Compras Mercado", "Compras 25/08", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto(), ""))
        mObjects.add(TransactionItemDto(1, "Restaurante", "Banana da Terra", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto(), ""))
    }

    private fun prepareViews() {
        mainExpensesRecyclerView?.layoutManager = LinearLayoutManager(context)
        mainExpensesRecyclerView?.itemAnimator = DefaultItemAnimator()
        mainExpensesRecyclerView?.adapter = TransactionsListArrayAdapter(context!!, mObjects) {
            //TODO: navigate to transaction detail screen
            Log.e("CLICKED", "item ${it.transactionName}")
        }
    }

    //endregion
}
