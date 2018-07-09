package com.summer.itis.summerproject.model_v2.entities.creation;

import com.summer.itis.summerproject.model_v2.entities.general.Question;

import java.util.List;

public class NewCardData {
    String name;

    String description;

    String photoUrl;

    String wikiUrl;


    Integer intelligence;

    Integer support;

    Integer prestige;

    Integer hp;

    Integer strength;

    List<Question> testQuestions;
}
