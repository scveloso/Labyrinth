package com.sveloso.labyrinth.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sveloso.labyrinth.model.Map;
import com.sveloso.labyrinth.model.Player;
import com.sveloso.labyrinth.R;
import com.sveloso.labyrinth.model.PlayerManager;

import java.util.Random;

/**
 * Created by Veloso on 7/1/2016.
 */
public class LabyrinthFragment extends Fragment {

    private Map mMap;
    private Player mPlayer;
    private PlayerManager mPlayerManager;

    private TextView mPlayerNameTextView;

    private ImageView mTopLeft;
    private ImageView mTopCenter;
    private ImageView mTopRight;
    private ImageView mCenterLeft;
    private ImageView mPlayerImage;
    private ImageView mCenterRight;
    private ImageView mBottomLeft;
    private ImageView mBottomCenter;
    private ImageView mBottomRight;

    private ImageButton mUpButton;
    private ImageButton mDownButton;
    private ImageButton mLeftButton;
    private ImageButton mRightButton;

    private ImageView mPlayerHealth;

    public static LabyrinthFragment newInstance() {
        return new LabyrinthFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMap = Map.get(getActivity());
        mPlayerManager = PlayerManager.get(getActivity());
        mPlayer = mPlayerManager.getCurrentPlayer();
        mPlayer.setHealth(200);
        mPlayer.setXcoordinate(mMap.getStartingX());
        mPlayer.setYcoordinate(mMap.getStartingY());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.fragment_labyrinth, container, false);

        mPlayerNameTextView = (TextView) v.findViewById(R.id.labyrinth_player_name_text_view);
        mPlayerNameTextView.setText(mPlayer.getName());

        mTopLeft = (ImageView) v.findViewById(R.id.top_left_map_block);
        mTopCenter = (ImageView) v.findViewById(R.id.top_center_map_block);
        mTopRight = (ImageView) v.findViewById(R.id.top_right_map_block);

        mCenterLeft = (ImageView) v.findViewById(R.id.center_left_map_block);
        mPlayerImage = (ImageView) v.findViewById(R.id.player_map_block);
        mPlayerImage.setImageResource(R.mipmap.ic_player_front);

        mCenterRight = (ImageView) v.findViewById(R.id.center_right_map_block);

        mBottomLeft = (ImageView) v.findViewById(R.id.bottom_left_map_block);
        mBottomCenter = (ImageView) v.findViewById(R.id.bottom_center_map_block);
        mBottomRight = (ImageView) v.findViewById(R.id.bottom_right_map_block);

        mUpButton = (ImageButton) v.findViewById(R.id.up_button);
        mUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.setYcoordinate(mPlayer.getYcoordinate() - 1);
                updateMapDisplay();
                mPlayerImage.setImageResource(R.mipmap.ic_player_back);
                checkPlayerOnExit();
                checkPlayerOnHealthPack();
                updateControls();
                checkEnemyEncounter();
            }
        });

        mDownButton = (ImageButton) v.findViewById(R.id.down_button);
        mDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.setYcoordinate(mPlayer.getYcoordinate() + 1);
                updateMapDisplay();
                mPlayerImage.setImageResource(R.mipmap.ic_player_front);
                checkPlayerOnExit();
                checkPlayerOnHealthPack();
                updateControls();
                checkEnemyEncounter();
            }
        });

        mLeftButton = (ImageButton) v.findViewById(R.id.left_button);
        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.setXcoordinate(mPlayer.getXcoordinate() - 1);
                updateMapDisplay();
                mPlayerImage.setImageResource(R.mipmap.ic_player_left);
                checkPlayerOnExit();
                checkPlayerOnHealthPack();
                updateControls();
                checkEnemyEncounter();
            }
        });

        mRightButton = (ImageButton) v.findViewById(R.id.right_button);
        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.setXcoordinate(mPlayer.getXcoordinate() + 1);
                updateMapDisplay();
                mPlayerImage.setImageResource(R.mipmap.ic_player_right);
                checkPlayerOnExit();
                checkPlayerOnHealthPack();
                updateControls();
                checkEnemyEncounter();
            }
        });

        mPlayerHealth = (ImageView) v.findViewById(R.id.player_health_image_view);

        updatePlayerHealth();
        updateMapDisplay();
        updateControls();
        return v;
    }

    private void updateMapDisplay() {
        updateTopMapBlocks();
        updateCenterMapBlocks();
        updateBottomMapBlocks();
    }

    private void updatePlayerHealth() {
        int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPlayer.getHealth(), getResources().getDisplayMetrics());
        mPlayerHealth.getLayoutParams().width = dimensionInDp;
        mPlayerHealth.requestLayout();
    }

    // 10% chance for enemy encounter
    private void checkEnemyEncounter() {
        Random random = new Random();
        int chance = random.nextInt(10);
        if (chance == 1) {
            Intent intent = new Intent(getActivity(), CombatActivity.class);
            startActivity(intent);
        }
    }

    private void checkPlayerOnExit() {
        if (mPlayer.getXcoordinate() == mMap.getEndingX() &&
                mPlayer.getYcoordinate() == mMap.getEndingY()) {
            Intent i = GameEndActivity.newIntent(getActivity(), false);
            startActivity(i);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePlayerHealth();
    }

    private void checkPlayerOnHealthPack() {
        int playerX = mPlayer.getXcoordinate();
        int playerY = mPlayer.getYcoordinate();
        // if player on health pack
        if (mMap.getType(playerX, playerY) == 3) {
            // give player 40 health points
            if (mPlayer.getHealth() + 50 > 200) {
                mPlayer.setHealth(200);
            } else {
                mPlayer.setHealth(mPlayer.getHealth() + 50);
            }

            // update new player health
            updatePlayerHealth();

            // and remove health pack from map
            mMap.setType(playerX, playerY, 1);
        }
    }

    // Check whether path up/down/left/right is blocked by walls
    // Prevent movement if blocked by walls
    private void updateControls() {
        // Update up button control
        int topCenterX = mPlayer.getXcoordinate();
        int topCenterY = mPlayer.getYcoordinate() - 1;
        int topCenterType = mMap.getType(topCenterX, topCenterY);
        if (topCenterType == 0) {
            mUpButton.setEnabled(false);
        } else if (topCenterType == 1 || topCenterType == 2 || topCenterType == 3) {
            mUpButton.setEnabled(true);
        }

        // Update down button control
        int bottomCenterX = mPlayer.getXcoordinate();
        int bottomCenterY = mPlayer.getYcoordinate() + 1;
        int bottomCenterType = mMap.getType(bottomCenterX, bottomCenterY);
        if (bottomCenterType == 0) {
            mDownButton.setEnabled(false);
        } else if (bottomCenterType == 1 || bottomCenterType == 2 || bottomCenterType == 3) {
            mDownButton.setEnabled(true);
        }

        // Update left button control
        int centerLeftX = mPlayer.getXcoordinate() - 1;
        int centerLeftY = mPlayer.getYcoordinate();
        int centerLeftType = mMap.getType(centerLeftX, centerLeftY);
        if (centerLeftType == 0) {
            mLeftButton.setEnabled(false);
        } else if (centerLeftType == 1 || centerLeftType == 2 || centerLeftType == 3) {
            mLeftButton.setEnabled(true);
        }

        // Update right button control
        int centerRightX = mPlayer.getXcoordinate() + 1;
        int centerRightY = mPlayer.getYcoordinate();
        int centerRightType = mMap.getType(centerRightX, centerRightY);
        if (centerRightType == 0) {
            mRightButton.setEnabled(false);
        } else if (centerRightType == 1 || centerRightType == 2 || centerRightType == 3) {
            mRightButton.setEnabled(true);
        }
    }

    private void updateTopMapBlocks() {
        // Updates display for top left map block
        int topLeftX = mPlayer.getXcoordinate() - 1;
        int topLeftY = mPlayer.getYcoordinate() - 1;
        int topLeftType = mMap.getType(topLeftX, topLeftY);
        if (topLeftType == 0) {
            mTopLeft.setImageResource(R.mipmap.ic_walls);
            mTopLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));

        } else if (topLeftType == 1) {
            mTopLeft.setImageResource(R.mipmap.ic_new_path);
            mTopLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));

        } else if (topLeftType == 2) {
            mTopLeft.setImageResource(R.mipmap.ic_exit);
            mTopLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorExit));

        } else if (topLeftType == 3) {
            mTopLeft.setImageResource(R.mipmap.ic_health_pack);
            mTopLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorHealth));

        }

        // Updates display for top center map block
        int topCenterX = mPlayer.getXcoordinate();
        int topCenterY = mPlayer.getYcoordinate() - 1;
        int topCenterType = mMap.getType(topCenterX, topCenterY);
        if (topCenterType == 0) {
            mTopCenter.setImageResource(R.mipmap.ic_walls);
            mTopCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));

        } else if (topCenterType == 1) {
            mTopCenter.setImageResource(R.mipmap.ic_new_path);
            mTopCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));

        } else if (topCenterType == 2) {
            mTopCenter.setImageResource(R.mipmap.ic_exit);
            mTopCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorExit));

        } else if (topCenterType == 3) {
            mTopCenter.setImageResource(R.mipmap.ic_health_pack);
            mTopCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorHealth));

        }

        // Updates display for top right map block
        int topRightX = mPlayer.getXcoordinate() + 1;
        int topRightY = mPlayer.getYcoordinate() - 1;
        int topRightType = mMap.getType(topRightX, topRightY);
        if (topRightType == 0) {
            mTopRight.setImageResource(R.mipmap.ic_walls);
            mTopRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));

        } else if (topRightType == 1) {
            mTopRight.setImageResource(R.mipmap.ic_new_path);
            mTopRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));

        } else if (topRightType == 2) {
            mTopRight.setImageResource(R.mipmap.ic_exit);
            mTopRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorExit));

        } else if (topRightType == 3) {
            mTopRight.setImageResource(R.mipmap.ic_health_pack);
            mTopRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorHealth));

        }
    }

    private void updateCenterMapBlocks() {
        // Update center left map block
        int centerLeftX = mPlayer.getXcoordinate() - 1;
        int centerLeftY = mPlayer.getYcoordinate();
        int centerLeftType = mMap.getType(centerLeftX, centerLeftY);
        if (centerLeftType == 0) {
            mCenterLeft.setImageResource(R.mipmap.ic_walls);
            mCenterLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));

        } else if (centerLeftType == 1) {
            mCenterLeft.setImageResource(R.mipmap.ic_new_path);
            mCenterLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));

        } else if (centerLeftType == 2) {
            mCenterLeft.setImageResource(R.mipmap.ic_exit);
            mCenterLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorExit));

        } else if (centerLeftType == 3) {
            mCenterLeft.setImageResource(R.mipmap.ic_health_pack);
            mCenterLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorHealth));

        }

        // Update center right map block
        int centerRightX = mPlayer.getXcoordinate() + 1;
        int centerRightY = mPlayer.getYcoordinate();
        int centerRightType = mMap.getType(centerRightX, centerRightY);
        if (centerRightType == 0) {
            mCenterRight.setImageResource(R.mipmap.ic_walls);
            mCenterRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));

        } else if (centerRightType == 1) {
            mCenterRight.setImageResource(R.mipmap.ic_new_path);
            mCenterRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));

        } else if (centerRightType == 2) {
            mCenterRight.setImageResource(R.mipmap.ic_exit);
            mCenterRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorExit));

        } else if (centerRightType == 3) {
            mCenterRight.setImageResource(R.mipmap.ic_health_pack);
            mCenterRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorHealth));

        }
    }

    private void updateBottomMapBlocks() {
        // Update bottom left map block
        int bottomLeftX = mPlayer.getXcoordinate() - 1;
        int bottomLeftY = mPlayer.getYcoordinate() + 1;
        int bottomLeftType = mMap.getType(bottomLeftX, bottomLeftY);
        if (bottomLeftType == 0) {
            mBottomLeft.setImageResource(R.mipmap.ic_walls);
            mBottomLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));
        } else if (bottomLeftType == 1) {
            mBottomLeft.setImageResource(R.mipmap.ic_new_path);
            mBottomLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));
        } else if (bottomLeftType == 2) {
            mBottomLeft.setImageResource(R.mipmap.ic_exit);
            mBottomLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorExit));
        } else if (bottomLeftType == 3) {
            mBottomLeft.setImageResource(R.mipmap.ic_health_pack);
            mBottomLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorHealth));

        }

        // Update bottom center map block
        int bottomCenterX = mPlayer.getXcoordinate();
        int bottomCenterY = mPlayer.getYcoordinate() + 1;
        int bottomCenterType = mMap.getType(bottomCenterX, bottomCenterY);
        if (bottomCenterType == 0) {
            mBottomCenter.setImageResource(R.mipmap.ic_walls);
            mBottomCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));
        } else if (bottomCenterType == 1) {
            mBottomCenter.setImageResource(R.mipmap.ic_new_path);
            mBottomCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));

        } else if (bottomCenterType == 2) {
            mBottomCenter.setImageResource(R.mipmap.ic_exit);
            mBottomCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorExit));

        } else if (bottomCenterType == 3) {
            mBottomCenter.setImageResource(R.mipmap.ic_health_pack);
            mBottomCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorHealth));

        }

        // Update bottom right map block
        int bottomRightX = mPlayer.getXcoordinate() + 1;
        int bottomRightY = mPlayer.getYcoordinate() + 1;
        int bottomRightType = mMap.getType(bottomRightX, bottomRightY);
        if (bottomRightType == 0) {
            mBottomRight.setImageResource(R.mipmap.ic_walls);
            mBottomRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));

        } else if (bottomRightType == 1) {
            mBottomRight.setImageResource(R.mipmap.ic_new_path);
            mBottomRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));

        } else if (bottomRightType == 2) {
            mBottomRight.setImageResource(R.mipmap.ic_exit);
            mBottomRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorExit));

        } else if (bottomRightType == 3) {
            mBottomRight.setImageResource(R.mipmap.ic_health_pack);
            mBottomRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorHealth));

        }
    }
}
