package pineapplesoftware.pineappleapp.main.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View

import pineapplesoftware.pineappleapp.R
import pineapplesoftware.pineappleapp.main.adapter.TransactionsListArrayAdapter
import pineapplesoftware.pineappleapp.main.model.AccountDto
import pineapplesoftware.pineappleapp.main.model.TransactionItemDto
import java.util.*

class MainActivity : AppCompatActivity() , View.OnClickListener
{
    //region Attributes

    private var mMainExpensesRecyclerView : RecyclerView? = null

    private var mObjects : ArrayList<TransactionItemDto> = ArrayList<TransactionItemDto>()

    //endregion

    //region Overidden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()
        getViews()
    }

    override fun onClick(view: View?) {
        if (view != null) {

        }
    }

    //endregion

    //region Private Methods

    private fun loadData() {
        mObjects.add(TransactionItemDto(1, "COMPRAS MERCADO", "MERCADO DA GENTE", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto()))
        mObjects.add(TransactionItemDto(1, "COMPRAS SHOPPING", "Riachuelo", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto()))
        mObjects.add(TransactionItemDto(1, "PADARIA", "Compra de pães", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto()))
        mObjects.add(TransactionItemDto(1, "POSTO", "Gasolina", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto()))
        mObjects.add(TransactionItemDto(1, "COMPRAS MERCADO", "Compras 25/08", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto()))
        mObjects.add(TransactionItemDto(1, "RESTAURANTE", "Banana da Terra", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto()))
    }

    private fun getViews() {
        mMainExpensesRecyclerView = findViewById(R.id.mainExpensesRecyclerView) as RecyclerView?

        mMainExpensesRecyclerView?.layoutManager = LinearLayoutManager(this)
        mMainExpensesRecyclerView?.itemAnimator = DefaultItemAnimator()
        mMainExpensesRecyclerView?.adapter = TransactionsListArrayAdapter(mObjects) {
            Log.e("CLICKED", "item ${it.transactionName}")
        }
    }

    //endregion

}
