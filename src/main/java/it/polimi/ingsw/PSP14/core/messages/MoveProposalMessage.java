package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;

import java.io.IOException;
import java.util.Collection;

public class MoveProposalMessage extends ProposalMessage<MoveProposal> {
    public MoveProposalMessage(Collection<MoveProposal> p) {
        super(p);
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) {
        int choice = ui.chooseMove(getProposals());

        try {
            serverConnection.sendMessage(new ChoiceMessage(choice));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
