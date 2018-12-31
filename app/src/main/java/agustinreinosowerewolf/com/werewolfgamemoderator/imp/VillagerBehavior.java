package agustinreinosowerewolf.com.werewolfgamemoderator.imp;

import agustinreinosowerewolf.com.werewolfgamemoderator.behaviors.VillagerPlayerAction;
import agustinreinosowerewolf.com.werewolfgamemoderator.models.Player;

public class VillagerBehavior implements VillagerPlayerAction {
    @Override
    public void act(Player player) {

    }

    @Override
    public void vote(Player player) {
        player.getVotedOff();
    }
}
