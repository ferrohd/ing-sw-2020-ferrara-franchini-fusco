package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.controller.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;

import java.io.IOException;
import java.util.Collection;

/**
 * Message that handles the retrieval of the player that will act first
 * in the match. This class sends the client a list of players and retrieves
 * the index of the player for the server.
 */
public class FirstPlayerProposalMessage extends ProposalMessage<PlayerProposal> implements ClientExecutableMessage {
    public FirstPlayerProposalMessage(Collection<PlayerProposal> p) {
        super(p);
    }

    @Override
    public void execute(UI ui, ServerConnection serverConnection) throws IOException, InterruptedException {
        int index = ui.chooseFirstPlayer(getProposals());
        serverConnection.sendMessage(new ChoiceMessage(index));
    }
}
