package it.polimi.ingsw.PSP14.core.gods;

import it.polimi.ingsw.PSP14.core.GodNotFoundException;

public class GodControllerFactory {
    public static GodController getController(String name) throws GodNotFoundException {
        switch (name) {
            case "Apollo":
                return Apollo.getInstance();

            // TODO: Add the other gods
            default:
                throw new GodNotFoundException();
        }
    }
}
