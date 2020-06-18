package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.proposals.GodProposal;

import java.io.IOException;
import java.util.Collection;

/**
 * Message that presents the first player with a list of gods to choose from
 * (up to the number of players)
 */
public class GodSublistProposalMessage extends ProposalMessage<GodProposal> {

    public GodSublistProposalMessage(Collection<GodProposal> p) {
        super(p);
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws IOException, InterruptedException {
        int index = ui.chooseAvailableGods(getProposals());
        serverConnection.sendMessage(new ChoiceMessage(index));
    }
}
