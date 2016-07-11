package com.sveloso.labyrinth;

import android.support.v4.app.Fragment;

/**
 * Created by Veloso on 7/11/2016.
 */
public class TitleScreenActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return TitleScreenFragment.newInstance();
    }

}
