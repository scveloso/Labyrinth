package com.sveloso.labyrinth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Veloso on 7/5/2016.
 */
public class CombatFragment extends Fragment {

    private Player sPlayer;

    private ImageView mEnemyImage;
    private TextView mEnemyName;
    private ImageView mEnemyHealth;

    private TextView mPlayerName;
    private ImageView mPlayerHealth;
    private Button mPlayerAttack;

    public static CombatFragment newInstance() {
        return new CombatFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPlayer = Player.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_combat, container, false);

        // Enemy views
        mEnemyImage = (ImageView) v.findViewById(R.id.enemy_pic_image_view);
        mEnemyImage.setImageResource(R.mipmap.ic_snake_enemy);
        mEnemyName = (TextView) v.findViewById(R.id.enemy_name_text_view);
        mEnemyHealth = (ImageView) v.findViewById(R.id.enemy_health_image_view);

        // Player views
        mPlayerName = (TextView) v.findViewById(R.id.player_name_text_view);

        mPlayerHealth = (ImageView) v.findViewById(R.id.player_health_image_view);
        updatePlayerHealth();

        mPlayerAttack = (Button) v.findViewById(R.id.player_attack_button);

        mPlayerAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCombat();
                // Hit enemy
                // Enemy hits u
                // Check if enemy dead
                // Check if u dead
            }
        });

        return v;
    }

    private void updateCombat() {
        // Attack each other

        // Player attack enemy
        int playerAttack = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        mEnemyHealth.getLayoutParams().width -= playerAttack;
        mEnemyHealth.requestLayout();

        // Enemy attack player
        sPlayer.setHealth(sPlayer.getHealth() - 10);
        updatePlayerHealth();

        // Check if either are dead
        if (mPlayerHealth.getLayoutParams().width == 0) {
            Intent i = GameEndActivity.newIntent(getActivity(), true);
            startActivity(i);
        } else if (mEnemyHealth.getLayoutParams().width == 0) {
            getActivity().finish();
        }
    }

    // Set player health bar to player health
    private void updatePlayerHealth() {
        int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sPlayer.getHealth(), getResources().getDisplayMetrics());
        mPlayerHealth.getLayoutParams().width = dimensionInDp;
        mPlayerHealth.requestLayout();
    }
}
