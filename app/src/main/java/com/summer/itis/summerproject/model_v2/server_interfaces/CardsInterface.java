package com.summer.itis.summerproject.model_v2.server_interfaces;

import com.summer.itis.summerproject.model_v2.entities.creation.NewCardData;
import com.summer.itis.summerproject.model_v2.entities.creation.NewCommentData;
import com.summer.itis.summerproject.model_v2.entities.general.Card;
import com.summer.itis.summerproject.model_v2.entities.general.Comment;
import com.summer.itis.summerproject.model_v2.entities.general.TestData;

import java.util.List;

import io.reactivex.Single;

public interface CardsInterface {
    Single<List<Card>> getAllCards();

    Single<List<Card>> getMyCards();

    Single<List<String>> getMyCardIds();

    Single<TestData> getTestDataByCardId(String cardId);

    Single<List<Comment>> getCommentsByCardId(String cardId);

    Single<Card> createCard(NewCardData newCard);

    Single<Comment> createComment(NewCommentData newComment);
}
