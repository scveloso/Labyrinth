package com.sveloso.labyrinth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Veloso on 7/11/2016.
 */
public class CreatePlayerFragment extends Fragment{

    private Player sPlayer;

    private EditText mPlayerNameField;
    private Button mCreatePlayerButton;
    private ImageView mPlayerImageView;

    public static CreatePlayerFragment newInstance() {
        return new CreatePlayerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPlayer = Player.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_player, container, false);

        mPlayerNameField = (EditText) v.findViewById(R.id.player_name_edit_text);

        mCreatePlayerButton = (Button) v.findViewById(R.id.button_create_player);
        mCreatePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sPlayer.setName(mPlayerNameField.getText().toString());
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
