package agustinreinosowerewolf.com.werewolfgamemoderator.views;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.InvitationsClient;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.InvitationCallback;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.realtime.OnRealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateCallback;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import agustinreinosowerewolf.com.werewolfgamemoderator.R;
import agustinreinosowerewolf.com.werewolfgamemoderator.adapters.ViewPagerFragmentAdapter;
import agustinreinosowerewolf.com.werewolfgamemoderator.viewmodels.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private int RC_INVITATION_INBOX = 456;
    private UserViewModel mUserViewModel;
    private int SIGN_GAMING = 123;
    private int JOIN_GAME = 12345;
    private int START_GAME = 1234;
    private GoogleSignInClient mGoogleGameSignIn;
    private GoogleSignInAccount mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
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
                mGoogleGameSignIn = mUserViewModel.getmAuthManager().getValue().authGame(this);
                startActivityForResult(mGoogleGameSignIn.getSignInIntent(), SIGN_GAMING);
                break;
            }
            case R.id.btn_play_game: {
                mGoogleGameSignIn = mUserViewModel.getmAuthManager().getValue().authGame(this);
                startActivityForResult(mGoogleGameSignIn.getSignInIntent(), JOIN_GAME);

                break;
            }

        }
        return super.onOptionsItemSelected(item);
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
                            .setInvitationIdToAccept(invitation.getInvitationId());
                    Games.getRealTimeMultiplayerClient(getApplicationContext(),
                            GoogleSignIn.getLastSignedInAccount(this))
                            .join(builder.build());
                    // prevent screen from sleeping during handshake
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
            }
        }


    }


    private void onCreateGameRoom(GoogleSignInAccount googleSignInAccount) {

        Games.getRealTimeMultiplayerClient(this, googleSignInAccount)
                .getSelectOpponentsIntent(1, 8, true)
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, START_GAME);
                    }

                });
    }

    private void onJoinedGame(final GoogleSignInAccount googleSignInAccount) {
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
        public void onRoomConnected(int i, @Nullable Room room) {
            Toast.makeText(getApplicationContext(), "HO", Toast.LENGTH_LONG).show();
        }
    };

    RoomStatusUpdateCallback mRoomStatusCallbackHandler = new RoomStatusUpdateCallback() {
        @Override
        public void onRoomConnecting(@Nullable Room room) {

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

    OnRealTimeMessageReceivedListener mMessageReceivedHandler = new OnRealTimeMessageReceivedListener() {
        @Override
        public void onRealTimeMessageReceived(@NonNull RealTimeMessage realTimeMessage) {

        }
    };
}
