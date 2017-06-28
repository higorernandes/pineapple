package pineapplesoftware.pineappleapp.main.adapter;

import android.content.ClipData
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by nds on 28/06/17.
 */

class ExpensesListArrayAdapter constructor(objects: ArrayList<String>?, listener: (ClipData.Item) -> Unit) : RecyclerView.Adapter<ExpensesListArrayAdapter.Holder>() {

    //region Attributes

    private var mObjects : ArrayList<String>? = null
    private var mContext : Context? = null

    //endregion

    //region Initializers

    init {
        mObjects = objects
    }

    //endregion

    //region Overridden Methods

    override fun onBindViewHolder(holder: Holder?, position: Int) {  }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        return Holder(parent?.rootView as View)
    }

    override fun getItemCount(): Int {
        return mObjects?.size as Int
    }

    //endregion

    //region Inner Classes

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    //endregion
}
