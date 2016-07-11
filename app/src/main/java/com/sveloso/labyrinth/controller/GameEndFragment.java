package com.sveloso.labyrinth.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sveloso.labyrinth.R;

/**
 * Created by Veloso on 7/7/2016.
 */
public class GameEndFragment extends Fragment {

    private static final String ARG_PLAYER_DEAD_BOOLEAN = "is_player_dead";
    private static final String TAG = "Start over error:";

    private Boolean mIsPlayerDead;
    private TextView mGameEndText;
    private Button mBackInsideButton;
    private Button mExitMazeButton;

    public static GameEndFragment newInstance(boolean isPlayerDead) {
        Bundle args = new Bundle();
        args.putBoolean(ARG_PLAYER_DEAD_BOOLEAN, isPlayerDead);

        GameEndFragment gameEndFragment = new GameEndFragment();
        gameEndFragment.setArguments(args);
        return gameEndFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_end, container, false);

        mGameEndText = (TextView) v.findViewById(R.id.game_end_text_view);
        mBackInsideButton = (Button) v.findViewById(R.id.back_inside_button);
        mExitMazeButton = (Button) v.findViewById(R.id.exit_maze_button);


        if (mIsPlayerDead) {
            mGameEndText.setText(R.string.player_dead_text);
            mBackInsideButton.setText("Start over!");
            mExitMazeButton.setText("RIP");
        } else {
            mGameEndText.setText(R.string.player_exit_text);
            mBackInsideButton.setText("Back to the maze!");
            mExitMazeButton.setText("Enjoy freedom!");
        }

        mBackInsideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Restart application
                Intent i = new Intent (getActivity(), LabyrinthActivity.class);
                startActivity(i);
                //startOver(getActivity());
            }
        });

        mExitMazeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close application
                getActivity().finishAffinity();
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsPlayerDead = getArguments().getBoolean(ARG_PLAYER_DEAD_BOOLEAN);
    }

//    public static void startOver (Context c) {
//        try {
//            //check if the context is given
//            if (c != null) {
//                //fetch the packagemanager so we can get the default launch activity
//                // (you can replace this intent with any other activity if you want
//                PackageManager pm = c.getPackageManager();
//                //check if we got the PackageManager
//                if (pm != null) {
//                    //create the intent with the default start activity for your application
//                    Intent mStartActivity = pm.getLaunchIntentForPackage(
//                            c.getPackageName()
//                    );
//                    if (mStartActivity != null) {
//                        mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        //create a pending intent so the application is restarted after System.exit(0) was called.
//                        // We use an AlarmManager to call this intent in 100ms
//                        int mPendingIntentId = 223344;
//                        PendingIntent mPendingIntent = PendingIntent
//                                .getActivity(c, mPendingIntentId, mStartActivity,
//                                        PendingIntent.FLAG_CANCEL_CURRENT);
//                        AlarmManager mgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
//                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
//                        //kill the application
//                        System.exit(0);
//                    } else {
//                        Log.e(TAG, "Was not able to restart application, mStartActivity null");
//                    }
//                } else {
//                    Log.e(TAG, "Was not able to restart application, PM null");
//                }
//            } else {
//                Log.e(TAG, "Was not able to restart application, Context null");
//            }
//        } catch (Exception ex) {
//            Log.e(TAG, "Was not able to restart application");
//        }
//    }

}
