package it.polimi.ingsw.PSP14.core.proposals;

public class PlayerProposal implements Proposal {
    private String name;

    public PlayerProposal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
