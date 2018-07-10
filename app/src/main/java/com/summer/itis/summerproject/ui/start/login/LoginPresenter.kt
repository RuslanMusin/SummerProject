package com.summer.itis.summerproject.ui.start.login

import android.text.TextUtils
import android.util.Log
import android.widget.Toast

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.User

import com.summer.itis.summerproject.repository.RepositoryProvider
import com.summer.itis.summerproject.repository.json.UserRepository
import com.summer.itis.summerproject.utils.ApplicationHelper

import com.summer.itis.summerproject.utils.Const.TAG_LOG

class LoginPresenter(private val logView: LoginActivity) {

    /*private void setList(List<Item> itemList) {
        for(Item item : itemList) {
            Log.d(TAG_LOG,"text " + item.getText().getContent());
            Log.d(TAG_LOG,"desc " + item.getDescription().getContent());
            Log.d(TAG_LOG,"url " + item.getUrl().getContent());
        }
    }

    private void setPages(List<Page> itemList) {
        String sep = "-----------";
        Log.d(TAG_LOG,sep);
        for(Page item : itemList) {
            Log.d(TAG_LOG,"title " + item.getTitle());
            Log.d(TAG_LOG,"desc " + item.getDescription());
            Log.d(TAG_LOG,"extract " + item.getExtract().getContent());
            Log.d(TAG_LOG,"url " + item.getOriginal().getSource());
        }
        Log.d(TAG_LOG,sep);
    }*/

    fun signIn(email: String, password: String) {
        /*  String sep = "-----------";
        RepositoryProvider.getWikiApiRepository().opensearch("Лев Толстой").subscribe(this::setList);
        RepositoryProvider.getWikiApiRepository().query("Толстой, Лев Николаевич").subscribe(this::setPages);
*/

        Log.d(TAG_LOG, "signIn:$email")
        if (!validateForm()) {
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

    fun validateForm(): Boolean {
        var valid = true

        val email = logView.etUsername!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            logView.tiUsername!!.error = logView.getString(R.string.enter_correct_name)
            valid = false
        } else {
            logView.tiUsername!!.error = null
        }

        val password = logView.etPassword!!.text.toString()
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
            val reference = RepositoryProvider.userRepository?.readUser(UserRepository.getCurrentId())
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
