package it.polimi.ingsw.PSP14.server.model.gods;

import it.polimi.ingsw.PSP14.server.model.GodNotFoundException;

public class GodControllerFactory {
    public static God getController(String name, String owner) throws GodNotFoundException {
        switch (name) {
            case "Apollo":
                return new Apollo(owner);
            case "Minotaur":
                return new Minotaur(owner);
            case "Atlas":
                return new Atlas(owner);
            case "Hephaestus":
                return new Hephaestus(owner);

            // TODO: Add the other gods
            default:
                throw new GodNotFoundException();
        }
    }
}
