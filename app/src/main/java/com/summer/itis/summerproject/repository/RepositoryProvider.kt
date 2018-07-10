package com.summer.itis.summerproject.repository

import com.summer.itis.summerproject.repository.api.WikiApiRepository
import com.summer.itis.summerproject.repository.api.WikiApiRepositoryImpl
import com.summer.itis.summerproject.repository.json.CardRepository
import com.summer.itis.summerproject.repository.json.QuestionRepository
import com.summer.itis.summerproject.repository.json.TestRepository
import com.summer.itis.summerproject.repository.json.UserRepository


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

        var wikiApiRepository: WikiApiRepository? = null
            get() {
                if (field == null) {
                    this.wikiApiRepository = WikiApiRepositoryImpl()
                }
                return field
            }

    }
}