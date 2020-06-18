package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;

import java.io.IOException;
import java.util.Collection;

/**
 * Message containing the details about a specific Build action.
 */
public class BuildProposalMessage extends ProposalMessage<BuildProposal> {
    public BuildProposalMessage(Collection<BuildProposal> p) {
        super(p);
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws IOException, InterruptedException {
        int choice = ui.chooseBuild(getProposals());
        serverConnection.sendMessage(new ChoiceMessage(choice));
    }
}
