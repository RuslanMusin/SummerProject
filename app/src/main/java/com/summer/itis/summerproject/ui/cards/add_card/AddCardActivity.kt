package com.summer.itis.summerproject.ui.cards.add_card

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
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
import com.summer.itis.summerproject.ui.tests.add_test.AddTestActivity
import com.summer.itis.summerproject.utils.Const

import com.summer.itis.summerproject.utils.Const.gsonConverter

class AddCardActivity : BaseActivity(), AddCardView, View.OnClickListener {

    private var card: Card? = null

    private var tvPrestige: TextView? = null
    private var tvHp: TextView? = null
    private var tvSupport: TextView? = null
    private var tvStrength: TextView? = null
    private var tvIntelligence: TextView? = null
    private var sbPrestige: SeekBar? = null
    private var sbSupport: SeekBar? = null
    private var sbHp: SeekBar? = null
    private var sbStrength: SeekBar? = null
    private var sbIntelligence: SeekBar? = null
    private var toolbar: Toolbar? = null

    private val wikiUrl: String? = null
    private val photoUrl: String? = null
    private val name: String? = null

    @InjectPresenter
    lateinit var presenter: AddCardPresenter

    private var item: Item? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        card = Card()
        item = gsonConverter.fromJson(intent.getStringExtra(ITEM_JSON), Item::class.java)
        card?.abstractCard?.wikiUrl = item!!.url!!.content
        card?.abstractCard?.description = item!!.description!!.content
        presenter = AddCardPresenter(this)
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
        sbStrength!!.setOnClickListener(this)
        sbSupport!!.setOnClickListener(this)
        sbPrestige!!.setOnClickListener(this)
        sbIntelligence!!.setOnClickListener(this)
        sbHp!!.setOnClickListener(this)

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
        tvHp = findViewById(R.id.tvHp)
        tvIntelligence = findViewById(R.id.tvIntelligence)
        tvPrestige = findViewById(R.id.tvPrestige)
        tvSupport = findViewById(R.id.tvSupport)
        tvStrength = findViewById(R.id.tvStrength)
        sbHp = findViewById(R.id.seekBarHp)
        sbIntelligence = findViewById(R.id.seekBarIntelligence)
        sbPrestige = findViewById(R.id.seekBarPrestige)
        sbSupport = findViewById(R.id.seekBarSupport)
        sbStrength = findViewById(R.id.seekBarStrength)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)

        val checkItem = menu.findItem(R.id.action_check)
        checkItem.setOnMenuItemClickListener {
            val intent = Intent()
            val cardJson = gsonConverter.toJson(card)
            intent.putExtra(CARD_EXTRA, cardJson)
            setResult(Activity.RESULT_OK, intent)
            finish()

            true
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onClick(view: View) {
        val seekBar = view as SeekBar
        when (seekBar.id) {

            R.id.seekBarHp -> tvHp!!.setText(seekBar.progress)

            R.id.seekBarPrestige -> tvPrestige!!.setText(seekBar.progress)

            R.id.seekBarIntelligence -> tvIntelligence!!.setText(seekBar.progress)

            R.id.seekBarSupport -> tvSupport!!.setText(seekBar.progress)

            R.id.seekBarStrength -> tvStrength!!.setText(seekBar.progress)
        }
    }

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
