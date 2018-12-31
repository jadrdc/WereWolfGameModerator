package agustinreinosowerewolf.com.werewolfgamemoderator.imp;

import agustinreinosowerewolf.com.werewolfgamemoderator.behaviors.VillagerPlayerAction;
import agustinreinosowerewolf.com.werewolfgamemoderator.enums.WitchActions;
import agustinreinosowerewolf.com.werewolfgamemoderator.models.Player;

public class WitchBehavior implements VillagerPlayerAction {
    @Override
    public void act(Player player) {

        switch (actions) {

            case SAVE: {
                player.setProtected(true);
                break;
            }
            case KILL: {
                player.setDead();
                break;
            }
            case NOTHING: {
                break;
            }
        }


    }

    public WitchActions actions;


    public void setWitchActions(WitchActions act) {
        actions = act;
    }

    @Override
    public void vote(Player player) {

        player.getVotedOff();
    }
}
