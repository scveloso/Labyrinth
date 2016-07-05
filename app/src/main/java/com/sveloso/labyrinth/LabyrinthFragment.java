package com.sveloso.labyrinth;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
    private ImageView mCenterRight;
    private ImageView mBottomLeft;
    private ImageView mBottomCenter;
    private ImageView mBottomRight;

    private ImageButton mUpButton;
    private ImageButton mDownButton;
    private ImageButton mLeftButton;
    private ImageButton mRightButton;

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
                updateControls();
                checkEnemyEncounter();
            }
        });

        updateMapDisplay();
        updateControls();
        return v;
    }

    private void updateMapDisplay() {
        updateTopMapBlocks();
        updateCenterMapBlocks();
        updateBottomMapBlocks();
    }

    // 20% chance for enemy encounter
    private void checkEnemyEncounter() {
        Random random = new Random();
        int chance = random.nextInt(5);
        if (chance == 1) {
            Intent intent = new Intent(getActivity(), CombatActivity.class);
            startActivity(intent);
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
        } else if (topCenterType == 1) {
            mUpButton.setEnabled(true);
        }

        // Update down button control
        int bottomCenterX = sPlayer.getXcoordinate();
        int bottomCenterY = sPlayer.getYcoordinate() + 1;
        int bottomCenterType = sMap.getType(bottomCenterX, bottomCenterY);
        if (bottomCenterType == 0) {
            mDownButton.setEnabled(false);
        } else if (bottomCenterType == 1) {
            mDownButton.setEnabled(true);
        }

        // Update left button control
        int centerLeftX = sPlayer.getXcoordinate() - 1;
        int centerLeftY = sPlayer.getYcoordinate();
        int centerLeftType = sMap.getType(centerLeftX, centerLeftY);
        if (centerLeftType == 0) {
            mLeftButton.setEnabled(false);
        } else if (centerLeftType == 1) {
            mLeftButton.setEnabled(true);
        }

        // Update right button control
        int centerRightX = sPlayer.getXcoordinate() + 1;
        int centerRightY = sPlayer.getYcoordinate();
        int centerRightType = sMap.getType(centerRightX, centerRightY);
        if (centerRightType == 0) {
            mRightButton.setEnabled(false);
        } else if (centerRightType == 1) {
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
        }

        // Updates display for top center map block
        int topCenterX = sPlayer.getXcoordinate();
        int topCenterY = sPlayer.getYcoordinate() - 1;
        int topCenterType = sMap.getType(topCenterX, topCenterY);
        if (topCenterType == 0) {
            mTopCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));
        } else if (topCenterType == 1) {
            mTopCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));
        }

        // Updates display for top right map block
        int topRightX = sPlayer.getXcoordinate() + 1;
        int topRightY = sPlayer.getYcoordinate() - 1;
        int topRightType = sMap.getType(topRightX, topRightY);
        if (topRightType == 0) {
            mTopRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));
        } else if (topRightType == 1) {
            mTopRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));
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
        }

        // Update center right map block
        int centerRightX = sPlayer.getXcoordinate() + 1;
        int centerRightY = sPlayer.getYcoordinate();
        int centerRightType = sMap.getType(centerRightX, centerRightY);
        if (centerRightType == 0) {
            mCenterRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));
        } else if (centerRightType == 1) {
            mCenterRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));
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
        }

        // Update bottom center map block
        int bottomCenterX = sPlayer.getXcoordinate();
        int bottomCenterY = sPlayer.getYcoordinate() + 1;
        int bottomCenterType = sMap.getType(bottomCenterX, bottomCenterY);
        if (bottomCenterType == 0) {
            mBottomCenter.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));
        } else if (bottomCenterType == 1) {
            mBottomCenter .setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));
        }

        // Update bottom right map block
        int bottomRightX = sPlayer.getXcoordinate() + 1;
        int bottomRightY = sPlayer.getYcoordinate() + 1;
        int bottomRightType = sMap.getType(bottomRightX, bottomRightY);
        if (bottomRightType == 0) {
            mBottomRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWall));
        } else if (bottomRightType == 1) {
            mBottomRight.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPath));
        }
    }
}
