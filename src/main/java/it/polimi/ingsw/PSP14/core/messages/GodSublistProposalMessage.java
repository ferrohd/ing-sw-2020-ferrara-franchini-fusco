package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;

import java.util.Collection;
//TODO: Is this really needed?

/**
 * This class presents the first player with a list of gods to choose from
 * (up to the number of players)
 */
public class GodSublistProposalMessage extends ProposalMessage<GodProposal> {
    private int nChoices;

    public GodSublistProposalMessage(Collection<GodProposal> p, int nChoices) {
        super(p);
        this.nChoices = nChoices;
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) {
        return false;
    }

    public int getNChoices() {
        return nChoices;
    }
}
