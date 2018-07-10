package com.summer.itis.summerproject.ui.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.summerproject.R
import kotlinx.android.synthetic.main.activity_find_game.*

public class FindGameActivity : MvpAppCompatActivity(), FindGameView {
    @InjectPresenter
    public lateinit var presenter: FindGamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_game)

        showNotSearching();

        btn_find_game.setOnClickListener {
            presenter!!.findGame()
        }

        btn_cancel.setOnClickListener {
            presenter!!.cancelSearching()
        }
    }

    override fun showNotSearching() {
        layout_searching.visibility = View.GONE
        btn_find_game.visibility = View.VISIBLE
    }

    override fun showSearching() {
        layout_searching.visibility = View.VISIBLE
        btn_find_game.visibility = View.GONE
        //TODO disable navigation
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, FindGameActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
    }
}
