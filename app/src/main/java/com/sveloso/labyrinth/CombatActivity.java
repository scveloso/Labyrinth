package com.sveloso.labyrinth;

import android.support.v4.app.Fragment;

/**
 * Created by Veloso on 7/5/2016.
 */
public class CombatActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return CombatFragment.newInstance();
    }
}
