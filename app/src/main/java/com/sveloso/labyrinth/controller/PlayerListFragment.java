package com.sveloso.labyrinth.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sveloso.labyrinth.R;
import com.sveloso.labyrinth.model.Player;
import com.sveloso.labyrinth.model.PlayerManager;

import java.util.List;


/**
 * Created by Veloso on 7/12/2016.
 */
public class PlayerListFragment extends Fragment {

    private RecyclerView mPlayerRecyclerView;
    private PlayerAdapter mAdapter;
    private PlayerManager mPlayerManager;

    public static PlayerListFragment newInstance() {
        return new PlayerListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayerManager = PlayerManager.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player_list, container, false);

        mPlayerRecyclerView = (RecyclerView) v.findViewById(R.id.player_recycler_view);
        mPlayerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    private void updateUI() {
        PlayerManager playerManager = PlayerManager.get(getActivity());
        List<Player> players = playerManager.getPlayers();

        mAdapter = new PlayerAdapter(players);
        mPlayerRecyclerView.setAdapter(mAdapter);
    }

    private class PlayerHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

        private Player mPlayer;

        private TextView mPlayerNameTextView;

        public PlayerHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mPlayerNameTextView = (TextView) itemView.findViewById(R.id.list_item_player_name_text_view);
        }

        public void bindPlayer(Player player) {
            mPlayer = player;
            mPlayerNameTextView.setText(mPlayer.getName());
        }

        @Override
        public void onClick(View view) {
            mPlayerManager.setCurrentPlayer(mPlayer.getId());
            Intent i = new Intent(getActivity(), LabyrinthActivity.class);
            startActivity(i);
        }
    }

    private class PlayerAdapter extends RecyclerView.Adapter<PlayerHolder> {

        private List<Player> mPlayers;

        public PlayerAdapter (List<Player> players) {
            mPlayers = players;
        }

        @Override
        public PlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_player, parent, false);
            return new PlayerHolder(view);
        }

        @Override
        public void onBindViewHolder(PlayerHolder holder, int position) {
            Player player = mPlayers.get(position);
            holder.bindPlayer(player);
        }

        @Override
        public int getItemCount() {
            return mPlayers.size();
        }
    }

}
