package it.polimi.ingsw.PSP14.core.proposals;

/**
 * A proposal containing data about a player (the username).
 */
public class PlayerProposal implements Proposal {
    private final String name;

    public PlayerProposal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
