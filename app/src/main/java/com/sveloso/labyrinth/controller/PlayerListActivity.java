package com.sveloso.labyrinth.controller;

import android.support.v4.app.Fragment;

import com.sveloso.labyrinth.util.SingleFragmentActivity;

/**
 * Created by Veloso on 7/12/2016.
 */
public class PlayerListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return PlayerListFragment.newInstance();
    }

}
