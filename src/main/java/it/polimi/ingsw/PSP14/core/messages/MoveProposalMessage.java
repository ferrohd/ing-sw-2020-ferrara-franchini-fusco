package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;

import java.io.IOException;
import java.util.Collection;

/**
 * Message containing the possible moves a client can perform.
 */
public class MoveProposalMessage extends ProposalMessage<MoveProposal> {
    public MoveProposalMessage(Collection<MoveProposal> p) {
        super(p);
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws IOException, InterruptedException {
        int choice = ui.chooseMove(getProposals());
        serverConnection.sendMessage(new ChoiceMessage(choice));
    }
}
