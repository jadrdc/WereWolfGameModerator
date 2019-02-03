package agustinreinosowerewolf.com.werewolfgamemoderator.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import agustinreinosowerewolf.com.werewolfgamemoderator.models.Player;
import agustinreinosowerewolf.com.werewolfgamemoderator.models.PlayerManager;

public class PlayerViewModel extends AndroidViewModel {

    private MutableLiveData<List<Player>> mPlayerList = new MutableLiveData<>();
    private PlayerManager manager;
    private List<Player> mPlayers = new ArrayList<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    private String roomId;

    public PlayerViewModel(@NonNull Application application) {
        super(application);
        mPlayerList.setValue(new ArrayList<Player>());

    }

    public void setUpViewModel() {
        mPlayerList.getValue().clear();
        isLoading.setValue(true);

    }

    public LiveData<List<Player>> getmPlayerList() {
        return mPlayerList;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setParticipants(List<Player> players) {
        mPlayers = players;
        mPlayerList.setValue(mPlayers);
        isLoading.setValue(false);
    }

    public void createPlayer(String name, Uri icon, String participantId) {
        if (manager == null) {
            manager = new PlayerManager();
        }
        mPlayers.add(manager.createPlayer(name, icon, participantId));
        mPlayerList.setValue(mPlayers);
        isLoading.setValue(true);

    }

}
