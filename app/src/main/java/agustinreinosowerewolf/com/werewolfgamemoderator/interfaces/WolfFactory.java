package agustinreinosowerewolf.com.werewolfgamemoderator.interfaces;

import agustinreinosowerewolf.com.werewolfgamemoderator.behaviors.WolfPlayerAction;

public interface WolfFactory {

    WolfPlayerAction getWolfAction(String type);
}
