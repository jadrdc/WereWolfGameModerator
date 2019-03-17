package agustinreinosowerewolf.com.werewolfgamemoderator.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import agustinreinosowerewolf.com.werewolfgamemoderator.models.Player;

public class PlayerPartyViewModel extends AndroidViewModel {

    private MutableLiveData<List<Player>> mplayerParty;
    private MutableLiveData<Player> mCurrentPlayer;

    public PlayerPartyViewModel(Application context) {
        super(context);
    }

    public LiveData<List<Player>> getMplayerParty() {
        if (mplayerParty == null) {
            mplayerParty = new MutableLiveData<>();
        }
        return mplayerParty;
    }

    public void setMplayerParty(List<Player> playerList) {
        if (mplayerParty == null) {
            mplayerParty = new MutableLiveData<>();
        }

        mplayerParty.setValue(playerList);
    }

    public LiveData<Player> getmCurrentPlayer() {
        return mCurrentPlayer;
    }

    public void setCurrentPlayer(Player player) {
        if (mCurrentPlayer == null) {
            mCurrentPlayer = new MutableLiveData<>();

        }
        mCurrentPlayer.setValue(player);

    }

    public void setmCurrentPlayer(MutableLiveData<Player> mCurrentPlayer) {
        this.mCurrentPlayer = mCurrentPlayer;
    }
}
