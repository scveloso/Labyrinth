package com.sveloso.labyrinth;

import android.content.Context;

/**
 * Created by Veloso on 7/1/2016.
 */
public class Player {

    private static Player sPlayer;
    private Context mContext;

    private int mXcoordinate;
    private int mYcoordinate;

    public static Player get(Context context) {
        if (sPlayer == null) {
            sPlayer = new Player (context);
        }
        return sPlayer;
    }

    private Player (Context context) {
        mContext = context;
        mXcoordinate = 3;
        mYcoordinate = 5;
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
}
