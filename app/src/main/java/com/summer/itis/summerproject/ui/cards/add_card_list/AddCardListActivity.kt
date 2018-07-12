package com.summer.itis.summerproject.ui.cards.add_card_list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.widget.TextView

import com.annimon.stream.Stream
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.model.pojo.opensearch.Item
import com.summer.itis.summerproject.ui.base.BaseAdapter
import com.summer.itis.summerproject.ui.base.NavigationBaseActivity
import com.summer.itis.summerproject.ui.cards.add_card.AddCardActivity
import com.summer.itis.summerproject.ui.cards.add_card.AddCardActivity.Companion.ITEM_JSON
import com.summer.itis.summerproject.ui.tests.add_test.AddTestActivity
import com.summer.itis.summerproject.ui.widget.EmptyStateRecyclerView

import java.util.ArrayList
import java.util.regex.Pattern

import com.summer.itis.summerproject.utils.Const.TAG_LOG
import com.summer.itis.summerproject.utils.Const.gsonConverter

class AddCardListActivity : NavigationBaseActivity(), AddCardListView, BaseAdapter.OnItemClickListener<Item> {

    private var card: Card? = null

    private var toolbar: Toolbar? = null

    @InjectPresenter
    lateinit var listPresenter: AddCardListPresenter

    private var recyclerView: EmptyStateRecyclerView? = null
    private var tvEmpty: TextView? = null

    private var adapter: AddCardListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_list)

        card = Card()

        initViews()
        initRecycler()
    }

    private fun initViews() {
        findViews()
        //        supportActionBar(toolbar);
        setSupportActionBar(toolbar)
        setBackArrow(toolbar!!)

    }

    override fun handleError(throwable: Throwable) {

    }

    private fun findViews() {
        toolbar = findViewById(R.id.tb_books_list)
        recyclerView = findViewById(R.id.rv_comics_list)
        tvEmpty = findViewById(R.id.tv_empty)
    }

    override fun setOpenSearchList(list: List<Item>) {
        var itemList = list

        Log.d(TAG_LOG,"setResult = " + itemList.size)

        for (item in itemList) {
            Log.d(TAG_LOG, "text " + item.text!!.content!!)
            Log.d(TAG_LOG, "desc " + item.description!!.content!!)
            Log.d(TAG_LOG, "url " + item.url!!.content!!)
        }
        val sep = "-----------"
        Log.d(TAG_LOG, sep)
        itemList = Stream.of(itemList)
                .filter { e ->
                    val text = e.description!!.content
                    val pattern = Pattern.compile(".*\\(.*[0-9]{1,4}.*(\\s*-\\s*[0-9]{1,4}.*)?\\).*")
                    pattern.matcher(text!!).matches()
                }
                .toList()
        for (item in itemList) {
            Log.d(TAG_LOG, "text " + item.text!!.content!!)
            Log.d(TAG_LOG, "desc " + item.description!!.content!!)
            Log.d(TAG_LOG, "url " + item.url!!.content!!)
        }

        adapter!!.changeDataSet(itemList)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)

        var searchView: android.support.v7.widget.SearchView? = null
        if (searchItem != null) {
            searchView = searchItem.actionView as android.support.v7.widget.SearchView
        }
        if (searchView != null) {
            val finalSearchView = searchView
            searchView.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String): Boolean {
                    Log.d(TAG_LOG,"opensearch")
                    listPresenter.opensearch(query)
                    if (!finalSearchView.isIconified) {
                        finalSearchView.isIconified = true
                    }
                    searchItem!!.collapseActionView()
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun initRecycler() {
        adapter = AddCardListAdapter(ArrayList())
        val manager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = manager
        recyclerView!!.setEmptyView(tvEmpty!!)
        adapter!!.attachToRecyclerView(recyclerView!!)
        adapter!!.setOnItemClickListener(this)
        recyclerView!!.adapter = adapter
        recyclerView!!.setHasFixedSize(true)
    }


    override fun onItemClick(item: Item) {
        val intent = Intent(this, AddCardActivity::class.java)
        val itemJson = gsonConverter.toJson(item)
        intent.putExtra(ITEM_JSON, itemJson)
        startActivityForResult(intent, ADD_CARD)
    }

    public override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(reqCode, resultCode, data)

        if (reqCode == ADD_CARD && resultCode == Activity.RESULT_OK) {
            val card = data.getStringExtra(CARD_EXTRA)
            val intent = Intent()
            intent.putExtra(CARD_EXTRA, card)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    companion object {

        val CARD_EXTRA = "card"

        private val ADD_CARD = 1

        fun start(activity: Activity) {
            val intent = Intent(activity, AddTestActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
