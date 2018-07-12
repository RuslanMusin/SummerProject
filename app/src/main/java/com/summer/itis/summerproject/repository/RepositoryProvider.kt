package com.summer.itis.summerproject.repository

import android.util.Log
import com.summer.itis.summerproject.repository.api.WikiApiRepository
import com.summer.itis.summerproject.repository.api.WikiApiRepositoryImpl
import com.summer.itis.summerproject.repository.json.*
import com.summer.itis.summerproject.utils.Const.TAG_LOG


class RepositoryProvider {

    companion object {
        var userRepository: UserRepository? = null
            get() {
                if (field == null) {
                    this.userRepository = UserRepository()
                }
                return field
            }

        var testRepository: TestRepository? = null
            get() {
                if (field == null) {
                    this.testRepository = TestRepository()
                }
                return field
            }

        var cardRepository: CardRepository? = null
            get() {
                if (field == null) {
                    this.cardRepository = CardRepository()
                }
                return field
            }

        var questionRepository: QuestionRepository? = null
            get() {
                if (field == null) {
                    this.questionRepository = QuestionRepository()
                }
                return field
            }

        val gamesRepository: GamesRepository by lazy {
            GamesRepository()
        }

        val wikiApiRepository: WikiApiRepository by lazy {
            Log.d(TAG_LOG,"wikiRepo")
            WikiApiRepositoryImpl()
        }


    }
}