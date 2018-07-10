package com.summer.itis.summerproject.ui.tests.add_test;

import com.arellomobile.mvp.MvpView;
import com.summer.itis.summerproject.api.Card;
import com.summer.itis.summerproject.model.Question;
import com.summer.itis.summerproject.model.Test;

public interface AddTestView extends MvpView {

    void setQuestion(Question question);

    void setCard(Card card);

    void createTest();

    void setTest(Test test);
}
