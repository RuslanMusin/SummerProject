package com.summer.itis.summerproject.repository

import android.util.Log
import com.summer.itis.summerproject.repository.api.WikiApiRepository
import com.summer.itis.summerproject.repository.api.WikiApiRepositoryImpl
import com.summer.itis.summerproject.repository.json.*
import com.summer.itis.summerproject.utils.Const.TAG_LOG


class RepositoryProvider {

    companion object {
        val testRepository: TestRepository by lazy {
            TestRepository()
        }

        val cardRepository: CardRepository by lazy {
            CardRepository()
        }

        val userRepository: UserRepository by lazy {
            UserRepository()
        }


        val gamesRepository: GamesRepository by lazy {
            GamesRepository()
        }

        val abstractCardRepository: AbstractCardRepository by lazy {
            AbstractCardRepository()
        }

        val wikiApiRepository: WikiApiRepository by lazy {
            Log.d(TAG_LOG,"wikiRepo")
            WikiApiRepositoryImpl()
        }


    }
}