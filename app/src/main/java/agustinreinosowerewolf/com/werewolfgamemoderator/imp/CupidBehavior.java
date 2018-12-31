package agustinreinosowerewolf.com.werewolfgamemoderator.imp;

import agustinreinosowerewolf.com.werewolfgamemoderator.behaviors.VillagerPlayerAction;
import agustinreinosowerewolf.com.werewolfgamemoderator.models.Player;

public class CupidBehavior implements VillagerPlayerAction {


    @Override
    public void act(Player player) {
        player.setInLove();
    }

    @Override
    public void vote(Player player) {
        player.getVotedOff();
    }
}
