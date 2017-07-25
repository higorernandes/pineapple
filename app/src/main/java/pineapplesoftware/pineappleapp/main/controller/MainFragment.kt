package pineapplesoftware.pineappleapp.main.controller

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

import pineapplesoftware.pineappleapp.R
import pineapplesoftware.pineappleapp.main.adapter.TransactionsListArrayAdapter

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_main, container, false)
    }

//    private fun getViews() {
//        mainExpensesRecyclerView?.layoutManager = LinearLayoutManager(this)
//        mainExpensesRecyclerView?.itemAnimator = DefaultItemAnimator()
//        mainExpensesRecyclerView?.adapter = TransactionsListArrayAdapter(applicationContext, mObjects) {
//            //TODO: navigate to transaction detail screen
//            Log.e("CLICKED", "item ${it.transactionName}")
//        }
//
//
//
//        val zillaSlabFontRegular : Typeface = Typeface.createFromAsset(applicationContext.assets, "fonts/ZillaSlab-Regular.ttf")
//        mainToolbar.toolbarTitle.typeface = zillaSlabFontRegular
//        name.typeface = zillaSlabFontRegular
//    }

}
