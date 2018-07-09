package com.summer.itis.summerproject.model_v2.entities.general;

import java.util.List;

public class Question {
    int id;

    //наверное, лучше так, чем хранить номер верного ответа
    //не уверен, что порядок в списке ответов не нарушится
    List<Answer> answers;

}
