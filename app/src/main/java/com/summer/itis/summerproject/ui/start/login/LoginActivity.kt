package com.summer.itis.summerproject.ui.start.login


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.GameOne
import com.summer.itis.summerproject.repository.json.OldGameRepository
import com.summer.itis.summerproject.ui.base.BaseActivity
import com.summer.itis.summerproject.ui.member.member_item.PersonalActivity
import com.summer.itis.summerproject.ui.start.registration.RegistrationActivity
import com.summer.itis.summerproject.utils.Const.TAG_LOG
import java.util.*

/**
 * Created by Ruslan on 18.02.2018.
 */

class LoginActivity : BaseActivity(), View.OnClickListener {

    private var enterBtn: Button? = null
    private var tvRegistration: TextView? = null
    var tiUsername: TextInputLayout? = null
    var tiPassword: TextInputLayout? = null
    var etUsername: EditText? = null
    var etPassword: EditText? = null

    //get-set

    var fireAuth: FirebaseAuth? = null

    private var presenter: LoginPresenter? = null

    private val gameId: String? = null

    private val gameRepository: OldGameRepository? = null

    private val gameOne: GameOne? = null

    private val gameTwo: GameOne? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkUserSession()


    }

    private fun checkUserSession() {
        fireAuth = FirebaseAuth.getInstance()

        /*FirebaseUser user = fireAuth.getCurrentUser();
        if(user != null) {
            DatabaseReference reference = RepositoryProvider.getUserRepository().readUser(user.getUid());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    ApplicationHelper.setCurrentUser(user);
                    goToBookList();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {*/
        initViews()

    }

    private fun initViews() {
        setContentView(R.layout.activity_login)

        enterBtn = findViewById(R.id.btn_enter)
        tvRegistration = findViewById(R.id.link_signup)

        enterBtn!!.setOnClickListener(this)
        tvRegistration!!.setOnClickListener(this)

        etUsername = findViewById(R.id.et_name)
        etPassword = findViewById(R.id.et_password)
        tiUsername = findViewById(R.id.ti_username)
        tiPassword = findViewById(R.id.ti_password)

        presenter = LoginPresenter(this)
        enterBtn!!.performClick()
    }

    override fun onClick(view: View) {

        when (view.id) {

            R.id.btn_enter -> {
                /*  gameRepository = new OldGameRepository();
                gameOne = new GameOne();
                gameOne.setCardId("nameCard1");
                gameOne.setScore(0);
                gameOne.setQustionId("question1");
                gameId = gameRepository.createGameOne(gameOne);*/
                /*String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();*/
                val username = "rust@ma.ru"
                val password = "rustamka"
                presenter!!.signIn(username, password)
            }

            R.id.link_signup -> goToRegistration()
        }
    }

    internal fun goToProfile() {
        PersonalActivity.start(this)
    }

    private fun goToRegistration() {
        /*   gameTwo = new GameOne();
        gameTwo.setCardId("nameCard2");
        gameTwo.setScore(0);
        gameTwo.setQustionId("question2");
        gameTwo.setEnemyId(gameOne.getId());
        gameRepository.createGameTwo(gameTwo,gameId,this);*/


        RegistrationActivity.start(this)
    }

    fun listen() {
        val query = gameRepository!!.readPoint(gameId)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val comments = ArrayList<GameOne>()
                for (postSnapshot in dataSnapshot.children) {
                    val point = postSnapshot.getValue(GameOne::class.java)
                    Log.d(TAG_LOG, point!!.id)
                    Log.d(TAG_LOG, point.qustionId)
                    Log.d(TAG_LOG, point.cardId)
                    Log.d(TAG_LOG, point.score.toString())

                    if (point.id != gameTwo!!.id) {
                        gameOne!!.enemyId = gameTwo.id
                        gameRepository.setEnemy(gameOne)
                        setQueries()
                    }
                    comments.add(point)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG_LOG, "loadPost:onCancelled", databaseError.toException())
            }
        })

    }

    private fun setQueries() {
        val queryOne = gameOne!!.enemyId?.let { gameRepository!!.readPoint(gameId).child(it) }
        queryOne?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val comments = ArrayList<GameOne>()
                val point = dataSnapshot.getValue(GameOne::class.java)
                Log.d(TAG_LOG, "one")
                Log.d(TAG_LOG, point!!.id)
                Log.d(TAG_LOG, point.qustionId)
                Log.d(TAG_LOG, point.cardId)
                Log.d(TAG_LOG, point.score.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG_LOG, "loadPost:onCancelled", databaseError.toException())
            }
        })

        val queryTwo = gameOne.enemyId?.let { gameRepository?.readPoint(gameId)?.child(it) }
        queryTwo?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val comments = ArrayList<GameOne>()
                val point = dataSnapshot.getValue(GameOne::class.java)
                Log.d(TAG_LOG, "two")
                Log.d(TAG_LOG, point!!.id)
                Log.d(TAG_LOG, point.qustionId)
                Log.d(TAG_LOG, point.cardId)
                Log.d(TAG_LOG, point.score.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG_LOG, "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    fun showError() {
        tiUsername!!.error = getString(R.string.enter_correct_name)
        tiPassword!!.error = getString(R.string.enter_correct_password)
    }

    companion object {

        fun start(activity: Context) {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            activity.startActivity(intent)
        }
    }
}
