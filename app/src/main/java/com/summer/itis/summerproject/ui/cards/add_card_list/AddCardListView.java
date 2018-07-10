package com.summer.itis.summerproject.ui.cards.add_card_list;

import com.arellomobile.mvp.MvpView;
import com.summer.itis.summerproject.model.pojo.opensearch.Item;
import com.summer.itis.summerproject.model.pojo.query.Page;

import java.util.List;

public interface AddCardListView extends MvpView {
    void setOpenSearchList(List<Item> object);

    void handleError(Throwable throwable);
}

