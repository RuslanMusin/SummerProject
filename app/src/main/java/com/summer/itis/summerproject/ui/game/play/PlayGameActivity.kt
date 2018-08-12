package com.summer.itis.summerproject.ui.game.play

import GameQuestionFragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import com.afollestad.materialdialogs.GravityEnum
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.R.id.rv_game_my_cards
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.model.Question
import com.summer.itis.summerproject.model.User
import com.summer.itis.summerproject.repository.json.GamesRepository
import com.summer.itis.summerproject.ui.game.find.FindGameActivity
import com.summer.itis.summerproject.ui.game.play.change_list.GameChangeListAdapter
import com.summer.itis.summerproject.ui.game.play.list.GameCardsListAdapter
import com.summer.itis.summerproject.ui.widget.CenterZoomLayoutManager
import com.summer.itis.summerproject.utils.Const.TAG_LOG
import kotlinx.android.synthetic.main.activity_play_game.*
import kotlinx.android.synthetic.main.dialog_end_game.view.*
import kotlinx.android.synthetic.main.item_game_card_medium.view.*


class PlayGameActivity : MvpAppCompatActivity(), PlayGameView {

    var mode: String = MODE_PLAY_GAME

    @InjectPresenter
    lateinit var presenter: PlayGamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_game)

        enemy_selected_card.visibility = View.INVISIBLE
        my_selected_card.visibility = View.INVISIBLE

        game_questions_container.visibility = View.GONE

        rv_game_my_cards.layoutManager = CenterZoomLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rv_game_start_cards.layoutManager = CenterZoomLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

    }

    override fun onBackPressed() {
        if(mode.equals(MODE_CHANGE_CARDS)) {
            mode = MODE_PLAY_GAME
            stopChange()
        } else {
            super.onBackPressed()
        }
    }

    fun stopChange(): () -> Unit {
        return {
            presenter.setCardList((rv_game_start_cards.adapter as GameChangeListAdapter).getItems() as ArrayList<Card>)

        }
    }

    override fun setEnemyUserData(user: User) {
        tv_game_enemy_name.text = user.username

        //TODO image, но еще нет Url в БД
    }

    override fun changeCards(cards: MutableList<Card>, mutCards: MutableList<Card>) {
        Log.d(TAG_LOG,"changeCards")
        mode = MODE_CHANGE_CARDS
        rv_game_my_cards.visibility = View.INVISIBLE
        rv_game_start_cards.adapter = GameChangeListAdapter(cards,mutCards,mutCards.size,stopChange())
    }

    override fun setCardsList(cards: ArrayList<Card>) {
        rv_game_start_cards.visibility = View.GONE
        rv_game_my_cards.visibility = View.VISIBLE
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
        if (enabled) {
            rv_game_my_cards.alpha = 1f
        } else {
            rv_game_my_cards.alpha = 0.5f
        }
//        rv_game_my_cards.isClickable = enabled
//        rv_game_my_cards.touc

    }

    fun onAnswer(correct: Boolean) {
        presenter.answer(correct)
    }

    override fun showEnemyCardChoose(card: Card) {
        setCard(enemy_selected_card, card)
        enemy_selected_card.visibility = View.VISIBLE

        val a_anim = AlphaAnimation(0f, 1f)
        a_anim.duration = 700;
        a_anim.fillAfter = true
        enemy_selected_card.startAnimation(a_anim)

        //TODO?
//        val m_anim = object : Animation() {
//            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
////                super.applyTransformation(interpolatedTime, t)
//                val params=enemy_selected_card.layoutParams as ConstraintLayout.LayoutParams
//                params.
//            }
//        }
    }

    override fun showQuestionForYou(question: Question) {
        game_questions_container.visibility = View.VISIBLE

        supportFragmentManager
                .beginTransaction()
                .replace(
                        R.id.game_questions_container,
                        GameQuestionFragment.newInstance(question)
                )
                .commit()

    }

    override fun hideQuestionForYou() {
        game_questions_container.visibility = View.GONE
    }

    override fun showYouCardChoose(choose: Card) {
        setCard(my_selected_card, choose)
        my_selected_card.visibility = View.VISIBLE

        val a_anim = AlphaAnimation(0f, 1f)
        a_anim.duration = 200;
        a_anim.fillAfter = true
        my_selected_card.startAnimation(a_anim)
    }

    override fun hideEnemyCardChoose() {
        enemy_selected_card.clearAnimation()
        enemy_selected_card.visibility = View.INVISIBLE
    }

    override fun hideYouCardChoose() {
        my_selected_card.clearAnimation()
        my_selected_card.visibility = View.INVISIBLE
    }

    override fun showEnemyAnswer(correct: Boolean) {
        if (correct) {
            tv_enemy_score.text = (tv_enemy_score.text.toString().toInt() + 1).toString()
        }
    }

    override fun showYourAnswer(correct: Boolean) {
        if (correct) {
            tv_my_score.text = (tv_my_score.text.toString().toInt() + 1).toString()
        }
    }

    fun setCard(view: View, card: Card) {
        view.tv_card_person_name.text = card.abstractCard!!.name
        view.tv_card_test_name.text = card.test.title

        Glide.with(this)
                .load(card.abstractCard!!.photoUrl)
                .into(view.iv_card)

        setWeight(view.ll_card_params.view_card_intelligence, card.intelligence!!.toFloat())
        setWeight(view.ll_card_params.view_card_support, card.support!!.toFloat())
        setWeight(view.ll_card_params.view_card_prestige, card.prestige!!.toFloat())
        setWeight(view.ll_card_params.view_card_hp, card.hp!!.toFloat())
        setWeight(view.ll_card_params.view_card_strength, card.strength!!.toFloat())
    }




    override fun showGameEnd(type: GamesRepository.GameEndType, card: Card) {

        if (type == GamesRepository.GameEndType.DRAW) {
            MaterialDialog.Builder(this)
                    .title("Draw")
                    .titleGravity(GravityEnum.CENTER)
//                    .content("draw")
                    .neutralText("ok")
                    .buttonsGravity(GravityEnum.END)
                    .onNeutral { dialog, which ->
                        goToFindGameActivity()
                    }
                    .canceledOnTouchOutside(false)
                    .cancelable(false)
                    .show()
        } else {
            val dialog = MaterialDialog.Builder(this)
                    .title(when (type) {
                        GamesRepository.GameEndType.YOU_WIN,
                        GamesRepository.GameEndType.ENEMY_DISCONNECTED_AND_YOU_WIN -> "You win"

                        GamesRepository.GameEndType.YOU_LOSE,
                        GamesRepository.GameEndType.YOU_DISCONNECTED_AND_LOSE -> "You lose"

                        GamesRepository.GameEndType.DRAW -> "Draw"//never
                    })
                    .titleGravity(GravityEnum.CENTER)
                    .customView(R.layout.dialog_end_game, false)

                    .neutralText("ok")
                    .buttonsGravity(GravityEnum.END)
                    .onNeutral { dialog, which ->
                        goToFindGameActivity()
                    }
                    .canceledOnTouchOutside(false)
                    .cancelable(false)
                    .show()

            setCard(dialog.view.card_in_end_dialog, card)

            dialog.view.tv_get_lose_card.text = when (type) {
                GamesRepository.GameEndType.YOU_WIN,
                GamesRepository.GameEndType.ENEMY_DISCONNECTED_AND_YOU_WIN -> "You get card:"

                GamesRepository.GameEndType.YOU_LOSE,
                GamesRepository.GameEndType.YOU_DISCONNECTED_AND_LOSE -> "You lose card:"

                GamesRepository.GameEndType.DRAW -> "Draw"//never
            }

            if (type == GamesRepository.GameEndType.ENEMY_DISCONNECTED_AND_YOU_WIN) {
                dialog.view.tv_game_end_reason.text = "Enemy disconnected"
                dialog.view.tv_game_end_reason.visibility = View.VISIBLE
            }

            if (type == GamesRepository.GameEndType.YOU_DISCONNECTED_AND_LOSE) {
                dialog.view.tv_game_end_reason.text = "You disconnected"
                dialog.view.tv_game_end_reason.visibility = View.VISIBLE
            }

        }

    }

    private fun goToFindGameActivity() {
        FindGameActivity.start(this)
    }


    companion object {

        const val MODE_CHANGE_CARDS = "change_cards"
        const val MODE_PLAY_GAME = "play_game"

        fun start(context: Context) {
            val intent = Intent(context, PlayGameActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }

        fun setWeight(view: View, w: Float) {
            val params = view.layoutParams as LinearLayout.LayoutParams
            params.weight = w
            view.layoutParams = params
        }
    }
}
