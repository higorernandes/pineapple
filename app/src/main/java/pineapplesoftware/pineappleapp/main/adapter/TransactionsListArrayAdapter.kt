package pineapplesoftware.pineappleapp.main.adapter;

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pineapplesoftware.pineappleapp.R
import pineapplesoftware.pineappleapp.main.model.TransactionItemDto

/**
 * Created by nds on 28/06/17.
 */

class TransactionsListArrayAdapter constructor(context: Context, objects: ArrayList<TransactionItemDto>, val clickListener: (TransactionItemDto) -> Unit) : RecyclerView.Adapter<TransactionsListArrayAdapter.Holder>() {

    //region Attributes

    private var mContext : Context
    private var mObjects : ArrayList<TransactionItemDto> = ArrayList<TransactionItemDto>()

    //endregion

    //region Initializers

    init {
        mObjects = objects
        mContext = context
    }

    //endregion

    //region Overridden Methods

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        val transactionItem : TransactionItemDto = mObjects[position]

        if (transactionItem.transactionType == TransactionItemDto.ExpenseType.EARNING) {
            holder?.transactionType?.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorEarning))
        } else {
            holder?.transactionType?.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent))
        }

        holder?.transactionName?.text = transactionItem.transactionName
        holder?.transactionDescription?.text = transactionItem.transactionDescription
        holder?.transactionAmount?.text = "R$ 25,30"
        holder?.transactionDate?.text = "25/06"

        //holder?.containerView?.setOnClickListener { clickListener(transactionItem) }
        holder?.containerView?.setOnClickListener { clickListener(transactionItem) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        var itemView : View = LayoutInflater.from(parent?.context).inflate(R.layout.item_transaction, parent, false)
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
        var transactionAmount : TextView? = null
        var transactionDate : TextView? = null
        var transactionType : View? = null

        init {
            transactionName = itemView.findViewById<TextView>(R.id.mainFragmentTransactionNameTextView)
            transactionDescription = itemView.findViewById<TextView>(R.id.mainFragmentTransactionDescriptionTextView)
            transactionAmount = itemView.findViewById<TextView>(R.id.mainFragmentTransactionAmountTextView)
            transactionDate = itemView.findViewById<TextView>(R.id.mainFragmentTransactionDateTextView)
            transactionType = itemView.findViewById<View>(R.id.mainFragmentTransactionTypeView)
        }
    }

    //endregion
}
