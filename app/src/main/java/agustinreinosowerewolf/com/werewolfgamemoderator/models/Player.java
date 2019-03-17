package agustinreinosowerewolf.com.werewolfgamemoderator.models;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import agustinreinosowerewolf.com.werewolfgamemoderator.behaviors.VillagerPlayerAction;
import agustinreinosowerewolf.com.werewolfgamemoderator.behaviors.WolfPlayerAction;

public class Player  implements Serializable {
    private int PRIORITY;
    private int FREQUENCY;
    private boolean isInLove;
    private String name;
    private  String participantId;

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    private transient  Uri image;
    private boolean isProtected;
    private int votes;
    private boolean isAlive = true;
    private  WolfPlayerAction wolfPlayerActions;
    private  List<VillagerPlayerAction> villagerPlayerActions=new ArrayList<>();
    private String playerId;
    private  int imageResource;

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getplayerId() {
        return playerId;
    }

    public void setplayerId(String participantId) {
        this.playerId = participantId;
    }

    public int getPRIORITY() {
        return PRIORITY;
    }

    public void setPRIORITY(int PRIORITY) {
        this.PRIORITY = PRIORITY;
    }

    public int getFREQUENCY() {
        return FREQUENCY;
    }

    public void setFREQUENCY(int FREQUENCY) {
        this.FREQUENCY = FREQUENCY;
    }

    public boolean isInLove() {
        return isInLove;
    }

    public void setInLove(boolean inLove) {
        isInLove = inLove;
    }

    public String getName() {
        return name;
    }

    public Uri getImage() {
        return image;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public WolfPlayerAction getWolfPlayerActions() {
        return wolfPlayerActions;
    }

    public void setWolfPlayerActions(WolfPlayerAction wolfPlayerActions) {
        this.wolfPlayerActions = wolfPlayerActions;
    }

    public List<VillagerPlayerAction> getVillagerPlayerActions() {
        return villagerPlayerActions;
    }

    public void setVillagerPlayerActions(List<VillagerPlayerAction> villagerPlayerActions) {
        this.villagerPlayerActions = villagerPlayerActions;
    }

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
