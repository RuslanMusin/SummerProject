package com.summer.itis.summerproject.ui.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.ui.game.FindGameActivity
import com.summer.itis.summerproject.ui.member.member_item.PersonalActivity
import com.summer.itis.summerproject.ui.member.member_list.reader.ReaderListActivity
import com.summer.itis.summerproject.ui.start.login.LoginActivity
import com.summer.itis.summerproject.ui.tests.add_test.AddTestActivity
import com.summer.itis.summerproject.utils.ApplicationHelper
import com.summer.itis.summerproject.utils.Const.TAG_LOG
import java.util.*

//АКТИВИТИ РОДИТЕЛЬ ДЛЯ ОСНОВНОЙ НАВИГАЦИИ(БОКОВОЙ). ЮЗАТЬ МЕТОДЫ supportActionBar И setBackArrow(ЕСЛИ НУЖНА СТРЕЛКА НАЗАД)
open class NavigationBaseActivity : MvpAppCompatActivity() {

    protected lateinit var mDrawer: DrawerLayout
    protected lateinit var mNavigationView: NavigationView
    var progressDialog: ProgressDialog? = null
    protected lateinit var headerImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        mDrawer = findViewById(R.id.drawer_layout)
        mNavigationView = findViewById(R.id.nav_view)
    }

    override fun onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    protected fun supportActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        Objects.requireNonNull<ActionBar>(supportActionBar).setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        initNavigationDrawer(toolbar)
    }

    protected fun setBackArrow(toolbar: Toolbar) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            toolbar.setNavigationOnClickListener { v -> onBackPressed() }
        }
    }

    private fun initNavigationDrawer(toolbar: Toolbar) {
        mNavigationView.setNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId
            when (id) {
                R.id.menu_tests -> AddTestActivity.start(this)

                R.id.menu_cards -> LoginActivity.start(this)

                R.id.menu_game -> FindGameActivity.start(this)

                R.id.menu_friends -> ReaderListActivity.start(this)

                R.id.menu_settings -> LoginActivity.start(this)

                R.id.menu_logout -> {
                    FirebaseAuth.getInstance().signOut()
                    LoginActivity.start(this)
                }
            }
            true
        }

        val header = mNavigationView.getHeaderView(0)
        headerImage = header.findViewById(R.id.iv_crossing)
        ApplicationHelper.loadUserPhoto(headerImage)

        headerImage.setOnClickListener { PersonalActivity.start(this@NavigationBaseActivity) }

        setActionBar(toolbar)
    }

    private fun setActionBar(toolbar: Toolbar) {
        Log.d(TAG_LOG, "set action bar")
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, mDrawer, toolbar,
                R.string.drawer_open, R.string.drawer_close)
        mDrawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

    @JvmOverloads
    fun showProgress(message: Int = R.string.loading) {
        hideProgress()
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage(getString(message))
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    fun hideProgress() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
            progressDialog = null
        }
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showSnackBar(message: String) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    fun showSnackBar(messageId: Int) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content),
                messageId, Snackbar.LENGTH_LONG)
        snackbar.show()
    }


    fun showWarningDialog(messageId: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(messageId)
        builder.setPositiveButton(R.string.button_ok, null)
        builder.show()
    }

    fun showWarningDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.button_ok, null)
        builder.show()
    }

    fun hasInternetConnection(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun checkInternetConnection(): Boolean {
        val hasInternetConnection = hasInternetConnection()
        if (!hasInternetConnection) {
            showWarningDialog(R.string.internet_connection_failed)
        }

        return hasInternetConnection
    }
}
