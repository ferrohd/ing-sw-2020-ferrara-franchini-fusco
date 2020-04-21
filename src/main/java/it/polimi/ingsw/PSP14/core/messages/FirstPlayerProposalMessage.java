package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;

import java.io.IOException;
import java.util.Collection;

public class FirstPlayerProposalMessage extends ProposalMessage<PlayerProposal> implements ClientExecutableMessage {
    public FirstPlayerProposalMessage(Collection<PlayerProposal> p) {
        super(p);
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) {
        int choice = ui.chooseFirstPlayer(getProposals());
        Message choiceMessage = new ChoiceMessage(choice);
        try {
            serverConnection.sendMessage(choiceMessage);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
