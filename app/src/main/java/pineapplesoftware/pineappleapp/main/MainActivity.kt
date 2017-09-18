package pineapplesoftware.pineappleapp.main

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.text.Spannable
import android.text.SpannableString
import android.util.Log
import android.view.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.app_bar_main.view.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.toolbar_main.view.*

import pineapplesoftware.pineappleapp.R
import pineapplesoftware.pineappleapp.account.LoginActivity
import pineapplesoftware.pineappleapp.application.PineappleApplication
import pineapplesoftware.pineappleapp.helper.SharedPreferencesHelper
import pineapplesoftware.pineappleapp.helper.UserCredentialsHelper
import pineapplesoftware.pineappleapp.util.CircleTransform
import pineapplesoftware.pineappleapp.util.CustomTypefaceSpan

class MainActivity : AppCompatActivity() , View.OnClickListener, NavigationView.OnNavigationItemSelectedListener
{
    //region Attributes

    private val TAG : String = "MainActivity"

    private val mUrlNavHeaderBackground : String = "http://api.androidhive.info/images/nav-menu-header-bg.jpg"
    private var mUrlNavUserProfile : String = "https://graph.facebook.com/{userId}/picture?type=large"
    private var mUserId : String? = null
    private var mNavItemIndex: Int = 0
    private var mShouldLoadHomeFragOnBackPress = true
    private var mHandler : Handler? = null

    private val TAG_MAIN : String = "Main"
    private val TAG_STATS : String = "Stats"
    private var CURRENT_TAG : String = TAG_MAIN

    //endregion

    //region Overridden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PineappleApplication(this)

        setSupportActionBar(mainToolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mHandler = Handler()

        // Getting the user ID to setup their profile picture.
        mUserId = UserCredentialsHelper.getInstance().getAuthenticationData()?.userId
        mUrlNavUserProfile = mUrlNavUserProfile.replace("{userId}", mUserId as String, false)

        prepareViews()
        loadNavHeader()
        setUpNavigationView()

        if (savedInstanceState == null) {
            mNavItemIndex = 0
            loadMainFragment()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.fab -> {
                val intent : Intent = CreateTransactionActivity.getActivityIntent(this)
                startActivity(intent)
            }
            R.id.nav_logout -> {
                UserCredentialsHelper.getInstance().removeCurrentToken()
                UserCredentialsHelper.getInstance().removeCurrentAuthData()

                val intent : Intent = LoginActivity.getActivityIntent(this)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                CURRENT_TAG = TAG_MAIN
                mNavItemIndex = 0
                loadMainFragment()
                return true
            }
            R.id.nav_stats -> {
                CURRENT_TAG = TAG_STATS
                mNavItemIndex = 1
                loadStatsFragment()
                return true
            }
            R.id.nav_logout -> {
                confirmLogout()
                return true
            }
            R.id.nav_about_us -> {
                Log.d(TAG, "Selected ABOUT US")
                return true
            }
            R.id.nav_rate_us -> {
                Log.d(TAG, "Selected RATE US")
                return true
            }
            else -> {
                Log.d(TAG, "I have no idea what they selected, sorry. :(")
            }
        }


        item.isChecked = !item.isChecked
        item.isChecked  = true

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        if (mNavItemIndex == 0) {
//            menuInflater.inflate(R.menu.activity_main_drawer, menu)
//        }

        return true
    }

    override fun onBackPressed() {
        if (mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainDrawerLayout.closeDrawers()
            return
        }

        if (mShouldLoadHomeFragOnBackPress) {
            if (mNavItemIndex != 0) {
                mNavItemIndex = 0
                loadMainFragment()
                return
            }
        }

        super.onBackPressed()
    }

    //endregion

    //region Private Methods

    private fun prepareViews() {
        fab.setOnClickListener(this)

        // Applying font to all menu items.
        for (index in 0..mainNavigationView.menu.size() - 1) {
            val menuItem : MenuItem = mainNavigationView.menu.getItem(index)
            val subMenu : SubMenu? = menuItem.subMenu
            if (subMenu != null && subMenu.size() > 0) {
                for (subMenuIndex in 0..mainNavigationView.menu.getItem(index).subMenu.size() -1) {
                    applyFontToMenuItem(subMenu.getItem(subMenuIndex))
                }
            }

            applyFontToMenuItem(menuItem)
        }
    }

    private fun applyFontToMenuItem(menuItem: MenuItem) {
        val robotoFontLight : Typeface? = ResourcesCompat.getFont(this, R.font.roboto_light)
        val newTitle : SpannableString = SpannableString(menuItem.title)
        newTitle.setSpan(CustomTypefaceSpan("", robotoFontLight), 0, newTitle.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        menuItem.title = newTitle
    }

    private fun loadNavHeader() {
        // Setting up the basic user information
        mainNavigationView.getHeaderView(0).userName.text = "Higor Ernandes"
        mainNavigationView.getHeaderView(0).userWebsite.text = "www.ismycomputeron.com"

        // Setting up the user image and the background
        //Glide.with(this).load(mUrlNavHeaderBackground).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(mainNavigationView.getHeaderView(0).imageHeaderBackground)
        Glide.with(this).load(mUrlNavUserProfile).crossFade().thumbnail(0.5f).bitmapTransform(CircleTransform(this)).diskCacheStrategy(DiskCacheStrategy.ALL).into(mainNavigationView.getHeaderView(0).imageProfile)
    }

    private fun loadMainFragment() {
        selectNavMenuItem()

        if (supportFragmentManager.findFragmentByTag(CURRENT_TAG) != null) {
            mainDrawerLayout.closeDrawers()
            toggleFab()
            return
        }

        val pendingRunnable = Runnable {
            kotlin.run {
                val fragment : Fragment = getHomeFragment()
                val fragmentTransaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                fragmentTransaction.replace(R.id.mainFrameLayout, fragment, CURRENT_TAG)
                fragmentTransaction.commitAllowingStateLoss()
            }
        }

        mHandler?.post(pendingRunnable)

        mainToolbar.toolbarTitle.text = resources.getString(R.string.app_name)
        toggleFab()
        mainDrawerLayout.closeDrawers()
        invalidateOptionsMenu()
    }

    private fun loadStatsFragment() {
        selectNavMenuItem()

        if (supportFragmentManager.findFragmentByTag(CURRENT_TAG) != null) {
            mainDrawerLayout.closeDrawers()
            toggleFab()
            return
        }

        val pendingRunnable = Runnable {
            kotlin.run {
                val fragment : Fragment = getHomeFragment()
                val fragmentTransaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                fragmentTransaction.replace(R.id.mainFrameLayout, fragment, CURRENT_TAG)
                fragmentTransaction.commitAllowingStateLoss()
            }
        }

        mHandler?.post(pendingRunnable)

        mainToolbar.toolbarTitle.text = resources.getString(R.string.nav_stats)
        toggleFab()
        mainDrawerLayout.closeDrawers()
        invalidateOptionsMenu()
    }

    private fun selectNavMenuItem() {
        mainNavigationView.menu.getItem(mNavItemIndex).isChecked = true
    }

    private fun getHomeFragment() : android.support.v4.app.Fragment {
        return when (mNavItemIndex) {
            0 -> {
                MainFragment()
            }
            1 -> {
                StatsFragment()
            }
            else -> {
                MainFragment()
            }
        }
    }

    private fun setUpNavigationView() {
        mainNavigationView.setNavigationItemSelectedListener(this)

        val actionBarDrawerToggle = ActionBarDrawerToggle(this, mainDrawerLayout, mainToolbar, R.string.open_drawer, R.string.close_drawer)
        mainDrawerLayout.setDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

    private fun toggleFab() {
        if (mNavItemIndex == 0) {
            fab.show()
        } else {
            fab.hide()
        }
    }

    private fun confirmLogout() {
        val alertDialogBuilder : AlertDialog.Builder = AlertDialog.Builder(this)

        // Setting the texts.
        alertDialogBuilder.setTitle(resources.getString(R.string.nav_logout))
                .setMessage(resources.getString(R.string.logout_confirmation))

        // Setting the button actions.
        alertDialogBuilder.setPositiveButton(R.string.logout_leave, { _, _ -> performLogout() })
                .setNegativeButton(R.string.logout_stay, { dialogInterface, _ -> dialogInterface.dismiss() })
                .setCancelable(true)

        // Creating the dialog.
        alertDialogBuilder.create()
        alertDialogBuilder.show()
    }

    private fun performLogout() {
        LoginManager.getInstance().logOut()

        if (!SharedPreferencesHelper.getStringFromSharedPreferences(this, SharedPreferencesHelper.USER_LOGGED).equals("")) {
            SharedPreferencesHelper.saveStringInSharedPreferences(this, SharedPreferencesHelper.USER_LOGGED, "NO")
        }

        startActivity(LoginActivity.getActivityIntent(this))
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    //endregion

    //region Inner Objects and Classes

    companion object {
        private val USER_ID = "user_id"

        fun getActivityIntent(context: Context) : Intent {
            val intent : Intent = Intent(context, MainActivity::class.java)
            return intent
        }
    }

    //endregion

}