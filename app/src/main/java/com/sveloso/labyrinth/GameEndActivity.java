package com.sveloso.labyrinth;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by Veloso on 7/7/2016.
 */
public class GameEndActivity extends SingleFragmentActivity {

    private static final String EXTRA_PLAYER_CHECK = "com.sveloso.labyrinth.player_dead";

    @Override
    protected Fragment createFragment() {
        boolean isPlayerDead = getIntent().getBooleanExtra(EXTRA_PLAYER_CHECK, false);

        return GameEndFragment.newInstance(isPlayerDead);
    }

    public static Intent newIntent(Context packageContext, boolean isPlayerDead) {
        Intent i = new Intent(packageContext, GameEndActivity.class);
        i.putExtra(EXTRA_PLAYER_CHECK, isPlayerDead);
        return i;
    }
}
