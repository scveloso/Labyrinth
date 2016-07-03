package com.sveloso.labyrinth;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Veloso on 7/1/2016.
 */
public class Map {

    private static Map sMap;
    private Context mContext;

    private List<List> mMap;

    public static Map get(Context context) {
        if (sMap == null) {
            sMap = new Map(context);
        }
        return sMap;
    }

    private Map (Context context) {
        mContext = context.getApplicationContext();
        mMap = new LinkedList<>();

        List<Integer> row1 = new LinkedList<>();
        List<Integer> row2 = new LinkedList<>();
        List<Integer> row3 = new LinkedList<>();
        List<Integer> row4 = new LinkedList<>();
        List<Integer> row5 = new LinkedList<>();
        List<Integer> row6 = new LinkedList<>();
        List<Integer> row7 = new LinkedList<>();

        // Row 1
        for (int i = 0; i < 7; i++) {
            row1.add(0);
        }

        // Row 2
        row2.add(0);
        row2.add(1); // ending point
        row2.add(0);
        row2.add(1);
        row2.add(0);
        row2.add(0);
        row2.add(0);

        // Row 3
        row3.add(0);
        row3.add(1);
        row3.add(1);
        row3.add(1);
        row3.add(1);
        row3.add(1);
        row3.add(0);

        // Row 4
        row4.add(0);
        row4.add(0);
        row4.add(0);
        row4.add(0);
        row4.add(0);
        row4.add(1);
        row4.add(0);

        // Row 5
        row5.add(0);
        row5.add(1);
        row5.add(1);
        row5.add(1);
        row5.add(1);
        row5.add(1);
        row5.add(0);

        // Row 6
        row6.add(0);
        row6.add(1);
        row6.add(0);
        row6.add(1); // starting point
        row6.add(0);
        row6.add(1);
        row6.add(0);

        // Row 7
        for (int i = 0; i < 7; i++) {
            row7.add(0);
        }

        mMap.add(row1);
        mMap.add(row2);
        mMap.add(row3);
        mMap.add(row4);
        mMap.add(row5);
        mMap.add(row6);
        mMap.add(row7);
    }

    // Returns whether there is a 0 or a 1 at the coordinate
    public int getType(int x, int y) {
        List<Integer> rowList = mMap.get(y);
        int type = rowList.get(x);
        return type;
    }
}