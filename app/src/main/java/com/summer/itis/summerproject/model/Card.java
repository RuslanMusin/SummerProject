package com.summer.itis.summerproject.model;

import com.summer.itis.summerproject.model.AbstractCard;

public class Card {

    private String id;

    private String cardId;

    private AbstractCard abstractCard;

    private Integer intelligence;

    private Integer support;

    private Integer prestige;

    private Integer hp;

    private Integer strength;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public AbstractCard getAbstractCard() {
        return abstractCard;
    }

    public void setAbstractCard(AbstractCard abstractCard) {
        this.abstractCard = abstractCard;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public Integer getSupport() {
        return support;
    }

    public void setSupport(Integer support) {
        this.support = support;
    }

    public Integer getPrestige() {
        return prestige;
    }

    public void setPrestige(Integer prestige) {
        this.prestige = prestige;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }
}
