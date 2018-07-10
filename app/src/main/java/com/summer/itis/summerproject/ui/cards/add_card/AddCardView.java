package com.summer.itis.summerproject.ui.cards.add_card;

import com.arellomobile.mvp.MvpView;
import com.summer.itis.summerproject.model.Test;
import com.summer.itis.summerproject.model.pojo.opensearch.Item;
import com.summer.itis.summerproject.model.pojo.query.Page;

import java.util.List;

public interface AddCardView extends MvpView {

    void setQueryResults(List<Page> object);

    void handleError(Throwable throwable);
}

