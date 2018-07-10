package com.summer.itis.summerproject.ui.cards.add_card;

import android.annotation.SuppressLint;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.summer.itis.summerproject.repository.RepositoryProvider;
import com.summer.itis.summerproject.ui.tests.add_test.AddTestView;

import static com.summer.itis.summerproject.utils.Const.TAG_LOG;

@InjectViewState
public class AddCardPresenter extends MvpPresenter<AddCardView> {

    private AddCardView addCardView;

    public AddCardPresenter(AddCardView addCardView) {
        this.addCardView = addCardView;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.d(TAG_LOG,"attach presenter");
    }

    @SuppressLint("CheckResult")
    public void query(String query) {
        RepositoryProvider.Companion.getWikiApiRepository()
                .query(query)
                .subscribe(addCardView::setQueryResults, getViewState()::handleError);

    }
}
