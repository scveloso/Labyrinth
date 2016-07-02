package com.sveloso.labyrinth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

    public static LabyrinthFragment newInstance() {
        return new LabyrinthFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sMap = Map.get(getActivity());
        sPlayer = Player.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.fragment_labyrinth, container, false);
        
        return v;
    }

}
