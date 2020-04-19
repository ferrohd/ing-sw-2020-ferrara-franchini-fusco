package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;

import java.util.Collection;

public class FirstPlayerProposalMessage extends ProposalMessage<PlayerProposal> {
    public FirstPlayerProposalMessage(Collection<PlayerProposal> p) {
        super(p);
    }
}
