package pineapplesoftware.pineappleapp.main

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.text.Spannable
import android.text.SpannableString
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.app_bar_main.view.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.toolbar_main.view.*

import pineapplesoftware.pineappleapp.R
import pineapplesoftware.pineappleapp.account.LoginActivity
import pineapplesoftware.pineappleapp.application.PineappleApplication
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
    private val CURRENT_TAG : String = TAG_MAIN

    //endregion

    //region Overridden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PineappleApplication(this)

        setSupportActionBar(mainToolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        mainToolbarLayout.userProfilePicture?.profileId = mUserId

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
                Log.d(TAG, "Selected HOME")
                return true
            }
            R.id.nav_contact -> {
                Log.d(TAG, "Selected CONTACT")
                return true
            }
            R.id.nav_logout -> {
                Log.d(TAG, "Selected LOGOUT")
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
        val openSansFontRegular : Typeface = Typeface.createFromAsset(applicationContext.assets, "fonts/OpenSans-Regular.ttf")
        mainToolbar.mainToolbarLayout.toolbarTitle.typeface = openSansFontRegular

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
        val openSansFontRegular : Typeface = Typeface.createFromAsset(applicationContext.assets, "fonts/OpenSans-Regular.ttf")
        val newTitle : SpannableString = SpannableString(menuItem.title)
        newTitle.setSpan(CustomTypefaceSpan("", openSansFontRegular), 0, newTitle.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        menuItem.title = newTitle
    }

    private fun loadNavHeader() {
        // Setting up the basic user information
        mainNavigationView.getHeaderView(0).userName.text = "Higor Ernandes"
        mainNavigationView.getHeaderView(0).userWebsite.text = "www.ismycomputeron.com"

        val openSansFontRegular : Typeface = Typeface.createFromAsset(applicationContext.assets, "fonts/OpenSans-Regular.ttf")
        mainNavigationView.getHeaderView(0).userName.typeface = openSansFontRegular
        mainNavigationView.getHeaderView(0).userWebsite.typeface = openSansFontRegular

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

        val pendingRunnable : Runnable = Runnable {
            kotlin.run {
                val fragment : Fragment = getHomeFragment()
                val fragmentTransaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                fragmentTransaction.replace(R.id.mainFrameLayout, fragment, CURRENT_TAG)
                fragmentTransaction.commitAllowingStateLoss()
            }
        }

        mHandler?.post(pendingRunnable)


        toggleFab()
        mainDrawerLayout.closeDrawers()
        invalidateOptionsMenu()
    }

    private fun selectNavMenuItem() {
        mainNavigationView.menu.getItem(mNavItemIndex).isChecked = true
    }

    private fun getHomeFragment() : android.support.v4.app.Fragment {
        when (mNavItemIndex) {
            0 -> {
                val mainFragment : MainFragment = MainFragment()
                return mainFragment
            }
            else -> {
                return MainFragment()
            }
        }
    }

    private fun setUpNavigationView() {
        mainNavigationView.setNavigationItemSelectedListener(this)

        val actionBarDrawerToggle : ActionBarDrawerToggle = ActionBarDrawerToggle(this, mainDrawerLayout, mainToolbar, R.string.open_drawer, R.string.close_drawer)
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