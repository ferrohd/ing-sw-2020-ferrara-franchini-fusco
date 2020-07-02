package it.polimi.ingsw.PSP14.core.messages;

import it.polimi.ingsw.PSP14.core.proposals.Proposal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This is the base class that is sent between client and server,
 * containing a list of Proposal that the client can choose from.
 * This class and its extensions can override the execute method to
 * execute code on the client.
 *
 * @param <T> the sub-type of Proposal used in this ProposalMessage
 */
public abstract class ProposalMessage<T extends Proposal> implements Message, ClientExecutableMessage {
    private final List<T> proposals;

    // Set the proposals when this class is instantiated.
    public ProposalMessage(Collection<T> p) {
        proposals = new ArrayList<T>(p);
    }

    // Getter
    public List<T> getProposals() {
        return proposals;
    }
}
