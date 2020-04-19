package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.core.proposals.GodProposal;

import java.util.Collection;

public class GodChoiceProposalMessage extends ProposalMessage<GodProposal> {
    public GodChoiceProposalMessage(Collection<GodProposal> p) {
        super(p);
    }
}
