package pineapplesoftware.pineappleapp.main.adapter;

import android.content.ClipData
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pineapplesoftware.pineappleapp.R
import pineapplesoftware.pineappleapp.main.model.TransactionItemDto
import java.text.DateFormat

/**
 * Created by nds on 28/06/17.
 */

class TransactionsListArrayAdapter constructor(objects: ArrayList<TransactionItemDto>, val clickListener: (TransactionItemDto) -> Unit) : RecyclerView.Adapter<TransactionsListArrayAdapter.Holder>() {

    //region Attributes

    private var mObjects : ArrayList<TransactionItemDto> = ArrayList<TransactionItemDto>()

    //endregion

    //region Initializers

    init {
        mObjects = objects
    }

    //endregion

    //region Overridden Methods

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        var transactionItem : TransactionItemDto = mObjects[position]

        holder?.transactionName?.text = transactionItem.transactionName
        holder?.transactionDescription?.text = transactionItem.transactionDescription
        holder?.transactionDate?.text = "25/06"

        holder?.containerView?.setOnClickListener { clickListener(transactionItem) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        var itemView : View = LayoutInflater.from(parent?.context).inflate(R.layout.fragment_main_transaction_item, parent, false)
        return Holder(itemView)

    }

    override fun getItemCount(): Int {
        return mObjects.size
    }

    //endregion

    //region Inner Classes

    class Holder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var containerView : View = itemView
        var transactionName : TextView? = null
        var transactionDescription : TextView? = null
        var transactionDate : TextView? = null

        init {
            transactionName = itemView.findViewById(R.id.mainFragmentTransactionNameTextView) as TextView
            transactionDescription = itemView.findViewById(R.id.mainFragmentTransactionDescriptionTextView) as TextView
            transactionDate = itemView.findViewById(R.id.mainFragmentTransactionDateTextView) as TextView
        }
    }

    //endregion
}
