package com.sveloso.labyrinth.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sveloso.labyrinth.database.PlayerBaseHelper;
import com.sveloso.labyrinth.database.PlayerCursorWrapper;
import com.sveloso.labyrinth.database.PlayerDbSchema.PlayerTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Veloso on 7/12/2016.
 */
public class PlayerManager {

    private static PlayerManager sPlayerManager;
    private Player mCurrentPlayer;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static PlayerManager get(Context context) {
        if (sPlayerManager == null) {
            sPlayerManager = new PlayerManager(context);
        }
        return sPlayerManager;
    }


    private PlayerManager(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new PlayerBaseHelper(mContext).getWritableDatabase();

    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();

        PlayerCursorWrapper cursor = queryPlayers(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                players.add(cursor.getPlayer());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return players;
    }

    public Player getPlayer(UUID id) {
        PlayerCursorWrapper cursor = queryPlayers(
                PlayerTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getPlayer();
        } finally {
            cursor.close();
        }
    }

    public void addPlayer(Player player) {
        ContentValues values = getContentValues(player);

        mDatabase.insert(PlayerTable.TITLE, null, values);
    }

    public void updatePlayer(Player player) {
        String uuidString = player.getId().toString();
        ContentValues values = getContentValues(player);

        mDatabase.update(PlayerTable.TITLE, values,
                PlayerTable.Cols.UUID + " = ?",
                new String [] {uuidString});
    }

    public Player getCurrentPlayer() {
        return mCurrentPlayer;
    }

    public void setCurrentPlayer(UUID uuid) {
        mCurrentPlayer = getPlayer(uuid);
    }

    private static ContentValues getContentValues(Player player) {
        ContentValues values = new ContentValues();
        values.put(PlayerTable.Cols.UUID, player.getId().toString());
        values.put(PlayerTable.Cols.NAME, player.getName());

        return values;
    }

    private PlayerCursorWrapper queryPlayers(String whereClause, String [] whereArgs) {
        Cursor cursor = mDatabase.query(
                PlayerTable.TITLE,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new PlayerCursorWrapper(cursor);
    }
}
