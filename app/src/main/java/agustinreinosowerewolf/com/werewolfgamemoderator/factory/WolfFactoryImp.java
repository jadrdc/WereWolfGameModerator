package agustinreinosowerewolf.com.werewolfgamemoderator.factory;

import agustinreinosowerewolf.com.werewolfgamemoderator.behaviors.WolfPlayerAction;
import agustinreinosowerewolf.com.werewolfgamemoderator.imp.WolfBehavior;
import agustinreinosowerewolf.com.werewolfgamemoderator.interfaces.WolfFactory;

public class WolfFactoryImp implements WolfFactory {
    @Override
    public WolfPlayerAction getWolfAction(String type) {
        return new WolfBehavior();
    }
}
