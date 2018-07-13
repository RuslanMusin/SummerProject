package com.summer.itis.summerproject.ui.tests.test_item.fragments.main

import QuestionFragment.Companion.QUESTION_NUMBER
import QuestionFragment.Companion.RIGHT_ANSWERS
import android.app.Activity
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Comment
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.model.User
import com.summer.itis.summerproject.repository.RepositoryProvider.Companion.userRepository
import com.summer.itis.summerproject.ui.base.NavigationBaseActivity
import com.summer.itis.summerproject.ui.comment.CommentAdapter
import com.summer.itis.summerproject.ui.comment.OnCommentClickListener
import com.summer.itis.summerproject.ui.member.member_item.PersonalActivity
import com.summer.itis.summerproject.ui.tests.add_test.AddTestView
import com.summer.itis.summerproject.ui.tests.add_test.fragments.question.AddQuestionFragment
import com.summer.itis.summerproject.ui.tests.test_item.TestActivity.Companion.TEST_JSON
import com.summer.itis.summerproject.ui.widget.CircularImageView
import com.summer.itis.summerproject.ui.widget.EmptyStateRecyclerView
import com.summer.itis.summerproject.ui.widget.ExpandableTextView
import com.summer.itis.summerproject.utils.ApplicationHelper

import com.summer.itis.summerproject.utils.Const.TAG_LOG
import com.summer.itis.summerproject.utils.Const.gsonConverter
import com.summer.itis.summerproject.utils.ImageLoadHelper
import kotlinx.android.synthetic.main.activity_play_game.view.*
import kotlinx.android.synthetic.main.layout_test.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TestFragment : Fragment(), View.OnClickListener, OnCommentClickListener, TestFragmentView {

    private lateinit var commentEditText: EditText

    internal var myFormat = "dd.MM.yyyy" //In which you need put here
    internal var sdf = SimpleDateFormat(myFormat, Locale.getDefault())

    private var recyclerView: EmptyStateRecyclerView? = null
    private var adapter: CommentAdapter? = null

    private var comments: MutableList<Comment> = ArrayList()

    private var addTestView: AddTestView? = null
    lateinit var test: Test

    @InjectPresenter
    lateinit var presenter: TestFragmentPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_test, container, false)
        addTestView = activity as AddTestView?

        val testStr: String? = savedInstanceState?.getString(TEST_JSON)
        test = gsonConverter.fromJson(testStr,Test::class.java)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        initRecycler()
        setListeners()
        test.id?.let { presenter.loadComments(it) }

        if(test.testDone == false) {
            tv_done.text = getText(R.string.test_wasnt_done)
        } else {
            tv_done.text = getText(R.string.test_was_done)
        }

        tv_author.text = test.authorName
        (extv_desc as ExpandableTextView).text = test.desc
        nameEditText.text = test.title
        test.imageUrl?.let { ImageLoadHelper.loadPicture(iv_crossing as ImageView, it) }


        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews(view: View) {

        commentEditText = view.findViewById<View>(R.id.commentEditText) as EditText

        val sendButton = view.findViewById<View>(R.id.sendButton) as Button

        commentEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                Log.d(TAG_LOG, "char length = " + charSequence.length)
                sendButton.isEnabled = charSequence.toString().trim { it <= ' ' }.length > 0
                Log.d(TAG_LOG, "enabled = " + sendButton.isEnabled)
            }

            override fun afterTextChanged(editable: Editable) {
                val charSequence = editable.toString()
                Log.d(TAG_LOG, "after char length = " + charSequence.length)
                sendButton.isEnabled = charSequence.trim { it <= ' ' }.length > 0
                Log.d(TAG_LOG, "enabled = " + sendButton.isEnabled)
            }
        })

        sendButton.setOnClickListener {
            if ((activity as NavigationBaseActivity).hasInternetConnection()) {
                sendComment()
            } else {
                (activity as NavigationBaseActivity).showSnackBar(R.string.internet_connection_failed)
            }
        }
    }

    private fun setListeners() {
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_do_test -> {

                val args: Bundle = Bundle()
                args.putString(TEST_JSON, gsonConverter.toJson(test))
                args.putInt(QUESTION_NUMBER,0)
                args.putInt(RIGHT_ANSWERS,0)

                activity!!.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, QuestionFragment.newInstance(args))
                        .addToBackStack("AddQuestionFragment")
                        .commit()

            }
        }
    }

    override fun onReplyClick(position: Int) {
        commentEditText.isEnabled = true
        val comment = comments.get(position)
        val commentString = comment.authorName + ", "
        commentEditText.setText(commentString)
    }

    override fun onAuthorClick(authorId: String) {
        val reference = userRepository.readUser(authorId)
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                user?.let { PersonalActivity.start(activity as Activity, it) }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    override fun setComments(comments: List<Comment>) {
        this.comments = comments.toMutableList()
        adapter?.changeDataSet(comments)
    }


    private fun sendComment() {
        val commentText = commentEditText.getText().toString()
        Log.d(TAG_LOG, "send comment = $commentText")
        if (commentText.length > 0) {
            val comment = Comment()
            val user = ApplicationHelper.currentUser
            user?.let {
                comment.text = commentText
                comment.authorId = user.id
                comment.authorName = user.username
                comment.authorPhotoUrl = user.photoUrl
                comment.createdDate = (Date().time)
                test.id?.let { it1 -> presenter.createComment(it1, comment) }
            }

            commentEditText.setText(null)
            commentEditText.clearFocus()
        }
    }

    override fun addComment(comment: Comment) {
        comments.add(comment)
        adapter?.changeDataSet(comments)
    }

    override fun showComments(comments: List<Comment>) {
    }


    override fun onItemClick(item: Comment) {
    }

    private fun initRecycler() {
        adapter = CommentAdapter(ArrayList(), this)
        adapter?.let {
            val manager = LinearLayoutManager(this.activity)
            recyclerView?.let {
                recyclerView?.setLayoutManager(manager)
                adapter?.attachToRecyclerView(it)
            }
            adapter?.setOnItemClickListener(this)
            recyclerView?.setAdapter(adapter)
        }


    }

    companion object {

        private val RESULT_LOAD_IMG = 0
        private val ADD_CARD = 1

        fun newInstance(args: Bundle): Fragment {
            val fragment = TestFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
