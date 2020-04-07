package it.polimi.ingsw.PSP14.core.controller.gods;

public abstract class GodController {
    /**
     * Called when the current player's turn starts.
     */
    public void onTurnStart() {};

    /**
     * Called when current player can move.
     */
    public void onMovePhase() {};

    /**
     * Called when current player can build.
     */
    public void onBuildPhase() {};

    /**
     * Called when current player's turn ends.
     */
    public void onTurnEnd() {};

    /**
     * Called before the match starts.
     */
    public void onSetupPhase() {};

    /**
     * Called during the opponent(s) turn(s).
     */
    public void onOpponentTurn() {};
}
