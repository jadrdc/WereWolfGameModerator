package agustinreinosowerewolf.com.werewolfgamemoderator.factory;

import agustinreinosowerewolf.com.werewolfgamemoderator.behaviors.VillagerPlayerAction;
import agustinreinosowerewolf.com.werewolfgamemoderator.imp.BodyGuardBehavior;
import agustinreinosowerewolf.com.werewolfgamemoderator.imp.CupidBehavior;
import agustinreinosowerewolf.com.werewolfgamemoderator.imp.HunterBehavior;
import agustinreinosowerewolf.com.werewolfgamemoderator.imp.VillagerBehavior;
import agustinreinosowerewolf.com.werewolfgamemoderator.imp.WitchBehavior;
import agustinreinosowerewolf.com.werewolfgamemoderator.interfaces.VillagerFactory;

public class VillagerFactoryImp implements VillagerFactory {
    @Override
    public VillagerPlayerAction getVillagerAction(String type) {
        VillagerPlayerAction action=null;

        if(type.equals("WITCH"))
        {
            action=new WitchBehavior();
        }
        else if(type.equals("VILLAGE"))
        {
            action=new VillagerBehavior();
        }
        else if(type.equals("HUNTER"))
        {

            action=new HunterBehavior();
        }
        else if(type.equals("CUPID"))
        {

            action=new CupidBehavior();
        }
        else if (type.equals("BODYGUARD"))
        {
            action=new BodyGuardBehavior();
        }
            return action;
    }
}
