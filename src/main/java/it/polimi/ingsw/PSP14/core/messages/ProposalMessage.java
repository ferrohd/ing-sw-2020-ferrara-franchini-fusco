package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.core.proposals.Proposal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProposalMessage<T extends Proposal> implements Message {
    private List<T> proposals;

    public ProposalMessage(Collection<T> p) {
        proposals = new ArrayList<T>(p);
    }

    public List<T> getProposals() {
        return proposals;
    }
}
