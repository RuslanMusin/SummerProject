package com.summer.itis.summerproject.ui.cards.add_card

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import android.widget.TextView

import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.model.pojo.opensearch.Item
import com.summer.itis.summerproject.model.pojo.query.Page
import com.summer.itis.summerproject.ui.base.BaseActivity
import com.summer.itis.summerproject.ui.cards.add_card_list.AddCardListActivity.Companion.CARD_EXTRA
import com.summer.itis.summerproject.ui.tests.add_test.AddTestActivity
import com.summer.itis.summerproject.utils.Const
import com.summer.itis.summerproject.utils.Const.TAG_LOG

import com.summer.itis.summerproject.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.layout_add_card.*

class AddCardActivity : BaseActivity(), AddCardView, SeekBar.OnSeekBarChangeListener {

    private var card: Card? = null

    private var toolbar: Toolbar? = null

    private lateinit var seekBars: List<SeekBar>
    private var balance: Int = 50

    @InjectPresenter
    lateinit var presenter: AddCardPresenter

    private var item: Item? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        presenter = AddCardPresenter(this)
        card = Card()
        seekBars = listOf<SeekBar>(seekBarSupport,seekBarIntelligence,seekBarPrestige,seekBarHp,seekBarStrength)
        item = gsonConverter.fromJson(intent.getStringExtra(ITEM_JSON), Item::class.java)
        card?.abstractCard?.wikiUrl = item!!.url!!.content
        card?.abstractCard?.description = item!!.description!!.content
        item!!.text!!.content?.let { presenter.query(it) }
        initViews()
        setListeners()
    }

    private fun initViews() {
        findViews()
        //        supportActionBar(toolbar);
        setSupportActionBar(toolbar)
        setBackArrow(toolbar!!)

    }

    private fun setListeners() {
        seekBarStrength!!.setOnSeekBarChangeListener(this)
        seekBarHp!!.setOnSeekBarChangeListener(this)
        seekBarPrestige!!.setOnSeekBarChangeListener(this)
        seekBarIntelligence!!.setOnSeekBarChangeListener(this)
        seekBarSupport!!.setOnSeekBarChangeListener(this)

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        changeTvSeekbar(seekBar)
    }

    private fun changeTvSeekbar(seekBar: SeekBar?) {
        val strProgress: String = seekBar?.progress.toString()
        when (seekBar?.id) {
            R.id.seekBarHp -> {
                tvHp!!.setText(strProgress)
                balanceWithOthers(seekBar)
            }

            R.id.seekBarPrestige -> {
                tvPrestige!!.setText(strProgress)
            }

            R.id.seekBarIntelligence -> {
                tvIntelligence!!.setText(strProgress)
            }

            R.id.seekBarSupport -> {
                tvSupport!!.setText(strProgress)
            }

            R.id.seekBarStrength -> {
                tvStrength!!.setText(strProgress)
            }
        }
    }

    private fun balanceWithOthers(seekBar: SeekBar?) {
        while(balance != 50) {
            for (seek in seekBars) {
                if(balance == 50) {
                    return
                }
                if (!seek.equals(seekBar) && balance !=50) {
                    seek.progress--
                }
            }
        }
    }

    override fun setQueryResults(list: List<Page>) {
        val page = list[0]
        card!!.abstractCard?.name = page.title
        card!!.abstractCard?.photoUrl = page.original!!.source
        card!!.abstractCard?.extract = page.extract!!.content

    }

    override fun handleError(throwable: Throwable) {

    }

    private fun findViews() {
        toolbar = findViewById(R.id.toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)

        val checkItem = menu.findItem(R.id.action_check)
        checkItem.setOnMenuItemClickListener {
            prepareCard()
            val intent = Intent()
            Log.d(TAG_LOG,"wiki url = " + card?.abstractCard?.wikiUrl)
            val cardJson = gsonConverter.toJson(card)
            intent.putExtra(CARD_EXTRA, cardJson)
            setResult(Activity.RESULT_OK, intent)
            finish()

            true
        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun prepareCard() {
        card?.hp = seekBarHp.progress
        card?.strength = seekBarStrength.progress
        card?.support = seekBarSupport.progress
        card?.prestige = seekBarPrestige.progress
        card?.intelligence = seekBarIntelligence.progress
    }

    /* override fun onClick(view: View) {

         }
     }*/

    companion object {

        val CARD_EXTRA = "card"

        var ITEM_JSON = "item_json"

        fun start(activity: Activity, item: Item) {
            val intent = Intent(activity, AddTestActivity::class.java)
            val itemJson = gsonConverter.toJson(item)
            intent.putExtra(ITEM_JSON, itemJson)
            activity.startActivity(intent)
        }
    }


}
