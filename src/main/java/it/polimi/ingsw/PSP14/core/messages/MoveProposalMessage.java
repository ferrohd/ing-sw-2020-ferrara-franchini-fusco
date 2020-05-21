package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;

import java.io.IOException;
import java.util.Collection;

public class MoveProposalMessage extends ProposalMessage<MoveProposal> {
    public MoveProposalMessage(Collection<MoveProposal> p) {
        super(p);
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) throws IOException {
        int choice = ui.chooseMove(getProposals());
        serverConnection.sendMessage(new ChoiceMessage(choice));
        return false;
    }
}
