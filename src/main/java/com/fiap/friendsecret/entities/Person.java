package com.fiap.friendsecret.entities;

public class Person {
    private String nickname;
    private Gift gift;

    public Person(String nickname, Gift gift) {
        this.nickname = nickname;
        this.gift = gift;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }
}
