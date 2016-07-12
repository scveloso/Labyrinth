package com.sveloso.labyrinth.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sveloso.labyrinth.database.PlayerDbSchema.PlayerTable;

/**
 * Created by Veloso on 7/12/2016.
 */
public class PlayerBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_TITLE = "playerBase.db";

    public PlayerBaseHelper(Context context) {
        super(context, DATABASE_TITLE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PlayerTable.TITLE + "(" +
                    "_id integer primary key autoincrement, " +
                    PlayerTable.Cols.UUID + ", " +
                    PlayerTable.Cols.NAME + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
