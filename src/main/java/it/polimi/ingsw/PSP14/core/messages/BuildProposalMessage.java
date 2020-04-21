package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;

import java.util.Collection;

public class BuildProposalMessage extends ProposalMessage<BuildProposal> {
    public BuildProposalMessage(Collection<BuildProposal> p) {
        super(p);
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) {
        return false;
    }
}
