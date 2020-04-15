package it.polimi.ingsw.PSP14.server.actions;

import it.polimi.ingsw.PSP14.core.proposals.Proposal;

/**
 * Element that can generate a proposal to be sent to the client through a message.
 */
public interface Proposable {
    Proposal getProposal();
}
