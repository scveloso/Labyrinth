package com.sveloso.labyrinth.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.sveloso.labyrinth.database.PlayerDbSchema.PlayerTable;
import com.sveloso.labyrinth.model.Player;

import java.util.UUID;

/**
 * Created by Veloso on 7/12/2016.
 */
public class PlayerCursorWrapper extends CursorWrapper {

    public PlayerCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Player getPlayer() {
        String uuidString = getString(getColumnIndex(PlayerTable.Cols.UUID));
        String title = getString(getColumnIndex(PlayerTable.Cols.NAME));

        Player player = new Player(UUID.fromString(uuidString));
        player.setName(title);

        return player;
    }

}
