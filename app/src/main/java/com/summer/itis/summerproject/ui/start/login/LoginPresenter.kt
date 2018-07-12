package com.summer.itis.summerproject.ui.start.login

import android.text.TextUtils
import android.util.Log
import android.widget.Toast

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.R.string.card
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.model.User
import com.summer.itis.summerproject.model.pojo.opensearch.Item
import com.summer.itis.summerproject.model.pojo.query.Page

import com.summer.itis.summerproject.repository.RepositoryProvider
import com.summer.itis.summerproject.repository.RepositoryProvider.Companion.abstractCardRepository
import com.summer.itis.summerproject.repository.RepositoryProvider.Companion.cardRepository
import com.summer.itis.summerproject.repository.RepositoryProvider.Companion.testRepository
import com.summer.itis.summerproject.repository.json.UserRepository
import com.summer.itis.summerproject.utils.ApplicationHelper
import com.summer.itis.summerproject.utils.Const.OFFICIAL_TYPE

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
//                        logView.goToProfile()

                        workWithDb()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })
            } else {
                logView.showError()
            }
        }

    fun workWithDb() {
        cardRepository?.readCard("-LHEW5YqlXQrV8quuLrd")?.subscribe { e ->
            val test: Test = e.test
            test.card = e
            ApplicationHelper.currentUser?.let {
                testRepository?.finishTest(test, it)?.subscribe { t ->
                    Log.d(TAG_LOG, "after first finish")
                    cardRepository!!.readCard("-LHEWO92FO6szrtwuU40").subscribe { r ->
                        Log.d(TAG_LOG, "after second readCard")
                        val test2: Test = r.test
                        test2.card = r
                        val userTwo: User = User()
                        userTwo.id = "Dm9nt1k1YxWnonZ2t3y9YUbjHUB2"
                        testRepository!!.finishTest(test2, userTwo).subscribe { k ->
                            Log.d(TAG_LOG, "after second finish")
                            cardRepository?.addCardAfterGame(r.id!!, it.id!!, userTwo.id!!)?.subscribe { m ->
                                Log.d(TAG_LOG, "after game")
                                findMyTests()
                            }

                        }

                    }

                }
            }
        }

    }

    fun findMyTests() {
        ApplicationHelper.currentUser?.id?.let {
            testRepository?.findMyTests(it)?.subscribe { e ->
                Log.d(TAG_LOG, "myTests")
                for (test in e) {
                    Log.d(TAG_LOG, "author = " + test.authorName)
                    Log.d(TAG_LOG, "test title" + test.title)
                }
                this.findTestsByType()

            }
        }
    }

    fun findTestsByType() {
        ApplicationHelper.currentUser?.id?.let {
            testRepository?.findTestsByType(it, OFFICIAL_TYPE)?.subscribe { e ->
                Log.d(TAG_LOG, "official_tests")
                for (test in e) {
                    Log.d(TAG_LOG, "author = " + test.authorName)
                    Log.d(TAG_LOG, "test title" + test.title)
                }
                this.findOfficialMyCards()
            }
        }
    }

    fun findOfficialMyCards() {
        ApplicationHelper.currentUser?.id?.let {
            cardRepository?.findOfficialMyCards(it)?.subscribe { e ->
                Log.d(TAG_LOG, "official_cards")
                for (card in e) {
                    Log.d(TAG_LOG, "card intelligence = " + card.intelligence)
                    Log.d(TAG_LOG, "card tyoe" + card.type)
                }
                this.findMyCards()
            }
        }

    }

    fun findMyCards() {
        ApplicationHelper.currentUser?.id?.let {
            cardRepository?.findMyCards(it)?.subscribe { e ->
                Log.d(TAG_LOG, "user_cards")
                for (card in e) {
                    Log.d(TAG_LOG, "card intelligence = " + card.intelligence)
                    Log.d(TAG_LOG, "card tyoe" + card.type)
                }
                this.findDefaultAbstractCardStates()
            }
        }
    }

    fun findDefaultAbstractCardStates() {
        cardRepository?.findDefaultAbstractCardStates("-LHEWO92FO6szrtwuU4-")?.subscribe { e ->
            Log.d(TAG_LOG, "default_card_states")
            for (card in e) {
                Log.d(TAG_LOG, "card intelligence = " + card.intelligence)
                Log.d(TAG_LOG, "card tyoe" + card.type)
            }
            this.findMyAbstractCardStates()
        }

    }

    fun findMyAbstractCardStates() {
        ApplicationHelper.currentUser?.id?.let {
            cardRepository?.findMyAbstractCardStates("-LHEWO92FO6szrtwuU4-", it)?.subscribe { e ->
            Log.d(TAG_LOG, "my_card_states")
            for (card in e) {
                Log.d(TAG_LOG, "card intelligence = " + card.intelligence)
                Log.d(TAG_LOG, "card tyoe" + card.type)
            }
            this.findDefaultAbstractCardTests()
        }
        }
    }

    fun findDefaultAbstractCardTests() {
        cardRepository?.findDefaultAbstractCardTests("-LHEWO92FO6szrtwuU4-")?.subscribe { e ->
            Log.d(TAG_LOG, "default_card_tests")
            for (test in e) {
                Log.d(TAG_LOG, "test title = " + test.title)
                Log.d(TAG_LOG, "test type" + test.type)
            }
            this.findMyAbstractCardTests()
        }

    }

    fun findMyAbstractCardTests() {
        ApplicationHelper.currentUser?.id?.let {
            cardRepository?.findMyAbstractCardTests("-LHEWO92FO6szrtwuU4-", it)?.subscribe { e ->
            Log.d(TAG_LOG, "user_card_tests")
            for (test in e) {
                Log.d(TAG_LOG, "test title = " + test.title)
                Log.d(TAG_LOG, "test type" + test.type)
            }
            this.findDefaultAbstractCards()
        }
        }
    }

    fun findDefaultAbstractCards() {
        ApplicationHelper.currentUser?.id?.let {
            abstractCardRepository.findDefaultAbstractCards(it).subscribe { e ->
                Log.d(TAG_LOG, "default_abstract_cards")
                for (card in e) {
                    Log.d(TAG_LOG, "card name = " + card.name)
                    Log.d(TAG_LOG, "card wiki" + card.wikiUrl)
                }
                this.findMyAbstractCards()
            }
        }

    }

    fun findMyAbstractCards() {
        ApplicationHelper.currentUser?.id?.let {
            abstractCardRepository.findMyAbstractCards(it).subscribe { e ->
                Log.d(TAG_LOG, "my_abstract_cards")
                for (card in e) {
                    Log.d(TAG_LOG, "card name = " + card.name)
                    Log.d(TAG_LOG, "card wiki" + card.wikiUrl)
                }
            }
        }

    }

}
