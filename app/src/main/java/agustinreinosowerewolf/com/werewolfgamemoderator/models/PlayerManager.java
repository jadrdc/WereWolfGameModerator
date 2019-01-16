package agustinreinosowerewolf.com.werewolfgamemoderator.models;

import android.net.Uri;

public class PlayerManager {

    public Player createPlayer(String name,Uri icon) {
        Player player = new Player();
        player.setName(name);
        player.setImage(icon);
        return player;
    }

}
