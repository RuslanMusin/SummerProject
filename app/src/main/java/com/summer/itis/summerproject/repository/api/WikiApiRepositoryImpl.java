package com.summer.itis.summerproject.repository.api;

import android.support.annotation.NonNull;


import com.summer.itis.summerproject.api.ApiFactory;
import com.summer.itis.summerproject.model.pojo.opensearch.Item;
import com.summer.itis.summerproject.model.pojo.opensearch.SearchSuggestion;
import com.summer.itis.summerproject.model.pojo.opensearch.Section;
import com.summer.itis.summerproject.model.pojo.query.Api;
import com.summer.itis.summerproject.model.pojo.query.Page;
import com.summer.itis.summerproject.model.pojo.query.Pages;
import com.summer.itis.summerproject.model.pojo.query.Query;
import com.summer.itis.summerproject.utils.Const;
import com.summer.itis.summerproject.utils.RxUtils;

import java.util.List;

import io.reactivex.Single;

import static android.app.SearchManager.QUERY;
import static com.summer.itis.summerproject.utils.Const.EXINTRO;
import static com.summer.itis.summerproject.utils.Const.EXPLAINTEXT;
import static com.summer.itis.summerproject.utils.Const.FORMAT;
import static com.summer.itis.summerproject.utils.Const.NAMESPACE;
import static com.summer.itis.summerproject.utils.Const.PILICENSE;
import static com.summer.itis.summerproject.utils.Const.PIPROP;
import static com.summer.itis.summerproject.utils.Const.PROP;
import static com.summer.itis.summerproject.utils.Const.SEARCH;

public class WikiApiRepositoryImpl implements WikiApiRepository {

    @NonNull
    @Override
    public Single<List<Item>> opensearch(String query) {
        return ApiFactory.Companion.getWikiService()
                .opensearch(FORMAT, SEARCH,query, NAMESPACE)
                .map(SearchSuggestion::getSection)
                .map(Section ::getItem)
                .compose(RxUtils.Companion.asyncSingle());
    }

    @NonNull
    @Override
    public Single<List<Page>> query(String query) {
        return ApiFactory.getWikiService()
                .query(FORMAT, QUERY, PROP, EXINTRO, EXPLAINTEXT, PIPROP, PILICENSE,query)
                .map(Api::getQuery)
                .map(Query ::getPages)
                .map(Pages::getPage)
                .compose(RxUtils.Companion.asyncSingle());
    }

 /*   @Override
    public Single<Book> book(String id) {
        return ApiFactory.getWikiService()
                .book(id)
                .map(this::convert)
                .compose(RxUtils.asyncSingle());
    }

    @Override
    public Single<List<Book>> loadDefaultBooks() {
        return ApiFactory.getWikiService()
                .books(DEFAULT_QUERY,DEFAULT_MAX_RESULTS,DEFAULT_ORDER_BOOK,DEFAULT_PROJECTION)
                .map(SearchResult::getListGBooks)
                .map(this ::convertList)
                .compose(RxUtils.asyncSingle());
    }

    private List<Book> convertList(List<GBook> booksApi){
        Log.d(TAG,"books size =  " + booksApi.size());
        List<Book> books = new ArrayList<>();

        for(GBook GBook : booksApi){
            books.add(convert(GBook));
        }
        return books;
    }*/

   /* private Book convert(GBook gBook) {
        Book book = new Book();
        book.setId(gBook.getId());

        VolumeInfo vInfo = gBook.getVolumeInfo();

        book.setName(vInfo.getTitle());

        if (vInfo.getAuthors() == null) {
            book.setAuthors(null);
        } else {
            book.setAuthors(Arrays.asList(vInfo.getAuthors()));
        }

        String desc = vInfo.getDescription();
        if (desc == null) {
            book.setDesc(null);
        } else {
            book.setDesc(desc);
        }

        ImageLinks imageLinks = vInfo.getImageLinks();
        if (imageLinks != null) {
            book.setPhotoUrl(imageLinks.getThumbnail());
        } else {
            book.setPhotoUrl(String.valueOf(R.drawable.book_default));
        }

        return book;
    }*/
}
