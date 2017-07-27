package pineapplesoftware.pineappleapp.application

import android.content.Context
import android.util.Log

/**
 * Created by root on 2017-07-26.
 */
class PineappleApplication(context: Context)
{
    //region Constructor

    init {
        mContext = context
    }

    //endregion

    //region Companion Object

    companion object {
        private var mContext : Context? = null

        fun getContext() : Context {
            if (mContext == null) {
                Log.e("PineappleApplication", "You should call PineappleApplication() before calling any other method")
            }
            return mContext!!
        }
    }

    //endregion
}