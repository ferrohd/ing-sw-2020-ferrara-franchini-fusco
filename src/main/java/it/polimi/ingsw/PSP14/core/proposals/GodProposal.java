package it.polimi.ingsw.PSP14.core.proposals;

/**
 * Proposal containing data about a god.
 */
public class GodProposal implements Proposal {
    private String name;

    public GodProposal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
