package agustinreinosowerewolf.com.werewolfgamemoderator.views;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.RealTimeMultiplayerClient;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.OnRealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateCallback;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import agustinreinosowerewolf.com.werewolfgamemoderator.R;
import agustinreinosowerewolf.com.werewolfgamemoderator.adapters.ViewPagerFragmentAdapter;
import agustinreinosowerewolf.com.werewolfgamemoderator.helpers.SerializeHelper;
import agustinreinosowerewolf.com.werewolfgamemoderator.models.Player;
import agustinreinosowerewolf.com.werewolfgamemoderator.viewmodels.PlayerPartyViewModel;
import agustinreinosowerewolf.com.werewolfgamemoderator.viewmodels.PlayerViewModel;
import agustinreinosowerewolf.com.werewolfgamemoderator.viewmodels.UserViewModel;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private UserViewModel mUserViewModel;
    private PlayerPartyViewModel playerPartyViewModel;
    private final int SIGN_GAMING = 1;
    private final int JOIN_GAME = 2;
    private final int START_GAME = 3;
    private final int RC_INVITATION_INBOX = 4;
    private GoogleSignInClient mGoogleGameSignIn;
    private GoogleSignInAccount mAccount;
    private RealTimeMultiplayerClient mClient;
    public TabLayout tabLayout;
    public ViewPager pager;
    private ViewPagerFragmentAdapter adapter;
    private PlayerViewModel playerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        ButterKnife.bind(this);

        tabLayout = findViewById(R.id.tabs);
        pager = findViewById(R.id.view_pager);
        adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());
        ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout: {
                mUserViewModel.getmAuthManager();
                mUserViewModel.signOut();
                Intent intent = new Intent(getApplicationContext(), SignActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_set_game: {
                startIntent(SIGN_GAMING);
                break;
            }
            case R.id.btn_play_game: {
                playerViewModel.setUpViewModel();
                startIntent(JOIN_GAME);
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void startIntent(int ACTION) {
        mGoogleGameSignIn = mUserViewModel.getmAuthManager().getValue().authGame(this);
        startActivityForResult(mGoogleGameSignIn.getSignInIntent(), ACTION);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SIGN_GAMING || requestCode == JOIN_GAME) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                this.mAccount = account;
                if (requestCode == SIGN_GAMING) {
                    onCreateGameRoom(account);
                } else {
                    onJoinedGame(account);
                }
            } catch (ApiException apiException) {
                apiException.printStackTrace();

            }
        }

        if (requestCode == START_GAME) {
            if (resultCode == Activity.RESULT_OK) {
                final ArrayList<String> invitees = data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);
                // Get Automatch criteria.
                int minAutoPlayers = data.getIntExtra(Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
                int maxAutoPlayers = data.getIntExtra(Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);
                // Create the room configuration.
                RoomConfig.Builder roomBuilder = RoomConfig.builder(mRoomUpdateCallback)
                        .setOnMessageReceivedListener(mMessageReceivedHandler)
                        .setRoomStatusUpdateCallback(mRoomStatusCallbackHandler)
                        .addPlayersToInvite(invitees);
                if (minAutoPlayers > 0) {
                    roomBuilder.setAutoMatchCriteria(
                            RoomConfig.createAutoMatchCriteria(minAutoPlayers, maxAutoPlayers, 0));
                }
                Games.getRealTimeMultiplayerClient(this, mAccount)
                        .create(roomBuilder.build());


            }
        }
        if (requestCode == RC_INVITATION_INBOX) {
            if (resultCode == Activity.RESULT_OK) {
                Invitation invitation = data.getExtras().getParcelable(Multiplayer.EXTRA_INVITATION);
                if (invitation != null) {

                    RoomConfig.Builder builder = RoomConfig.builder(mRoomUpdateCallback)
                            .setInvitationIdToAccept(invitation.getInvitationId()).setOnMessageReceivedListener(mMessageReceivedHandler).
                                    setRoomStatusUpdateCallback(mRoomStatusCallbackHandler);

                    mClient.join(builder.build());

                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
            }
        }


    }


    private void onCreateGameRoom(GoogleSignInAccount googleSignInAccount) {
        if (mClient == null) {
            mClient = Games.getRealTimeMultiplayerClient(this, googleSignInAccount);
        }
        mClient.getSelectOpponentsIntent(1, 8, true)
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, START_GAME);
                    }

                });
    }

    private void onJoinedGame(final GoogleSignInAccount googleSignInAccount) {
        if (mClient == null) {
            mClient = Games.getRealTimeMultiplayerClient(this, googleSignInAccount);
        }
        Games.getInvitationsClient(this, googleSignInAccount)
                .getInvitationInboxIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_INVITATION_INBOX);
                    }
                });

    }

    private RoomUpdateCallback mRoomUpdateCallback = new RoomUpdateCallback() {
        @Override
        public void onRoomCreated(int i, @Nullable Room room) {

        }

        @Override
        public void onJoinedRoom(int i, @Nullable Room room) {

        }

        @Override
        public void onLeftRoom(int i, @NonNull String s) {

        }

        @Override
        public void onRoomConnected(int i, @Nullable final Room room) {
            Toast.makeText(getApplicationContext(), "TODOS SE HAN CONECTADO", Toast.LENGTH_LONG).show();

            Games.getPlayersClient(getApplicationContext(), mAccount).getCurrentPlayerId().
                    addOnSuccessListener(new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String s) {

                            if (room.getParticipantId(s).equals(room.getCreatorId())) {
                                adapter.addFragment(new SummaryFragment());
                                adapter.addFragment(new PlayersListFragment());
                                adapter.notifyDataSetChanged();
                                pager.setAdapter(adapter);
                                tabLayout.setupWithViewPager(pager);
                                tabLayout.getTabAt(1).setIcon(R.drawable.profile);
                                tabLayout.getTabAt(0).setIcon(R.drawable.list_not);

                                for (Participant participant : room.getParticipants()) {

                                    playerViewModel.createPlayer(
                                            participant.getDisplayName(),
                                            participant.getHiResImageUri(), participant.getParticipantId(),
                                            participant.getPlayer().getPlayerId());
                                }
                                playerViewModel.getIsLoading().setValue(false);
                            }
                        }
                    });


        }
    };


    OnRealTimeMessageReceivedListener mMessageReceivedHandler = new OnRealTimeMessageReceivedListener() {
        @Override
        public void onRealTimeMessageReceived(@NonNull RealTimeMessage realTimeMessage) {

            byte[] data = realTimeMessage.getMessageData();
            try {
                final List<Player> players = (List<Player>) SerializeHelper.deserialize(data);
                playerPartyViewModel = ViewModelProviders.of(MainActivity.this).
                        get(PlayerPartyViewModel.class);
                playerPartyViewModel.setMplayerParty(players);
                Games.getPlayersClient(getApplicationContext(), mAccount).getCurrentPlayerId().addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {

                        for (Player p : players) {

                            if (p.getplayerId().equals(s)) {
                                p.setImage(p.getImage());
                                playerPartyViewModel.setCurrentPlayer(p);
                                adapter.addFragment(new SummaryFragment());
                                adapter.addFragment(new PartyInformationFragment());
                                adapter.notifyDataSetChanged();
                                pager.setAdapter(adapter);
                                tabLayout.setupWithViewPager(pager);
                                tabLayout.getTabAt(1).setIcon(R.drawable.profile);

                            }
                        }
                    }
                });

             /*   Games.getPlayersClient(getApplicationContext(),
                        mAccount).getCurrentPlayerId()
                        .addOnSuccessListener(new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(String s) {
                                Toast.makeText(getApplicationContext(),"SUCCESS",Toast.LENGTH_LONG).show();

                                for (Player p : players) {

                                    if (p.getplayerId() == s) {

                                        playerPartyViewModel.setCurrentPlayer(p);
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"FALLO",Toast.LENGTH_LONG).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {

                        Toast.makeText(getApplicationContext(),"COMPLETADO",Toast.LENGTH_LONG).show();

                    }
                });*/
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
    };
    RoomStatusUpdateCallback mRoomStatusCallbackHandler = new RoomStatusUpdateCallback() {
        @Override
        public void onRoomConnecting(@Nullable Room room) {
            playerViewModel.setRoomId(room.getRoomId());
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "jadrdc");
            builder.setSmallIcon(R.drawable.notification)
                    .setContentText("Se ha conectado: ")
                    .setContentTitle("Nuevo Objeto Reportado").setChannelId("jadrdc").
                    setPriority(NotificationCompat.PRIORITY_HIGH).setStyle(new NotificationCompat.BigTextStyle().bigText("SE HA CONECTADO UN NUEVO JUGADOR"));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("jadrdc", "notif", importance);
                channel.setDescription(" NOTIFICATION CHANNEL ");
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
                notificationManager.notify(1, builder.build());
            } else {
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                notificationManager.notify(1, builder.build());
            }
        }

        @Override
        public void onRoomAutoMatching(@Nullable Room room) {

        }

        @Override
        public void onPeerInvitedToRoom(@Nullable Room room, @NonNull List<String> list) {

        }

        @Override
        public void onPeerDeclined(@Nullable Room room, @NonNull List<String> list) {

        }

        @Override
        public void onPeerJoined(@Nullable Room room, @NonNull List<String> list) {

        }

        @Override
        public void onPeerLeft(@Nullable Room room, @NonNull List<String> list) {

        }

        @Override
        public void onConnectedToRoom(@Nullable Room room) {

        }

        @Override
        public void onDisconnectedFromRoom(@Nullable Room room) {

        }

        @Override
        public void onPeersConnected(@Nullable Room room, @NonNull List<String> list) {

        }

        @Override
        public void onPeersDisconnected(@Nullable Room room, @NonNull List<String> list) {

        }

        @Override
        public void onP2PConnected(@NonNull String s) {

        }

        @Override
        public void onP2PDisconnected(@NonNull String s) {

        }
    };


}



