package com.sveloso.labyrinth.model;

import android.content.Context;

/**
 * Created by Veloso on 7/1/2016.
 */
public class Player {

    private static Player sPlayer;
    private Context mContext;

    private String mName;
    private int mXcoordinate;
    private int mYcoordinate;

    private int mHealth;

    public static Player get(Context context) {
        if (sPlayer == null) {
            sPlayer = new Player (context);
        }
        return sPlayer;
    }

    private Player (Context context) {
        mContext = context;
        mHealth = 200;
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

    public String getName() {
        if (mName == null) {
            return "Player";
        } else {
            return mName;
        }
    }
}
