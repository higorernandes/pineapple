package pineapplesoftware.pineappleapp.account.controller

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

import kotlinx.android.synthetic.main.activity_login.*
import pineapplesoftware.pineappleapp.R
import pineapplesoftware.pineappleapp.account.model.AuthenticationData
import pineapplesoftware.pineappleapp.account.model.AuthenticationResult
import pineapplesoftware.pineappleapp.application.PineappleApplication
import pineapplesoftware.pineappleapp.main.controller.MainActivity
import pineapplesoftware.pineappleapp.helper.UserCredentialsHelper
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class LoginActivity : AppCompatActivity(), FacebookCallback<LoginResult>, View.OnClickListener {

    //region Attributes

    private var TAG : String = "LoginActivity"

    private var mCallbackManager : CallbackManager? = null

    //endregion

    //region Overridden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        PineappleApplication(this)

        setUpFacebookLogin()

        prepareViews()
        setFonts()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSuccess(result: LoginResult?) {
        Log.d(TAG, "Login successful - UserID: ${result?.accessToken?.userId}}, Auth Token: ${result?.accessToken?.token}.")

        val authenticationResult : AuthenticationResult = AuthenticationResult()
        authenticationResult.accessToken = result?.accessToken?.token
        UserCredentialsHelper.getInstance().saveCurrentToken(authenticationResult)

        setUpAuthenticationData(result?.accessToken?.userId)

        val intent : Intent = MainActivity.getActivityIntent(this)
        startActivity(intent)

        emailSignInButton.isEnabled = true
    }

    override fun onCancel() {
        Log.d(TAG, "Login attempt has been canceled.")
    }

    override fun onError(error: FacebookException?) {
        Log.e(TAG, "Could not log onto Facebook: ${error?.message}.")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.emailSignInButton -> {
                if (isEmailValid() && isPasswordValid()) {
                    attemptLogin()
                }
            }
        }
    }

    //endregion

    //region Private Methods

    private fun setUpFacebookLogin() {
        FacebookSdk.sdkInitialize(applicationContext)
        mCallbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(mCallbackManager, this)
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email"))
    }

    private fun prepareViews() {
        facebookLoginButton.setOnClickListener(this)
        emailSignInButton.setOnClickListener { attemptLogin() }
    }

    private fun setFonts() {
        val openSansFontRegular : Typeface = Typeface.createFromAsset(applicationContext.assets, "fonts/OpenSans-Regular.ttf")

        emailTextInputLayout.setTypeface(openSansFontRegular)
        passwordTextInputLayout.setTypeface(openSansFontRegular)
        loginTextView.typeface = openSansFontRegular
        passwordTextView.typeface = openSansFontRegular
        facebookLoginButton.typeface = openSansFontRegular
        emailSignInButton.typeface = openSansFontRegular
        appNameTextView.typeface = openSansFontRegular

        facebookLoginButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
    }

    private fun isEmailValid(): Boolean {
        val email : String = loginTextView.text.toString()
        return email.contains("@")
    }

    private fun isPasswordValid(): Boolean {
        val password : String = passwordTextView.text.toString()
        return password.length > 4
    }

    /**
     * This method gets the App's HashKey for Facebook authentication. Used for test purposes only.
     */
//    private fun getFacebookAppHashKey() {
//        try {
//            var info : PackageInfo = packageManager.getPackageInfo("pineapplesoftware.pineappleapp", PackageManager.GET_SIGNATURES)
//            for (signature in info.signatures) {
//                val messageDigest : MessageDigest = MessageDigest.getInstance("SHA")
//                messageDigest.update(signature.toByteArray())
//                Log.d(TAG, "KeyHash: ${Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT)}")
//            }
//        } catch (e: PackageManager.NameNotFoundException) {
//            Log.e(TAG,"The package name could not be found.")
//        } catch (e: NoSuchAlgorithmException) {
//            Log.e(TAG, "Could not find the given algorithm.")
//        }
//    }

    private fun attemptLogin() {
        emailSignInButton.isEnabled = false

        setUpAuthenticationData()

        val intent : Intent = MainActivity.getActivityIntent(this)
        startActivity(intent)

        emailSignInButton.isEnabled = true
    }

    fun setUpAuthenticationData(userId: String? = null) {
        val authenticationData : AuthenticationData = AuthenticationData()
        authenticationData.username = loginTextView?.text.toString()
        authenticationData.password = passwordTextView?.text.toString()
        authenticationData.userId = userId ?: "whateverId"
        UserCredentialsHelper.getInstance().saveCurrentAuthenticationData(authenticationData)
    }

    //endregion

    //region Companion Object

    companion object {
        fun getActivityIntent(context: Context) : Intent {
            var intent : Intent = Intent(context, LoginActivity::class.java)
            return intent
        }
    }

    //endregion
}
