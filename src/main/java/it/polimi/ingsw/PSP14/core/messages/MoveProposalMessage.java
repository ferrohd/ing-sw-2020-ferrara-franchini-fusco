package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;

import java.util.Collection;

public class MoveProposalMessage extends ProposalMessage<MoveProposal> {
    public MoveProposalMessage(Collection<MoveProposal> p) {
        super(p);
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) {
        return false;
    }
}
