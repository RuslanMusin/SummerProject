package com.summer.itis.summerproject.repository.api;

import android.support.annotation.NonNull;


import com.summer.itis.summerproject.model.pojo.opensearch.Item;
import com.summer.itis.summerproject.model.pojo.query.Page;

import java.util.List;

import io.reactivex.Single;

public interface WikiApiRepository {

    Single<List<Item>> opensearch(String query);

    Single<List<Page>> query(String query);


   /* @NonNull
    Single<List<Book>> books(String query);

    Single<Book> book(String id);

    Single<List<Book>> loadDefaultBooks();*/
}
