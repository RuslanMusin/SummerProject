package com.summer.itis.summerproject.ui.cards.add_card_list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.summer.itis.summerproject.R;
import com.summer.itis.summerproject.api.Card;
import com.summer.itis.summerproject.model.pojo.opensearch.Item;
import com.summer.itis.summerproject.ui.base.BaseAdapter;
import com.summer.itis.summerproject.ui.base.NavigationBaseActivity;
import com.summer.itis.summerproject.ui.cards.add_card.AddCardActivity;
import com.summer.itis.summerproject.ui.tests.add_test.AddTestActivity;
import com.summer.itis.summerproject.ui.widget.EmptyStateRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.summer.itis.summerproject.ui.cards.add_card.AddCardActivity.ITEM_JSON;
import static com.summer.itis.summerproject.utils.Const.TAG_LOG;
import static com.summer.itis.summerproject.utils.Const.gsonConverter;

public class AddCardListListActivity extends NavigationBaseActivity implements AddCardListView, BaseAdapter.OnItemClickListener<Item> {

    public final static String CARD_EXTRA = "card";

    private Card card;

    private Toolbar toolbar;

    @InjectPresenter
    AddCardPresenter presenter;

    private EmptyStateRecyclerView recyclerView;
    private TextView tvEmpty;

    private AddCardListAdapter adapter;

    private static final int ADD_CARD = 1;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, AddTestActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);

        card = new Card();

        initViews();
        initRecycler();
    }

    private void initViews() {
        findViews();
//        supportActionBar(toolbar);
        setSupportActionBar(toolbar);
        setBackArrow(toolbar);

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    private void findViews() {
        toolbar = findViewById(R.id.tb_books_list);
        recyclerView = findViewById(R.id.rv_comics_list);
        tvEmpty = findViewById(R.id.tv_empty);
    }

    @Override
    public void setOpenSearchList(List<Item> itemList) {

        for(Item item : itemList) {
            Log.d(TAG_LOG,"text " + item.getText().getContent());
            Log.d(TAG_LOG,"desc " + item.getDescription().getContent());
            Log.d(TAG_LOG,"url " + item.getUrl().getContent());
        }
        String sep = "-----------";
        Log.d(TAG_LOG,sep);
        itemList = Stream.of(itemList)
                .filter(e -> {
                    String text = e.getDescription().getContent();
                    Pattern pattern = Pattern.compile(".*\\(.*[0-9]{1,4}.*(\\s*-\\s*[0-9]{1,4}.*)?\\).*");
                    return pattern.matcher(text).matches();
                })
                .toList();
        for(Item item : itemList) {
            Log.d(TAG_LOG,"text " + item.getText().getContent());
            Log.d(TAG_LOG,"desc " + item.getDescription().getContent());
            Log.d(TAG_LOG,"url " + item.getUrl().getContent());
        }

        adapter.changeDataSet(itemList);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        android.support.v7.widget.SearchView searchView = null;
        if (searchItem != null) {
            searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            android.support.v7.widget.SearchView finalSearchView = searchView;
            searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    presenter.opensearch(query);
                    if (!finalSearchView.isIconified()) {
                        finalSearchView.setIconified(true);
                    }
                    searchItem.collapseActionView();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void initRecycler() {
        adapter = new AddCardListAdapter(new ArrayList<>());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setEmptyView(tvEmpty);
        adapter.attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }


    @Override
    public void onItemClick(@NonNull Item item) {
        Intent intent = new Intent(this, AddCardActivity.class);
        String itemJson = gsonConverter.toJson(item);
        intent.putExtra(ITEM_JSON,itemJson);
        startActivityForResult(intent,ADD_CARD);    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == ADD_CARD && resultCode == Activity.RESULT_OK) {
            String card = data.getStringExtra(CARD_EXTRA);
            Intent intent = new Intent();
            intent.putExtra(CARD_EXTRA, card);
            setResult(RESULT_OK, intent);
            finish();        }
    }
}
