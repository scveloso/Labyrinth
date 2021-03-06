package com.sveloso.labyrinth.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Veloso on 7/1/2016.
 */
public class Map {

    private static final int STARTING_X = 20;
    private static final int STARTING_Y = 17;
    private static final int ENDING_X = 1;
    private static final int ENDING_Y = 1;

    // The maze
    // 0s are unpassable terrain
    // 1s are passable terrain
    // 2 is the exit of the maze
    // 3s are health packs
    private String row0 =  "0000000000000000000000";
    private String row1 =  "0211111113000031111130";
    private String row2 =  "0000010101111000010000";
    private String row3 =  "0311011101001113110000";
    private String row4 =  "0001010001311000010000";
    private String row5 =  "0101011101000001110000";
    private String row6 =  "0111000101131001011000";
    private String row7 =  "0100000101001111001110";
    private String row8 =  "0101101101131000011000";
    private String row9 =  "0100110101000000010000";
    private String row10 = "0110010101311001110000";
    private String row11 = "0011110101001111000000";
    private String row12 = "0010000001111000011310";
    private String row13 = "0011311001000011110010";
    private String row14 = "0000001001110010000110";
    private String row15 = "0111111000011110011100";
    private String row16 = "0300000001310000010000";
    private String row17 = "0101110111000011110010";
    private String row18 = "0111011101110310010010";
    private String row19 = "0100000100010010111010";
    private String row20 = "0311110113110011101110";
    private String row21 = "0000000000000000000000";

    private static Map sMap;
    private Context mContext;

    private List<List> mRows;

    public static Map get(Context context) {
        if (sMap == null) {
            sMap = new Map(context);
        }
        return sMap;
    }

    private Map (Context context) {
        mContext = context.getApplicationContext();
        mRows = new LinkedList<>();
        createMap();
    }

    // Returns whether there is a 0/1/2 at the coordinate
    public int getType(int x, int y) {
        List<Integer> rowList = mRows.get(y);
        int type = rowList.get(x);
        return type;
    }

    public void setType(int x, int y, int type) {
        List<Integer> rowList = mRows.get(y);
        rowList.set(x, type);
    }

    // Put the String map into lists of rows and convert them to list of ints
    // Add each list of ints (or row) to the list of rows
    private void createMap() {
        List<String> stringRows = new ArrayList<>();
        stringRows.add(row0);
        stringRows.add(row1);
        stringRows.add(row2);
        stringRows.add(row3);
        stringRows.add(row4);
        stringRows.add(row5);
        stringRows.add(row6);
        stringRows.add(row7);
        stringRows.add(row8);
        stringRows.add(row9);
        stringRows.add(row10);
        stringRows.add(row11);
        stringRows.add(row12);
        stringRows.add(row13);
        stringRows.add(row14);
        stringRows.add(row15);
        stringRows.add(row16);
        stringRows.add(row17);
        stringRows.add(row18);
        stringRows.add(row19);
        stringRows.add(row20);
        stringRows.add(row21);

        for (String s : stringRows) {
            List<Integer> points = new ArrayList<>();
            for (char c : s.toCharArray()) {
                points.add(Character.getNumericValue(c));
            }
            mRows.add(points);
        }
    }

    public static int getStartingX() {
        return STARTING_X;
    }

    public static int getStartingY() {
        return STARTING_Y;
    }

    public static int getEndingX() {
        return ENDING_X;
    }

    public static int getEndingY() {
        return ENDING_Y;
    }
}