package it.polimi.ingsw.PSP14.core.proposals;

import java.io.Serializable;

/**
 * Generic proposal. It is contained in a ProposalMessage.
 * <p>
 * A list of proposals is sent from the server to the client whenever the client
 * has to make a choice. The client will respond with a ChoiceMessage, indicating the index of
 * the chosen proposal in the list of proposals.
 */
public interface Proposal extends Serializable {
}
