package com.sveloso.labyrinth.controller;

import android.support.v4.app.Fragment;

import com.sveloso.labyrinth.util.SingleFragmentActivity;

/**
 * Created by Veloso on 7/11/2016.
 */
public class TitleScreenActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return TitleScreenFragment.newInstance();
    }

}
