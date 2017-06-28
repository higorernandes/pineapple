package pineapplesoftware.pineappleapp.main.view

import android.content.ClipData
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView

import pineapplesoftware.pineappleapp.R
import pineapplesoftware.pineappleapp.main.adapter.ExpensesListArrayAdapter

class MainActivity : AppCompatActivity() , View.OnClickListener
{
    //region Attributes

    private var mMainExpensesRecyclerView : RecyclerView? = null

    private var mObjects : ArrayList<String>? = null

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
        mObjects = ArrayList<String>()
    }

    private fun getViews() {
        mMainExpensesRecyclerView = findViewById(R.id.mainExpensesRecyclerView) as RecyclerView?

        mMainExpensesRecyclerView?.layoutManager = LinearLayoutManager(this)
        mMainExpensesRecyclerView?.itemAnimator = DefaultItemAnimator()
        mMainExpensesRecyclerView?.adapter = ExpensesListArrayAdapter(mObjects) {
            Log.e("CLICKED", "item x")
        }
    }

    //endregion

}
