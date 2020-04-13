package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.GodNotFoundException;

public class GodControllerFactory {
    public static God getController(String name) throws GodNotFoundException {
        switch (name) {
            case "Apollo":
                return Apollo.getInstance();

            // TODO: Add the other gods
            default:
                throw new GodNotFoundException();
        }
    }
}
