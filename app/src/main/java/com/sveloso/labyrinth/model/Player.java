package com.sveloso.labyrinth.model;

import android.content.Context;

import java.util.UUID;

/**
 * Created by Veloso on 7/1/2016.
 */
public class Player {

    private String mName;
    private UUID mId;

    private int mXcoordinate;
    private int mYcoordinate;
    private int mHealth;

    public Player() {
        this(UUID.randomUUID());
    }

    public Player(UUID id) {
        mId = id;
        mHealth = 200;
        mName = "Player";
    }

    public UUID getId() {
        return mId;
    }

    public int getXcoordinate() {
        return mXcoordinate;
    }

    public void setXcoordinate(int xcoordinate) {
        mXcoordinate = xcoordinate;
    }

    public int getYcoordinate() {
        return mYcoordinate;
    }

    public void setYcoordinate(int ycoordinate) {
        mYcoordinate = ycoordinate;
    }

    public int getHealth() {
        return mHealth;
    }

    public void setHealth(int health) {
        mHealth = health;
    }

    public void setName(String name) {
        mName = name;
    }
    
}
