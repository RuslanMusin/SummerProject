package com.summer.itis.summerproject.ui.cards.cards_list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.summer.itis.summerproject.model.AbstractCard
import com.summer.itis.summerproject.repository.RepositoryProvider
import com.summer.itis.summerproject.utils.ApplicationHelper

/**
 * Created by Home on 13.07.2018.
 */
@InjectViewState
open class CardsPresenter: MvpPresenter<CardsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun getAbstractCardsList(){
        ApplicationHelper.currentUser?.id?.let{it ->
            RepositoryProvider
                    .abstractCardRepository?.findDefaultAbstractCards(it)
                    .subscribe(viewState::showItems,viewState::handleError)
        }
    }

    fun getUserAbstractCardsList(){
        ApplicationHelper.currentUser?.id?.let{it ->
            RepositoryProvider
                    .abstractCardRepository?.findMyAbstractCards(it)
                    .subscribe(viewState::showItems,viewState::handleError)
        }
    }

    fun onItemClick(card: AbstractCard) {
        viewState.showDetails(card)
    }
}