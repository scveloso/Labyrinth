package com.sveloso.labyrinth.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sveloso.labyrinth.model.Player;
import com.sveloso.labyrinth.R;
import com.sveloso.labyrinth.model.PlayerManager;

import java.util.List;

/**
 * Created by Veloso on 7/11/2016.
 */
public class CreatePlayerFragment extends Fragment{

    private Player mPlayer;
    private PlayerManager mPlayerManager;

    private EditText mPlayerNameField;
    private Button mCreatePlayerButton;
    private ImageView mPlayerImageView;

    public static CreatePlayerFragment newInstance() {
        return new CreatePlayerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayerManager = PlayerManager.get(getActivity());
        mPlayer = new Player();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_player, container, false);

        mPlayerNameField = (EditText) v.findViewById(R.id.player_name_edit_text);

        mCreatePlayerButton = (Button) v.findViewById(R.id.button_create_player);
        mCreatePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.setName(mPlayerNameField.getText().toString());
                mPlayerManager.addPlayer(mPlayer);
                mPlayerManager.setCurrentPlayer(mPlayer.getId());
                Intent i = new Intent(getActivity(), LabyrinthActivity.class);
                startActivity(i);
            }
        });

        mPlayerImageView = (ImageView) v.findViewById(R.id.create_player_image_view);
        mPlayerImageView.setImageResource(R.mipmap.ic_player_front);
        mPlayerImageView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));

        return v;
    }
}
