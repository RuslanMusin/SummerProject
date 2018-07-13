package com.summer.itis.summerproject.ui.tests.test_list.test

import android.annotation.SuppressLint
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.summer.itis.summerproject.model.Test
import com.summer.itis.summerproject.repository.RepositoryProvider
import com.summer.itis.summerproject.utils.ApplicationHelper
import com.summer.itis.summerproject.utils.Const.TAG_LOG
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

@InjectViewState
class TestListPresenter : MvpPresenter<TestListView>() {

    @SuppressLint("CheckResult")
    fun loadOfficialTestsByQUery(query: String) {
        RepositoryProvider.testRepository!!
                .findOfficialTests(query)
                .doOnSubscribe(Consumer<Disposable> { viewState.showLoading(it) })
                .doAfterTerminate(Action { viewState.hideLoading() })
                .subscribe({ viewState.changeDataSet(it) }, { viewState.handleError(it) })
    }

    @SuppressLint("CheckResult")
    fun loadUserTestsByQUery(query: String, userId: String) {
        RepositoryProvider.testRepository!!
                .findUserTests(query)
                .doOnSubscribe(Consumer<Disposable> { viewState.showLoading(it) })
                .doAfterTerminate(Action { viewState.hideLoading() })
                .subscribe({ viewState.changeDataSet(it) }, { viewState.handleError(it) })
    }

    @SuppressLint("CheckResult")
    fun loadMyTestsByQUery(query: String, userId: String) {
        RepositoryProvider.testRepository!!
                .findMyTests(query)
                .doOnSubscribe({ viewState.showLoading(it) })
                .doAfterTerminate({ viewState.hideLoading() })
                .subscribe({ viewState.changeDataSet(it) }, { viewState.handleError(it) })
    }

    @SuppressLint("CheckResult")
    fun loadUserTests(userId: String) {
        RepositoryProvider.testRepository!!
                .findUserTests(userId)
                .doOnSubscribe(Consumer<Disposable> { viewState.showLoading(it) })
                .doAfterTerminate(Action { viewState.hideLoading() })
                .subscribe({ viewState.changeDataSet(it) }, { viewState.handleError(it) })
    }

    @SuppressLint("CheckResult")
    fun loadMyTests(userId: String) {
        RepositoryProvider.testRepository!!
                .findMyTests(userId)
                .doOnSubscribe(Consumer<Disposable> { viewState.showLoading(it) })
                .doAfterTerminate(Action { viewState.hideLoading() })
                .subscribe({ viewState.changeDataSet(it) }, { viewState.handleError(it) })
    }

    @SuppressLint("CheckResult")
    fun loadOfficialTests() {
        Log.d(TAG_LOG, "load books")
        ApplicationHelper.currentUser?.id?.let {
            RepositoryProvider.testRepository
                    .findOfficialTests(it)
                    .doOnSubscribe({ viewState.showLoading(it) })
                    .doAfterTerminate({ viewState.hideLoading() })
                    .doAfterTerminate({ viewState.setNotLoading() })
                    .subscribe({ viewState.changeDataSet(it) }, { viewState.handleError(it) })
        }
    }

    /*@SuppressLint("CheckResult")
    fun loadNextElements(page: Int) {
        Log.d(TAG, "load books")
        RepositoryProvider.userRepository!!
                .loadDefaultUsers()
                .doOnSubscribe(Consumer<Disposable> { viewState.showLoading(it) })
                .doAfterTerminate(Action { viewState.hideLoading() })
                .doAfterTerminate(Action { viewState.setNotLoading() })
                .subscribe({ this.setReaders(it) }, { viewState.handleError(it) })

    }*/

    /*@SuppressLint("CheckResult")
    fun loadByIds(usersId: List<String>) {
        RepositoryProvider.userRepository!!
                .loadByIds(usersId)
                .doOnSubscribe(Consumer<Disposable> { viewState.showLoading(it) })
                .doAfterTerminate(Action { viewState.hideLoading() })
                .subscribe({ this.showItems(it) }, { viewState.handleError(it) })
    }


    //work with DB
    fun setFriends(books: Query) {
        books.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val friendsId = ArrayList<String>()
                for (snapshot in dataSnapshot.children) {
                    val elementId = snapshot.getValue(ElementId::class.java)
                    friendsId.add(elementId!!.id)
                }
                loadByIds(friendsId)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

    fun setRequests(books: Query) {
        books.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val friendsId = ArrayList<String>()
                for (snapshot in dataSnapshot.children) {
                    val elementId = snapshot.getValue(ElementId::class.java)
                    friendsId.add(elementId!!.id)
                }
                loadByIds(friendsId)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    fun setReaders(books: Query) {
        books.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val users = ArrayList<User>()
                for (snapshot in dataSnapshot.children) {
                    val reader = snapshot.getValue(User::class.java)
                    if (reader != null) {
                        users.add(reader)
                    }
                }
                viewState.changeDataSet(users)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    fun setFriendsByQuery(queries: List<Query>) {
        val friends = ArrayList<User>()
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val reader = snapshot.getValue(User::class.java)
                    reader?.let { friends.add(it) }
                    if (friends.size == queries.size) {
                        viewState.changeDataSet(friends)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        for (query in queries) {
            query.addListenerForSingleValueEvent(listener)
        }

    }

    fun setRequestsByQuery(queries: List<Query>) {
        val requests = ArrayList<User>()
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val reader = snapshot.getValue(User::class.java)
                    reader?.let { requests.add(it) }
                    if (requests.size == queries.size) {
                        viewState.changeDataSet(requests)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        for (query in queries) {
            query.addListenerForSingleValueEvent(listener)
        }

    }

    fun showItems(queries: List<Query>) {
        val users = ArrayList<User>()
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val crossing = dataSnapshot.getValue(User::class.java)
                crossing?.let { users.add(it) }
                if (users.size == queries.size) {
                    viewState.changeDataSet(users)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG_LOG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        for (query in queries) {
            query.addListenerForSingleValueEvent(listener)
        }
    }*/


    fun onItemClick(comics: Test) {
        viewState.showDetails(comics)
    }
}
