package com.sveloso.labyrinth.controller;

import android.support.v4.app.Fragment;

import com.sveloso.labyrinth.util.SingleFragmentActivity;

public class LabyrinthActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return LabyrinthFragment.newInstance();
    }

}
