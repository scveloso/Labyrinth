package com.sveloso.labyrinth.controller;

import android.support.v4.app.Fragment;

import com.sveloso.labyrinth.util.SingleFragmentActivity;

/**
 * Created by Veloso on 7/5/2016.
 */
public class CombatActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return CombatFragment.newInstance();
    }
}
