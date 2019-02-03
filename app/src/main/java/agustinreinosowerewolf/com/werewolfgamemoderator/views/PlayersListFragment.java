package agustinreinosowerewolf.com.werewolfgamemoderator.views;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.RealTimeMultiplayerClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

import agustinreinosowerewolf.com.werewolfgamemoderator.R;
import agustinreinosowerewolf.com.werewolfgamemoderator.adapters.PlayerListAdapter;
import agustinreinosowerewolf.com.werewolfgamemoderator.factory.VillagerFactoryImp;
import agustinreinosowerewolf.com.werewolfgamemoderator.factory.WolfFactoryImp;
import agustinreinosowerewolf.com.werewolfgamemoderator.helpers.SerializeHelper;
import agustinreinosowerewolf.com.werewolfgamemoderator.interfaces.WolfFactory;
import agustinreinosowerewolf.com.werewolfgamemoderator.models.Player;
import agustinreinosowerewolf.com.werewolfgamemoderator.viewmodels.PlayerViewModel;


public class PlayersListFragment extends Fragment implements PlayerListAdapter.SelectPlayerListener {
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
    private ProgressBar mProgressbar;
    private Button mButton;

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
        mProgressbar = view.findViewById(R.id.loading_bar);
        mButton = view.findViewById(R.id.btn_start);
        playerViewModel = ViewModelProviders.of(getActivity()).get(PlayerViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new PlayerListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        adapter.setListener(this);
        playerViewModel.getmPlayerList().observe(getActivity(), new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable List<Player> players) {
                adapter.addPlayers(players);

            }
        });
        playerViewModel.getIsLoading().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                mProgressbar.setVisibility(aBoolean.booleanValue() == true ? View.VISIBLE : View.INVISIBLE);
                mButton.setVisibility(aBoolean.booleanValue() == false ? View.VISIBLE : View.INVISIBLE);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        for (Player player : playerViewModel.getmPlayerList().getValue()) {
                            try {
                                Games.getRealTimeMultiplayerClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getActivity()))
                                        .sendReliableMessage(SerializeHelper.serialize(playerViewModel.getmPlayerList().getValue()),
                                                playerViewModel.getRoomId(), player.getParticipantId(),
                                                new RealTimeMultiplayerClient.ReliableMessageSentCallback() {
                                                    @Override
                                                    public void onRealTimeMessageSent(int i, int i1, String s) {

                                                    }
                                                }).addOnSuccessListener(new OnSuccessListener<Integer>() {
                                    @Override
                                    public void onSuccess(Integer integer) {
                                        Toast.makeText(getActivity(), "Se ha iniciado el juego ", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Error iniciando el juego ", Toast.LENGTH_LONG).show();

                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }

                        }

                    }
                });
            }
        }).start();

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

    @Override
    public void onSelectPlayer(final Player player, final ImageView image) {

        final String[] role = {"Villager", "Wolf", "Hunter", "Cupid", "BodyGuard", "Witch"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle("Pick a Chracter Role");
        builder.setItems(role, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String word = role[which].toUpperCase();

                if (word.equals("WOLF")) {
                    WolfFactory wolfFactory = new WolfFactoryImp();
                    player.getWolfPlayerActions().add(wolfFactory.getWolfAction(word));

                } else {
                    VillagerFactoryImp factoryImp = new VillagerFactoryImp();
                    player.getVillagerPlayerActions().add(factoryImp.getVillagerAction(word));
                }

                if (word.equals("WITCH")) {
                    image.setImageResource(R.drawable.witch);
                } else if (word.equals("VILLAGER")) {
                    image.setImageResource(R.drawable.farmer);
                } else if (word.equals("HUNTER")) {

                    image.setImageResource(R.drawable.hunter);
                } else if (word.equals("CUPID")) {

                    image.setImageResource(R.drawable.cupid);
                } else if (word.equals("BODYGUARD")) {
                    image.setImageResource(R.drawable.bodyguard);
                } else {
                    image.setImageResource(R.drawable.wolfhead);

                }


            }
        });
        builder.show();

    }


}
