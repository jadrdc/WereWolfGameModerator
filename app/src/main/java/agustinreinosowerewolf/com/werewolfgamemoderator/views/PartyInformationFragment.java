package agustinreinosowerewolf.com.werewolfgamemoderator.views;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import agustinreinosowerewolf.com.werewolfgamemoderator.R;
import agustinreinosowerewolf.com.werewolfgamemoderator.adapters.PlayerListAdapter;
import agustinreinosowerewolf.com.werewolfgamemoderator.models.Player;
import agustinreinosowerewolf.com.werewolfgamemoderator.viewmodels.PlayerPartyViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PartyInformationFragment extends Fragment {

    private ImageView mProfileIcon;
    private TextView mUsername;
    private ImageView mRoleIcon;
    private PlayerPartyViewModel mplayerPartyViewModel;
    private RecyclerView mrecyclerView;
    private PlayerListAdapter adapter;
    private PlayerPartyViewModel playerPartyViewModel;

    public PartyInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_party_information, container, false);
        mplayerPartyViewModel = ViewModelProviders.of(getActivity()).
                get(PlayerPartyViewModel.class);
        mProfileIcon = view.findViewById(R.id.profile_photo);
        mUsername = view.findViewById(R.id.player_username);
        mRoleIcon = view.findViewById(R.id.role_photo);
        mrecyclerView = view.findViewById(R.id.recycler_player_party);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mrecyclerView.setHasFixedSize(true);
        adapter = new PlayerListAdapter(getContext());
        mrecyclerView.setAdapter(adapter);
       mplayerPartyViewModel.getMplayerParty().observe(getActivity(), new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable List<Player> players) {

                adapter.addPlayers(players);
            }
        });

        mplayerPartyViewModel.getmCurrentPlayer().observe(this, new Observer<Player>() {
            @Override
            public void onChanged(@Nullable Player player) {
                mProfileIcon.setImageURI(player.getImage());
                mUsername.setText(player.getName());
                mRoleIcon.setImageResource(player.getImageResource());
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
