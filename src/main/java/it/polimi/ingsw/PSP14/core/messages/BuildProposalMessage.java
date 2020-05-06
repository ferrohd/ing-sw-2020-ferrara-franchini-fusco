package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;

import java.io.IOException;
import java.util.Collection;

/**
 * ProposalMessage containing the details about a specific Build action.
 */
public class BuildProposalMessage extends ProposalMessage<BuildProposal> {
    public BuildProposalMessage(Collection<BuildProposal> p) {
        super(p);
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) {
        int choice = ui.chooseBuild(getProposals());

        try {
            serverConnection.sendMessage(new ChoiceMessage(choice));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
