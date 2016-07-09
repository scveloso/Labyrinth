package com.sveloso.labyrinth;

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

import java.util.Random;

/**
 * Created by Veloso on 7/1/2016.
 */
public class LabyrinthFragment extends Fragment {

    private Map sMap;
    private Player sPlayer;

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
        sMap = Map.get(getActivity());
        sPlayer = Player.get(getActivity());
        sPlayer.setXcoordinate(sMap.getStartingX());
        sPlayer.setYcoordinate(sMap.getStartingY());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.fragment_labyrinth, container, false);

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
                sPlayer.setYcoordinate(sPlayer.getYcoordinate() - 1);
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
                sPlayer.setYcoordinate(sPlayer.getYcoordinate() + 1);
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
                sPlayer.setXcoordinate(sPlayer.getXcoordinate() - 1);
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
                sPlayer.setXcoordinate(sPlayer.getXcoordinate() + 1);
                updateMapDisplay();
                mPlayerImage.setImageResource(R.mipmap.ic_player_right);
                checkPlayerOnExit();
                checkPlayerOnHealthPack();
                updateControls();
                checkEnemyEncounter();
            }
        });

        mPlayerHealth = (ImageView) v.findViewById(R.id.player_health_image_view);

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
        int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sPlayer.getHealth(), getResources().getDisplayMetrics());
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
        if (sPlayer.getXcoordinate() == sMap.getEndingX() &&
                sPlayer.getYcoordinate() == sMap.getEndingY()) {
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
        int playerX = sPlayer.getXcoordinate();
        int playerY = sPlayer.getYcoordinate();
        // if player on health pack
        if (sMap.getType(playerX, playerY) == 3) {
            // give player 40 health points
            if (sPlayer.getHealth() + 50 > 200) {
                sPlayer.setHealth(200);
            } else {
                sPlayer.setHealth(sPlayer.getHealth() + 50);
            }

            // update new player health
            updatePlayerHealth();

            // and remove health pack from map
            sMap.setType(playerX, playerY, 1);
        }
    }

    // Check whether path up/down/left/right is blocked by walls
    // Prevent movement if blocked by walls
    private void updateControls() {
        // Update up button control
        int topCenterX = sPlayer.getXcoordinate();
        int topCenterY = sPlayer.getYcoordinate() - 1;
        int topCenterType = sMap.getType(topCenterX, topCenterY);
        if (topCenterType == 0) {
            mUpButton.setEnabled(false);
        } else if (topCenterType == 1 || topCenterType == 2 || topCenterType == 3) {
            mUpButton.setEnabled(true);
        }

        // Update down button control
        int bottomCenterX = sPlayer.getXcoordinate();
        int bottomCenterY = sPlayer.getYcoordinate() + 1;
        int bottomCenterType = sMap.getType(bottomCenterX, bottomCenterY);
        if (bottomCenterType == 0) {
            mDownButton.setEnabled(false);
        } else if (bottomCenterType == 1 || bottomCenterType == 2 || bottomCenterType == 3) {
            mDownButton.setEnabled(true);
        }

        // Update left button control
        int centerLeftX = sPlayer.getXcoordinate() - 1;
        int centerLeftY = sPlayer.getYcoordinate();
        int centerLeftType = sMap.getType(centerLeftX, centerLeftY);
        if (centerLeftType == 0) {
            mLeftButton.setEnabled(false);
        } else if (centerLeftType == 1 || centerLeftType == 2 || centerLeftType == 3) {
            mLeftButton.setEnabled(true);
        }

        // Update right button control
        int centerRightX = sPlayer.getXcoordinate() + 1;
        int centerRightY = sPlayer.getYcoordinate();
        int centerRightType = sMap.getType(centerRightX, centerRightY);
        if (centerRightType == 0) {
            mRightButton.setEnabled(false);
        } else if (centerRightType == 1 || centerRightType == 2 || centerRightType == 3) {
            mRightButton.setEnabled(true);
        }
    }

    private void updateTopMapBlocks() {
        // Updates display for top left map block
        int topLeftX = sPlayer.getXcoordinate() - 1;
        int topLeftY = sPlayer.getYcoordinate() - 1;
        int topLeftType = sMap.getType(topLeftX, topLeftY);
        if (topLeftType == 0) {
            mTopLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));
        } else if (topLeftType == 1) {
            mTopLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));
        } else if (topLeftType == 2) {
            mTopLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorExit));
        } else if (topLeftType == 3) {
            mTopLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        }

        // Updates display for top center map block
        int topCenterX = sPlayer.getXcoordinate();
        int topCenterY = sPlayer.getYcoordinate() - 1;
        int topCenterType = sMap.getType(topCenterX, topCenterY);
        if (topCenterType == 0) {
            mTopCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));
        } else if (topCenterType == 1) {
            mTopCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));
        } else if (topCenterType == 2) {
            mTopCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorExit));
        } else if (topCenterType == 3) {
            mTopCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        }

        // Updates display for top right map block
        int topRightX = sPlayer.getXcoordinate() + 1;
        int topRightY = sPlayer.getYcoordinate() - 1;
        int topRightType = sMap.getType(topRightX, topRightY);
        if (topRightType == 0) {
            mTopRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));
        } else if (topRightType == 1) {
            mTopRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));
        } else if (topRightType == 2) {
            mTopRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorExit));
        } else if (topRightType == 3) {
            mTopRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        }
    }

    private void updateCenterMapBlocks() {
        // Update center left map block
        int centerLeftX = sPlayer.getXcoordinate() - 1;
        int centerLeftY = sPlayer.getYcoordinate();
        int centerLeftType = sMap.getType(centerLeftX, centerLeftY);
        if (centerLeftType == 0) {
            mCenterLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));
        } else if (centerLeftType == 1) {
            mCenterLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));
        } else if (centerLeftType == 2) {
            mCenterLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorExit));
        } else if (centerLeftType == 3) {
            mCenterLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        }

        // Update center right map block
        int centerRightX = sPlayer.getXcoordinate() + 1;
        int centerRightY = sPlayer.getYcoordinate();
        int centerRightType = sMap.getType(centerRightX, centerRightY);
        if (centerRightType == 0) {
            mCenterRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));
        } else if (centerRightType == 1) {
            mCenterRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));
        } else if (centerRightType == 2) {
            mCenterRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorExit));
        } else if (centerRightType == 3) {
            mCenterRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        }
    }

    private void updateBottomMapBlocks() {
        // Update bottom left map block
        int bottomLeftX = sPlayer.getXcoordinate() - 1;
        int bottomLeftY = sPlayer.getYcoordinate() + 1;
        int bottomLeftType = sMap.getType(bottomLeftX, bottomLeftY);
        if (bottomLeftType == 0) {
            mBottomLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));
        } else if (bottomLeftType == 1) {
            mBottomLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));
        } else if (bottomLeftType == 2) {
            mBottomLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorExit));
        } else if (bottomLeftType == 3) {
            mBottomLeft.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        }

        // Update bottom center map block
        int bottomCenterX = sPlayer.getXcoordinate();
        int bottomCenterY = sPlayer.getYcoordinate() + 1;
        int bottomCenterType = sMap.getType(bottomCenterX, bottomCenterY);
        if (bottomCenterType == 0) {
            mBottomCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));
        } else if (bottomCenterType == 1) {
            mBottomCenter .setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));
        } else if (bottomCenterType == 2) {
            mBottomCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorExit));
        } else if (bottomCenterType == 3) {
            mBottomCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        }

        // Update bottom right map block
        int bottomRightX = sPlayer.getXcoordinate() + 1;
        int bottomRightY = sPlayer.getYcoordinate() + 1;
        int bottomRightType = sMap.getType(bottomRightX, bottomRightY);
        if (bottomRightType == 0) {
            mBottomRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));
        } else if (bottomRightType == 1) {
            mBottomRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));
        } else if (bottomRightType == 2) {
            mBottomRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorExit));
        } else if (bottomRightType == 3) {
            mBottomRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        }
    }
}
