package pineapplesoftware.pineappleapp.account

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import pineapplesoftware.pineappleapp.R
import android.view.WindowManager
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_onboarding.*
import pineapplesoftware.pineappleapp.helper.SharedPreferencesHelper
import pineapplesoftware.pineappleapp.helper.UserCredentialsHelper
import pineapplesoftware.pineappleapp.main.MainActivity

class OnboardingActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    //region Attributes

    private var mLayouts = intArrayOf()

    companion object {
        var instance : OnboardingActivity? = null
    }

    //endregion

    //region Overridden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        if (SharedPreferencesHelper.getStringFromSharedPreferences(this, SharedPreferencesHelper.USER_LOGGED) != null && SharedPreferencesHelper.getStringFromSharedPreferences(this, SharedPreferencesHelper.USER_LOGGED).equals("YES")) {
            val userId = SharedPreferencesHelper.getStringFromSharedPreferences(this, SharedPreferencesHelper.USER_ID)
            val userName = SharedPreferencesHelper.getStringFromSharedPreferences(this, SharedPreferencesHelper.USER_NAME)
            val userEmail = SharedPreferencesHelper.getStringFromSharedPreferences(this, SharedPreferencesHelper.USER_EMAIL)

            if (userId != null && userName != null && userEmail != null) {
                startActivity(MainActivity.getActivityIntent(this, userId, userName, userEmail))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out);
                finish()
            }
        }

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        mLayouts = intArrayOf(R.layout.fragment_onboarding_page_one,
                R.layout.fragment_onboarding_page_two,
                R.layout.fragment_onboarding_page_three)

        prepareViews()
        addBottomDots(0)
        changeStatusBarColor()

        instance = this
    }

    override fun onPageScrollStateChanged(state: Int) { }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }

    override fun onPageSelected(position: Int) {
        addBottomDots(position)
    }

    //endregion

    //region Private Methods

    private fun prepareViews() {
        onboardingSignUpButton.setOnClickListener{ navigateToRegisterScreen() }
        onBoardingSignInButton.setOnClickListener { navigateToLoginScreen() }

        val onboardingViewPagerAdapter = OnboardingViewPagerAdapter()
        onboardingViewPager.adapter = onboardingViewPagerAdapter
        onboardingViewPager.addOnPageChangeListener(this)
    }

    private fun changeStatusBarColor() {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun addBottomDots(currentPage: Int) {
        val dots = arrayOfNulls<TextView>(mLayouts.size)

        onboardingDotsLinearLayout.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this)
            val linearLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            linearLayoutParams.setMargins(10, 0, 10, 0) // llp.setMargins(left, top, right, bottom);
            dots[i]?.layoutParams = linearLayoutParams

            val result: Spanned
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                result = Html.fromHtml("&#8226;", Html.FROM_HTML_MODE_LEGACY)
            } else {
                result = Html.fromHtml("&#8226;")
            }
            dots[i]?.text = result

            dots[i]?.textSize = 36f
            dots[i]?.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryExtraLight))
            onboardingDotsLinearLayout.addView(dots[i])
        }

        if (dots.isNotEmpty())
            dots[currentPage]?.setTextColor(ContextCompat.getColor(this, android.R.color.white))
    }

    private fun navigateToRegisterScreen() {

    }

    private fun navigateToLoginScreen() {
        val intent = LoginActivity.getActivityIntent(this)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out);
    }

    //endregion

    //region Inner Classes

    inner class OnboardingViewPagerAdapter : PagerAdapter() {

        private var layoutInflater : LayoutInflater? = null;

        override fun instantiateItem(container: ViewGroup?, position: Int): Any? {
            layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view = layoutInflater?.inflate(mLayouts[position], container, false)

            container?.addView(view)

            return view
        }

        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return mLayouts.size
        }

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            val view : View = `object` as View
            container?.removeView(view)
        }

    }

    //endregion
}
