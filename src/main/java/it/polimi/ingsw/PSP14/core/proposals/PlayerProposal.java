package it.polimi.ingsw.PSP14.core.proposals;

/**
 * A proposal containing data about a player, currently:<br/>
 * - name
 */
public class PlayerProposal implements Proposal {
    private String name;

    public PlayerProposal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
