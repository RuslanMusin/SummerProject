package com.summer.itis.summerproject.ui.member.member_item

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide

import com.google.gson.Gson
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.User
import com.summer.itis.summerproject.repository.json.UserRepository
import com.summer.itis.summerproject.ui.base.NavigationBaseActivity
import com.summer.itis.summerproject.utils.ApplicationHelper

import com.summer.itis.summerproject.utils.Const.ADD_FRIEND
import com.summer.itis.summerproject.utils.Const.ADD_REQUEST
import com.summer.itis.summerproject.utils.Const.OWNER_TYPE
import com.summer.itis.summerproject.utils.Const.REMOVE_FRIEND
import com.summer.itis.summerproject.utils.Const.REMOVE_REQUEST
import com.summer.itis.summerproject.utils.Const.STUB_PATH
import com.summer.itis.summerproject.utils.Const.TAG_LOG
import com.summer.itis.summerproject.utils.Const.USER_KEY
import kotlinx.android.synthetic.main.layout_personal.*


class PersonalActivity : NavigationBaseActivity(), View.OnClickListener {

    private var toolbar: Toolbar? = null
    private var tvTests: TextView? = null
    private val tvCards: TextView? = null
    private var tvName: TextView? = null
    private var btnAddFriend: AppCompatButton? = null
    private var ivPhoto: ImageView? = null

    var user: User? = null
    //SET-GET


    var type: String? = null

    private var presenter: PersonalPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contentFrameLayout = findViewById<FrameLayout>(R.id.container)
        layoutInflater.inflate(R.layout.activity_profile, contentFrameLayout)

        presenter = PersonalPresenter(this)

        val userJson = intent.getStringExtra(USER_KEY)
        presenter!!.setUserRelationAndView(userJson)


    }

    internal fun initViews() {
        Log.d(TAG_LOG, "type = " + type!!)
        findViews()
        supportActionBar(toolbar!!)

        btnAddFriend!!.setOnClickListener(this)
        tvTests!!.setOnClickListener(this)
    }

    private fun findViews() {
        toolbar = findViewById(R.id.toolbar)
        tvTests = findViewById(R.id.tv_crossings)
        ivPhoto = findViewById(R.id.iv_crossing)

        btnAddFriend = findViewById(R.id.btn_add_friend)
        tvName = findViewById(R.id.nameEditText)

        if (type == OWNER_TYPE) {
            user = ApplicationHelper.currentUser
            setUserData()
        } else {
            setUserData()
        }
    }

    private fun setUserData() {
        tvName!!.text = user!!.username

        val path = user!!.photoUrl
        if (!path.equals(STUB_PATH)) {
            val imageReference = user!!.photoUrl?.let { ApplicationHelper.storageReference.child(it) }

            Log.d(TAG_LOG, "name " + (imageReference?.path ?: ""))

            Glide.with(this)
                    .load(imageReference)
                    .into(ivPhoto!!)
        }

        when (type) {
            ADD_FRIEND -> btnAddFriend!!.setText(R.string.add_friend)

            ADD_REQUEST -> btnAddFriend!!.setText(R.string.add_friend)

            REMOVE_FRIEND -> btnAddFriend!!.setText(R.string.remove_friend)

            REMOVE_REQUEST -> btnAddFriend!!.setText(R.string.remove_request)

            OWNER_TYPE -> btnAddFriend!!.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
        /*case R.id.btn_change_data:
                changeData();
                break;*/

            R.id.btn_add_friend -> actWithUser()

            R.id.tv_crossings -> showCrossings()
        }
    }

    private fun showCrossings() {
        val myUser = User()
        /* if(type.equals(OWNER_TYPE)) {
            myUser.setId(UserRepository.getCurrentId());
            CrossingListActivity.start(this,myUser);
        } else {
            CrossingListActivity.start(this,user);
        }*/
    }

    private fun actWithUser() {
        when (type) {
            ADD_FRIEND -> {
                user!!.id?.let { UserRepository().addFriend(UserRepository.currentId, it) }
                type = REMOVE_FRIEND
                btnAddFriend!!.setText(R.string.remove_friend)
            }

            ADD_REQUEST -> {
                user!!.id?.let { UserRepository().addFriendRequest(UserRepository.currentId, it) }
                type = REMOVE_REQUEST
                btnAddFriend!!.setText(R.string.remove_request)
            }

            REMOVE_FRIEND -> {
                user!!.id?.let { UserRepository().removeFriend(UserRepository.currentId, it) }
                type = ADD_FRIEND
                btnAddFriend!!.setText(R.string.add_friend)
            }

            REMOVE_REQUEST -> {
                user!!.id?.let { UserRepository().removeFriendRequest(UserRepository.currentId, it) }
                type = ADD_REQUEST
                btnAddFriend!!.setText(R.string.add_friend)
            }
        }
    }

    private fun changeData() {
        //        startActivity(ChangeUserDataActivity.makeIntent(TestActivity.this));
    }

    companion object {

        fun start(activity: Activity, comics: User) {
            val intent = Intent(activity, PersonalActivity::class.java)
            val gson = Gson()
            val userJson = gson.toJson(comics)
            intent.putExtra(USER_KEY, userJson)
            activity.startActivity(intent)
        }

        fun start(activity: Activity) {
            val intent = Intent(activity, PersonalActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            activity.startActivity(intent)
        }
    }
}
