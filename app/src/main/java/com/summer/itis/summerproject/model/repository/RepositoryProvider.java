package com.summer.itis.summerproject.model.repository;

import com.summer.itis.summerproject.model.repository.json.CardRepository;
import com.summer.itis.summerproject.model.repository.json.QuestionRepository;
import com.summer.itis.summerproject.model.repository.json.TestRepository;
import com.summer.itis.summerproject.model.repository.json.UserRepository;


public class RepositoryProvider {

    private static UserRepository userRepository;

    private static TestRepository testRepository;

    private static CardRepository cardRepository;

    private static QuestionRepository questionRepository;

    public static UserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    public static void setUserRepository(UserRepository userRepository) {
        RepositoryProvider.userRepository = userRepository;
    }

    public static TestRepository getTestRepository() {
        if (testRepository == null) {
            testRepository = new TestRepository();
        }
        return testRepository;
    }

    public static void setTestRepository(TestRepository testRepository) {
        RepositoryProvider.testRepository = testRepository;
    }

    public static CardRepository getCardRepository() {
        if (cardRepository == null) {
            cardRepository = new CardRepository();
        }
        return cardRepository;
    }

    public static void setCardRepository(CardRepository cardRepository) {
        RepositoryProvider.cardRepository = cardRepository;
    }

    public static QuestionRepository getQuestionRepository() {
        if (questionRepository == null) {
            questionRepository = new QuestionRepository();
        }
        return questionRepository;
    }

    public static void setQuestionRepository(QuestionRepository questionRepository) {
        RepositoryProvider.questionRepository = questionRepository;
    }
}