package com.summer.itis.summerproject.ui.game.play

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.summerproject.R

class PlayGameActivity : MvpAppCompatActivity(), PlayGameView {

    @InjectPresenter
    lateinit var presenter: PlayGamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_game)
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, PlayGameActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
    }
}
