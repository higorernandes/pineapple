package pineapplesoftware.pineappleapp.main.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.facebook.login.widget.ProfilePictureView

import pineapplesoftware.pineappleapp.R
import pineapplesoftware.pineappleapp.main.adapter.TransactionsListArrayAdapter
import pineapplesoftware.pineappleapp.main.model.AccountDto
import pineapplesoftware.pineappleapp.main.model.TransactionItemDto
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() , View.OnClickListener
{
    //region Attributes

    private var mMainExpensesRecyclerView : RecyclerView? = null
    private var mUserProfilePictureView: ProfilePictureView? = null

    private var mObjects : ArrayList<TransactionItemDto> = ArrayList<TransactionItemDto>()
    private var mUserId : String? = null

    //endregion

    //region Overidden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mUserId = intent.getStringExtra(USER_ID) ?: throw IllegalStateException("Field $USER_ID missing in Intent.")

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
        mUserProfilePictureView = findViewById(R.id.profile_picture) as ProfilePictureView?


        mMainExpensesRecyclerView?.layoutManager = LinearLayoutManager(this)
        mMainExpensesRecyclerView?.itemAnimator = DefaultItemAnimator()
        mMainExpensesRecyclerView?.adapter = TransactionsListArrayAdapter(mObjects) {
            Log.e("CLICKED", "item ${it.transactionName}")
        }

        mUserProfilePictureView?.profileId = mUserId
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
