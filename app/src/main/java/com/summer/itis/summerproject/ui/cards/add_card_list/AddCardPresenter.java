package com.summer.itis.summerproject.ui.cards.add_card_list;

import android.annotation.SuppressLint;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.summer.itis.summerproject.repository.RepositoryProvider;

import static com.summer.itis.summerproject.utils.Const.TAG_LOG;

@InjectViewState
public class AddCardPresenter extends MvpPresenter<AddCardListView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.d(TAG_LOG,"attach presenter");
    }

    @SuppressLint("CheckResult")
    public void opensearch(String opensearch) {
        RepositoryProvider.Companion.getWikiApiRepository()
                .opensearch(opensearch)
                .subscribe(getViewState()::setOpenSearchList, getViewState()::handleError);
    }
}
