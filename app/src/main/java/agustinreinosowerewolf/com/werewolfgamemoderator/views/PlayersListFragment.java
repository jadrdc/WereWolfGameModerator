package agustinreinosowerewolf.com.werewolfgamemoderator.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import agustinreinosowerewolf.com.werewolfgamemoderator.R;
import agustinreinosowerewolf.com.werewolfgamemoderator.adapters.PlayerListAdapter;
import agustinreinosowerewolf.com.werewolfgamemoderator.viewmodels.PlayerViewModel;
import butterknife.BindView;


public class PlayersListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public RecyclerView recyclerView;
    public PlayerListAdapter adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PlayerViewModel playerViewModel;


    public PlayersListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_players_list, container, false);
        recyclerView = view.findViewById(R.id.recycle_products);
        playerViewModel = ViewModelProviders.of(getActivity()).get(PlayerViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new PlayerListAdapter(getContext(), playerViewModel.getmPlayerList().getValue());
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
