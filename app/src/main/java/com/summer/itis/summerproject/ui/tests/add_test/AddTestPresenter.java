package com.summer.itis.summerproject.ui.tests.add_test;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import static com.summer.itis.summerproject.utils.Const.TAG_LOG;

@InjectViewState
public class AddTestPresenter extends MvpPresenter<AddTestView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.d(TAG_LOG,"attach presenter");
    }
}
