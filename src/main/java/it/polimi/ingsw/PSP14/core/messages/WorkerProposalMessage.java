package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.core.proposals.WorkerProposal;

import java.util.Collection;

public class WorkerProposalMessage extends ProposalMessage<WorkerProposal> {
    public WorkerProposalMessage(Collection<WorkerProposal> p) {
        super(p);
    }
}
