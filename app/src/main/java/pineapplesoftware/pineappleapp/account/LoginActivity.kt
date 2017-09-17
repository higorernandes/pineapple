package pineapplesoftware.pineappleapp.account

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_create_transaction.*

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar_main.*
import pineapplesoftware.pineappleapp.R
import pineapplesoftware.pineappleapp.account.model.AuthenticationData
import pineapplesoftware.pineappleapp.account.model.AuthenticationResult
import pineapplesoftware.pineappleapp.application.PineappleApplication
import pineapplesoftware.pineappleapp.main.MainActivity
import pineapplesoftware.pineappleapp.helper.UserCredentialsHelper
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

        // Setting up the ToolBar.
//        setSupportActionBar(loginToolbar as Toolbar)
//        toolbarTitle.text = resources.getString(R.string.login)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)

        // setUpFacebookLogin()
        prepareViews()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSuccess(result: LoginResult?) {
        Log.d(TAG, "Login successful - UserID: ${result?.accessToken?.userId}}, Auth Token: ${result?.accessToken?.token}.")

        val authenticationResult = AuthenticationResult()
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

    override fun onClick(v: View?) { }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        super.onBackPressed()
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
        facebookLoginButton.setOnClickListener { setUpFacebookLogin() }
        emailSignInButton.setOnClickListener { validateFields() }
    }

    private fun isEmailValid(): Boolean {
        val email : String = loginEditText.text.toString()
        return email.contains("@")
    }

    private fun isPasswordValid(): Boolean {
        val password : String = passwordEditText.text.toString()
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

    private fun validateFields() {
        if (!isEmailValid() ) {
            // TODO: handle it
            return
        }

        if (!isPasswordValid() ) {
            // TODO: handle it
            return
        }

        attemptLogin()
    }

    private fun attemptLogin() {
        emailSignInButton.isEnabled = false

        setUpAuthenticationData()

        val intent : Intent = MainActivity.getActivityIntent(this)
        startActivity(intent)

        emailSignInButton.isEnabled = true
    }

    private fun setUpAuthenticationData(userId: String? = null) {
        val authenticationData = AuthenticationData()
        authenticationData.username = loginEditText?.text.toString()
        authenticationData.password = passwordEditText?.text.toString()
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
