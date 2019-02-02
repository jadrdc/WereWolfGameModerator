package agustinreinosowerewolf.com.werewolfgamemoderator.models;

import android.net.Uri;

public class PlayerManager {

    public Player createPlayer(String name, Uri icon, String participantId) {
        Player player = new Player();
        player.setName(name);
        player.setImage(icon);
        player.setParticipantId(participantId );
        return player;
    }

}
