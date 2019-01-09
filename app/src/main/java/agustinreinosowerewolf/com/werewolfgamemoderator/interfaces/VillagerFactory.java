package agustinreinosowerewolf.com.werewolfgamemoderator.interfaces;

import agustinreinosowerewolf.com.werewolfgamemoderator.behaviors.VillagerPlayerAction;

public interface VillagerFactory {

    VillagerPlayerAction getVillagerAction(String type);
}
