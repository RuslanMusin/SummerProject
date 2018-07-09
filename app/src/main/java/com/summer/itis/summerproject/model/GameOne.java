package com.summer.itis.summerproject.model;

public class GameOne {

    private String id;

    private String gameId;

    private String qustionId;

    private String cardId;

    private Integer score;

    private String enemyId;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQustionId() {
        return qustionId;
    }

    public void setQustionId(String qustionId) {
        this.qustionId = qustionId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(String enemyId) {
        this.enemyId = enemyId;
    }
}
