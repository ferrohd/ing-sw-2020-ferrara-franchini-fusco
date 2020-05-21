package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;

import java.io.IOException;
import java.util.Collection;

/**
 * This class sends to the client a list of Gods to choose from,
 * and sends the answer back to the server.
 */
public class GodChoiceProposalMessage extends ProposalMessage<GodProposal> {
    public GodChoiceProposalMessage(Collection<GodProposal> p) {
        super(p);
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) throws IOException {
        int index = ui.chooseGod(getProposals());
        serverConnection.sendMessage(new ChoiceMessage(index));
        return false;
    }
}
