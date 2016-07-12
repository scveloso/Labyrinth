package com.sveloso.labyrinth.database;

/**
 * Created by Veloso on 7/12/2016.
 */
public class PlayerDbSchema {

    public static final class PlayerTable {
        public static final String TITLE = "players";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
        }
    }

}
