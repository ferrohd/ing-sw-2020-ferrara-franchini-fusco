package it.polimi.ingsw.PSP14.core.controller.gods;

public class Apollo extends GodController {

    private static Apollo ref = new Apollo();

    private Apollo() { }

    public static Apollo getInstance() {
        if (ref == null) {
            ref = new Apollo();
        }
        return ref;
    }

    @Override
    public void onMovePhase() {
        /* Your Worker may move into an opponent Worker' space
        by forcing their Worker to the space yours just vacated. */
    }
}
