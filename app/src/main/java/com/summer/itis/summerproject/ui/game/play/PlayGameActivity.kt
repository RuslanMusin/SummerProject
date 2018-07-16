package com.summer.itis.summerproject.ui.game.play

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.model.Question
import com.summer.itis.summerproject.ui.game.play.list.GameCardsListAdapter
import kotlinx.android.synthetic.main.activity_play_game.*
import kotlinx.android.synthetic.main.item_game_card_medium.view.*

class PlayGameActivity : MvpAppCompatActivity(), PlayGameView {

    @InjectPresenter
    lateinit var presenter: PlayGamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_game)

//        var _card = Card()
//        _card.abstractCard = AbstractCard()
//        _card.abstractCard!!.name = "Person Name"
//        _card.test = Test()
//        _card.test.title = "Test title"
//
//        enemy_selected_card.setOnClickListener {
//            showEnemyCardChoose(_card)
//        }

//        val layoutManager=LinearLayoutManager(this)
//        layoutManager.orientation=LinearLayoutManager.HORIZONTAL
//        rv_game_my_cards.layoutManager=layoutManager

//        rv_game_my_cards.adapter = GameCardsListAdapter(
//                getTestCards(),
//                this,
//                {
//                    showYouCardChoose(it)
//                }
//        )

        enemy_selected_card.visibility = View.INVISIBLE
        my_selected_card.visibility = View.INVISIBLE


    }

    override fun setCardsList(cards: ArrayList<Card>) {
        rv_game_my_cards.adapter = GameCardsListAdapter(
                cards,
                this,
                {
                    if (choosingEnabled) {
                        presenter.chooseCard(it)
                    }
//                    showYouCardChoose(it)
                }
        )
    }

    var choosingEnabled = false

    override fun setCardChooseEnabled(enabled: Boolean) {
        choosingEnabled = enabled
//        rv_game_my_cards.isClickable = enabled
//        rv_game_my_cards.touc

    }

//    fun getTestCards(): ArrayList<Card> {
//        var _card = Card()
//        _card.abstractCard = AbstractCard()
//        _card.abstractCard!!.name = "Person Name"
//        _card.test = Test()
//        _card.test.title = "Test title"
//
//        return arrayListOf(_card, _card, _card)
//    }

    override fun showEnemyCardChoose(card: Card) {
        setCard(enemy_selected_card, card)
        enemy_selected_card.visibility = View.VISIBLE

        val a_anim = AlphaAnimation(0f, 1f)
        a_anim.duration = 700;
        a_anim.fillAfter = true
        enemy_selected_card.startAnimation(a_anim)

//        val m_anim = object : Animation() {
//            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
////                super.applyTransformation(interpolatedTime, t)
//                val params=enemy_selected_card.layoutParams as ConstraintLayout.LayoutParams
//                params.
//            }
//        }
    }

    override fun showQuestionForYou(question: Question) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showYouCardChoose(choose: Card) {
        setCard(my_selected_card, choose)
        my_selected_card.visibility = View.VISIBLE

        val a_anim = AlphaAnimation(0f, 1f)
        a_anim.duration = 200;
        a_anim.fillAfter = true
        my_selected_card.startAnimation(a_anim)
    }

    override fun showEnemyAnswer(correct: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setCard(view: View, card: Card) {
        view.tv_card_person_name.text = card.abstractCard!!.name
        view.tv_card_test_name.text = card.test.title

    }

//
//    @InjectPresenter
//    lateinit var presenter: PlayGamePresenter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_play_game)
//
//        btn_choose_random_card.setOnClickListener {
//            RepositoryProvider.cardRepository.findMyCards(UserRepository.currentId).subscribe { list ->
//
//                presenter.chooseCard(list.getRandom()!!)
//            }
//        }
//
//        btn_answer_true.setOnClickListener {
//            presenter.answer(true)
//        }
//
//        btn_answer_false.setOnClickListener {
//            presenter.answer(false)
//        }
//    }
//
//    override fun showEnemyCardChoose(card: Card) {
//        tv_enemy_chooses.text = tv_enemy_chooses.text.toString() + "\r\n" + card.id
//    }
//
//    override fun showQuestionForYou(question: Question) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun showYouCardChoose(choose: Card) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun showEnemyAnswer(correct: Boolean) {
//        tv_enenmy_answers.text = tv_enenmy_answers.text.toString() + "\r\n" + correct
//    }


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, PlayGameActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
    }
}
