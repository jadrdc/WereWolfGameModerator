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

    public LiveData<List<Player>> mPlayerList;
    public PlayerManager manager;

    public PlayerViewModel(@NonNull Application application) {
        super(application);

    }

    public LiveData<List<Player>> getmPlayerList() {
        if (mPlayerList == null) {
            mPlayerList = new MutableLiveData<>();
            ((MutableLiveData<List<Player>>) mPlayerList).setValue(new ArrayList<Player>());
        }
        return mPlayerList;
    }

    public void createPlayer(String name, Uri icon) {
        if (mPlayerList == null) {
            mPlayerList = new MutableLiveData<>();
        }
        if (manager == null) {
            manager = new PlayerManager();
        }
        mPlayerList.getValue().add(manager.createPlayer(name, icon));


    }

}
