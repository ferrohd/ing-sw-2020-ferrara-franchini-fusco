package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.client.ServerConnection;
import it.polimi.ingsw.PSP14.client.view.UI;
import it.polimi.ingsw.PSP14.core.proposals.PlayerProposal;

import java.io.IOException;
import java.util.Collection;

/**
 * This class handles the retrieval of the player that will act first
 * in the match. This class sends the client a list of players and retrieves
 * the index of the player for the server.
 */
public class FirstPlayerProposalMessage extends ProposalMessage<PlayerProposal> implements ClientExecutableMessage {
    public FirstPlayerProposalMessage(Collection<PlayerProposal> p) {
        super(p);
    }

    @Override
    public boolean execute(UI ui, ServerConnection serverConnection) {
        // Retrieve the index of the first player
        int index = ui.chooseFirstPlayer(getProposals());
        // Send it back to the server
        try {
            serverConnection.sendMessage(new ChoiceMessage(index));
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
