package pineapplesoftware.pineappleapp.helper

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import pineapplesoftware.pineappleapp.account.model.AuthenticationData
import pineapplesoftware.pineappleapp.account.model.AuthenticationResult
import pineapplesoftware.pineappleapp.application.PineappleApplication

/**
 * Created by root on 2017-07-26.
 */
class UserCredentialsHelper private constructor()
{
    //region Attributes

    private var TAG : String = "UserCredentialsHelper"

    //endregion

    //region Companion Object

    companion object {
        private val AUTH_TOKEN_SHARED_KEY : String = "AUTH_TOKEN_SHARED_KEY"
        private val AUTH_DATA_SHARED_KEY : String = "AUTH_DATA_SHARED_KEY"
        private var manager : UserCredentialsHelper? = null

        fun getInstance() : UserCredentialsHelper {
            if (manager == null) {
                manager = UserCredentialsHelper()
            }

            return manager!!
        }
    }

    //endregion

    //region Public Methods

    fun saveCurrentAuthenticationData(authData: AuthenticationData) {
        val gson = Gson()
        val jsonString : String = gson.toJson(authData)
        SharedPreferencesHelper.saveStringInSharedPreferences(PineappleApplication.getContext(), AUTH_DATA_SHARED_KEY, jsonString)
    }

    fun getAuthenticationData() : AuthenticationData? {
        val authDataString : String? = SharedPreferencesHelper.getStringFromSharedPreferences(PineappleApplication.getContext(), AUTH_DATA_SHARED_KEY)
        if (authDataString != null && TextUtils.isEmpty(authDataString)) {
            return null
        }

        try {
            val gson = Gson()
            val returnData : AuthenticationData = gson.fromJson(authDataString, AuthenticationData::class.java)
            return returnData
        } catch (e: Exception) {
            Log.e(TAG, e.message)
            return null
        }
    }

    fun saveCurrentToken(token: AuthenticationResult) {
        val gson = Gson()
        val jsonString : String = gson.toJson(token)
        SharedPreferencesHelper.saveStringInSharedPreferences(PineappleApplication.getContext(), AUTH_TOKEN_SHARED_KEY, jsonString)
    }

    fun getCurrentToken() : AuthenticationResult? {
        val tokenString : String? = SharedPreferencesHelper.getStringFromSharedPreferences(PineappleApplication.getContext(), AUTH_TOKEN_SHARED_KEY)
        if (tokenString == null || tokenString.isEmpty()) {
            return null
        }

        try {
            val gson : Gson = Gson()
            val returnToken : AuthenticationResult = gson.fromJson(tokenString, AuthenticationResult::class.java)
            return returnToken
        } catch (e: Exception) {
            Log.e(TAG, e.message)
            return null
        }
    }

    fun removeCurrentToken() {
        SharedPreferencesHelper.removeFromSharedPreferences(PineappleApplication.getContext(), AUTH_TOKEN_SHARED_KEY)
    }

    fun removeCurrentAuthData() {
        SharedPreferencesHelper.removeFromSharedPreferences(PineappleApplication.getContext(), AUTH_DATA_SHARED_KEY)
    }

    //endregion
}