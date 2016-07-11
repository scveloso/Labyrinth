package com.sveloso.labyrinth.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sveloso.labyrinth.R;

/**
 * Created by Veloso on 7/11/2016.
 */
public class TitleScreenFragment extends Fragment {

    private Button mNewGameButton;
    private Button mLoadSaveButton;

    public static TitleScreenFragment newInstance() {
        return new TitleScreenFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_title_screen, container, false);

        mNewGameButton = (Button) v.findViewById(R.id.button_new_game);
        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLabyrinthGame();
            }
        });

        mLoadSaveButton = (Button) v.findViewById(R.id.button_load_save);
        mLoadSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSavedGames();
            }
        });

        return v;
    }

    private void startLabyrinthGame() {
        Intent i = new Intent(getActivity(), CreatePlayerActivity.class);
        startActivity(i);
    }

    private void viewSavedGames() {
        // Create new intent with LoadGameActivity
        // View saved games
    }
}
