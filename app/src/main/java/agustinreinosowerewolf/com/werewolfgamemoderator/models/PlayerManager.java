package agustinreinosowerewolf.com.werewolfgamemoderator.models;

import android.net.Uri;

public class PlayerManager {

    public Player createPlayer(String name, Uri icon, String participantId,String playerId) {
        Player player = new Player();
        player.setName(name);
        player.setImage(icon);
        player.setParticipantId(participantId);
        player.setplayerId(playerId );
        return player;
    }

}
