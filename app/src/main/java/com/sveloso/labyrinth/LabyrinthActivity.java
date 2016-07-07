package com.sveloso.labyrinth;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LabyrinthActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return LabyrinthFragment.newInstance();
    }

}
