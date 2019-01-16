package agustinreinosowerewolf.com.werewolfgamemoderator.models;

import android.net.Uri;

import java.util.List;

import agustinreinosowerewolf.com.werewolfgamemoderator.behaviors.VillagerPlayerAction;
import agustinreinosowerewolf.com.werewolfgamemoderator.behaviors.WolfPlayerAction;

public class Player {
    public int PRIORITY;
    public int FREQUENCY;
    public boolean isInLove;
    public String name;
    public Uri image;
    public boolean isProtected;
    public int votes;
    public boolean isAlive = true;
    public List<WolfPlayerAction> wolfPlayerActions;
    public List<VillagerPlayerAction> villagerPlayerActions;


    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public void setName(String username) {
        name = username;
    }

    public void setImage(Uri imageIcon) {
        image = imageIcon;
    }

    public void setPriority(int priority) {
        PRIORITY = priority;
    }

    public boolean setDead() {
        isAlive = false;
        return isAlive;
    }

    public void setFrequency(int frequency) {
        FREQUENCY = frequency;
    }

    public void getVotedOff() {
        votes = votes++;
    }

    public void setInLove() {
        isInLove = true;
    }

}
