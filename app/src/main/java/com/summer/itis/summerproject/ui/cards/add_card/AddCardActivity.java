package com.summer.itis.summerproject.ui.cards.add_card;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.summer.itis.summerproject.R;
import com.summer.itis.summerproject.api.Card;
import com.summer.itis.summerproject.model.pojo.opensearch.Item;
import com.summer.itis.summerproject.model.pojo.query.Page;
import com.summer.itis.summerproject.ui.base.BaseActivity;
import com.summer.itis.summerproject.ui.tests.add_test.AddTestActivity;
import com.summer.itis.summerproject.utils.Const;

import java.util.List;

import static com.summer.itis.summerproject.utils.Const.gsonConverter;

public class AddCardActivity extends BaseActivity implements AddCardView,View.OnClickListener {

    public final static String CARD_EXTRA = "card";

    private Card card;

    private TextView tvPrestige;
    private TextView tvHp;
    private TextView tvSupport;
    private TextView tvStrength;
    private TextView tvIntelligence;
    private SeekBar sbPrestige;
    private SeekBar sbSupport;
    private SeekBar sbHp;
    private SeekBar sbStrength;
    private SeekBar sbIntelligence;
    private Toolbar toolbar;

    private String wikiUrl;
    private String photoUrl;
    private String name;

    @InjectPresenter
    AddCardPresenter presenter;

    private Item item;

    public static String ITEM_JSON = "item_json";

    public static void start(@NonNull Activity activity, Item item) {
        Intent intent = new Intent(activity, AddTestActivity.class);
        String itemJson = gsonConverter.toJson(item);
        intent.putExtra(ITEM_JSON,itemJson);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        card = new Card();
        item = gsonConverter.fromJson(getIntent().getStringExtra(ITEM_JSON),Item.class);
        card.setWikiUrl(item.getUrl().getContent());
        card.setDescription(item.getDescription().getContent());
        presenter = new AddCardPresenter(this);
        presenter.query(item.getText().getContent());
        initViews();
        setListeners();
    }

    private void initViews() {
        findViews();
//        supportActionBar(toolbar);
        setSupportActionBar(toolbar);
        setBackArrow(toolbar);

    }

    private void setListeners() {
        sbStrength.setOnClickListener(this);
        sbSupport.setOnClickListener(this);
        sbPrestige.setOnClickListener(this);
        sbIntelligence.setOnClickListener(this);
        sbHp.setOnClickListener(this);

    }

    @Override
    public void setQueryResults(List<Page> list) {
        Page page = list.get(0);
        card.setName(page.getTitle());
        card.setPhotoUrl(page.getOriginal().getSource());
        card.setExtract(page.getExtract().getContent());

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbar);
        tvHp = findViewById(R.id.tvHp);
        tvIntelligence = findViewById(R.id.tvIntelligence);
        tvPrestige = findViewById(R.id.tvPrestige);
        tvSupport = findViewById(R.id.tvSupport);
        tvStrength = findViewById(R.id.tvStrength);
        sbHp = findViewById(R.id.seekBarHp);
        sbIntelligence = findViewById(R.id.seekBarIntelligence);
        sbPrestige = findViewById(R.id.seekBarPrestige);
        sbSupport = findViewById(R.id.seekBarSupport);
        sbStrength = findViewById(R.id.seekBarStrength);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);

        MenuItem checkItem = menu.findItem(R.id.action_check);
        checkItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent();
                String cardJson = gsonConverter.toJson(card);
                intent.putExtra(CARD_EXTRA, cardJson);
                setResult(RESULT_OK, intent);
                finish();

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
        SeekBar seekBar = (SeekBar) view;
        switch (seekBar.getId()) {

            case R.id.seekBarHp :
                tvHp.setText(seekBar.getProgress());
                break;

            case R.id.seekBarPrestige :
                tvPrestige.setText(seekBar.getProgress());
                break;

            case R.id.seekBarIntelligence :
                tvIntelligence.setText(seekBar.getProgress());
                break;

            case R.id.seekBarSupport :
                tvSupport.setText(seekBar.getProgress());
                break;

            case R.id.seekBarStrength :
                tvStrength.setText(seekBar.getProgress());
                break;
        }
    }


}
