package agustinreinosowerewolf.com.werewolfgamemoderator.imp;

import java.io.Serializable;

import agustinreinosowerewolf.com.werewolfgamemoderator.behaviors.WolfPlayerAction;
import agustinreinosowerewolf.com.werewolfgamemoderator.models.Player;

public class WolfBehavior implements WolfPlayerAction ,Serializable {
    @Override
    public boolean kill(Player player) {

        return player.setDead();
    }

    @Override
    public void vote(Player player) {
        player.getVotedOff();

    }
}
