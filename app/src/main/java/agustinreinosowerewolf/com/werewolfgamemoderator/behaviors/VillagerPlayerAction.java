package agustinreinosowerewolf.com.werewolfgamemoderator.behaviors;

import java.io.Serializable;

import agustinreinosowerewolf.com.werewolfgamemoderator.models.Player;

public interface VillagerPlayerAction extends CommonPlayerActions, Serializable {
    public void act(Player player);
}
