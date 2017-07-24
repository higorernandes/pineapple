package pineapplesoftware.pineappleapp.main.controller

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.view.*

import pineapplesoftware.pineappleapp.R
import pineapplesoftware.pineappleapp.main.adapter.TransactionsListArrayAdapter
import pineapplesoftware.pineappleapp.main.model.AccountDto
import pineapplesoftware.pineappleapp.main.model.TransactionItemDto
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() , View.OnClickListener
{
    //region Attributes

    private var mObjects : ArrayList<TransactionItemDto> = ArrayList<TransactionItemDto>()
    private var mUserId : String? = null

    //endregion

    //region Overidden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mUserId = intent.getStringExtra(USER_ID) ?: throw IllegalStateException("Field ${USER_ID} missing in Intent.")

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
        mObjects.add(TransactionItemDto(1, "Compras Mercado", "Mercado da Gente", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto(), ""))
        mObjects.add(TransactionItemDto(1, "Compras Shopping", "Riachuelo", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto(), ""))
        mObjects.add(TransactionItemDto(1, "Padaria", "Compra de pães", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto(), ""))
        mObjects.add(TransactionItemDto(1, "Posto", "Gasolina", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto(), ""))
        mObjects.add(TransactionItemDto(1, "Compras Mercado", "Compras 25/08", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto(), ""))
        mObjects.add(TransactionItemDto(1, "Restaurante", "Banana da Terra", TransactionItemDto.ExpenseType.EXPENSE, Date(), AccountDto(), ""))
    }

    private fun getViews() {
        mainExpensesRecyclerView?.layoutManager = LinearLayoutManager(this)
        mainExpensesRecyclerView?.itemAnimator = DefaultItemAnimator()
        mainExpensesRecyclerView?.adapter = TransactionsListArrayAdapter(applicationContext, mObjects) {
            //TODO: navigate to transaction detail screen
            Log.e("CLICKED", "item ${it.transactionName}")
        }

        setSupportActionBar(mainToolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.mipmap.ic_launcher)
        mainToolbar.userProfilePicture?.profileId = mUserId

        val zillaSlabFontRegular : Typeface = Typeface.createFromAsset(applicationContext.assets, "fonts/ZillaSlab-Regular.ttf")
        mainToolbar.toolbarTitle.typeface = zillaSlabFontRegular
    }

    private fun getUsersFacebookProfilePicture(userId: String?) : Bitmap {
        val imageUrl : URL = URL("https://graph.facebook.com/$userId/picture?type=large")
        val bitmap : Bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream())
        return bitmap
    }

    //endregion

    //region Inner Objects and Classes

    companion object {
        private val USER_ID = "user_id"

        fun getActivityIntent(context: Context, userId: String?) : Intent {
            var intent : Intent = Intent(context, MainActivity::class.java)
            intent.putExtra(USER_ID, userId)
            return intent
        }
    }

    //endregion

}