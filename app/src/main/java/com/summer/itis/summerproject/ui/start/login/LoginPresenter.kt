package com.summer.itis.summerproject.ui.start.login

import android.text.TextUtils
import android.util.Log
import android.widget.Toast

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.model.User
import com.summer.itis.summerproject.model.pojo.opensearch.Item
import com.summer.itis.summerproject.model.pojo.query.Page

import com.summer.itis.summerproject.repository.RepositoryProvider
import com.summer.itis.summerproject.repository.json.UserRepository
import com.summer.itis.summerproject.utils.ApplicationHelper

import com.summer.itis.summerproject.utils.Const.TAG_LOG
import com.summer.itis.summerproject.utils.RxUtils

class LoginPresenter(private val logView: LoginActivity) {

    fun setList(itemList: List<Item>) {
        for (item in itemList) {
            Log.d(TAG_LOG, "text " + item.text?.content);
            Log.d(TAG_LOG, "desc " + item.description?.content);
            Log.d(TAG_LOG, "url " + item.url?.content);
        }
    }

    fun setPages(itemList: List<Page>) {
        val sep: String = "-----------";
        Log.d(TAG_LOG, sep);
        for (item in itemList) {
            Log.d(TAG_LOG, "title " + item.title);
            Log.d(TAG_LOG, "desc " + item.description);
            Log.d(TAG_LOG, "extract " + item.extract?.content);
            Log.d(TAG_LOG, "url " + item.original?.source);
        }
        Log.d(TAG_LOG, sep);
    }

    fun setTest(test: Test) {
        Log.d(TAG_LOG, "title " + test.title);
        Log.d(TAG_LOG, "desc " + test.authorId);
        Log.d(TAG_LOG, "extract " + test.authorName);
    }

    fun readCard(card: Card) {
        Log.d(TAG_LOG, "cardId " + card.cardId);
        Log.d(TAG_LOG, "testId " + card.testId);
        Log.d(TAG_LOG, "intelligence " + card.intelligence);

        val abstractCard = card.abstractCard
        Log.d(TAG_LOG, "wikiUrl " + abstractCard?.wikiUrl);
        Log.d(TAG_LOG, "desc " + abstractCard?.description);
        Log.d(TAG_LOG, "name " + abstractCard?.name);

        val test = card.test
        Log.d(TAG_LOG, "test author " + test.authorName);
        Log.d(TAG_LOG, "test title " + test.title);
        val question  = test.questions[0]
        Log.d(TAG_LOG, "question " + question.question);
        for(answer in question.answers){
            Log.d(TAG_LOG, "answer = " + answer.text);
        }


    }

    fun signIn(email: String, password: String) {
       /* val sep: String = "-----------";
        RepositoryProvider.wikiApiRepository.opensearch("Лев Толстой").subscribe(this::setList);
        RepositoryProvider.wikiApiRepository.query("Толстой, Лев Николаевич").subscribe(this::setPages);*/

//        RepositoryProvider.testRepository?.readTest("-LH8d3j8GpNDVem9pWRo")?.subscribe(this::setTest)
      /*  RepositoryProvider.cardRepository
                ?.readCard("-LH9PIX0TlQ-h33CymC9")
                ?.subscribe(this::readCard)*/

        Log.d(TAG_LOG, "signIn:$email")
        if (!validateForm(email, password)) {
            return
        }

        logView.showProgressDialog()

        logView.fireAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(logView) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG_LOG, "signInWithEmail:success")
                        val user = logView.fireAuth!!.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG_LOG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(logView, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }

                    logView.hideProgressDialog()
                }
    }
        fun validateForm(email: String, password: String): Boolean {
            var valid = true

            if (TextUtils.isEmpty(email)) {
                logView.tiUsername!!.error = logView.getString(R.string.enter_correct_name)
                valid = false
            } else {
                logView.tiUsername!!.error = null
            }

            if (TextUtils.isEmpty(password)) {
                logView.tiPassword!!.error = logView.getString(R.string.enter_correct_password)
                valid = false
            } else {
                logView.tiPassword!!.error = null
            }

            return valid
        }

        fun updateUI(firebaseUser: FirebaseUser?) {
            logView.hideProgressDialog()
            if (firebaseUser != null) {
                val reference = RepositoryProvider.userRepository?.readUser(UserRepository.currentId)
                reference?.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val user = dataSnapshot.getValue(User::class.java)
                        ApplicationHelper.currentUser = user
                        logView.goToProfile()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })
            } else {
                logView.showError()
            }
        }
}
