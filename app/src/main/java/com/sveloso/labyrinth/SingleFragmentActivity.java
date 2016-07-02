package com.sveloso.labyrinth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Veloso on 5/31/2016.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    // Subclasses of SingleFragmentActivity will implement this method to return an instance
    // of the fragment that the activity is hosting
    protected abstract Fragment createFragment();

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
