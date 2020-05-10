package it.polimi.ingsw.PSP14.server.model.gods;

public class GodFactory {
    public static God getGod(String name, String owner) {
        switch (name) {
            case "Apollo":
                return new Apollo(owner);
            case "Artemis":
                return new Artemis(owner);
            case "Athena":
                return new Athena(owner);
            case "Atlas":
                return new Atlas(owner);
            case "Demeter":
                return new Demeter(owner);
            case "Hephaestus":
                return new Hephaestus(owner);
            case "Minotaur":
                return new Minotaur(owner);
            case "Pan":
                return new Pan(owner);
            case "Prometheus":
               return new Prometheus(owner);
            // ADVANCED
            case "Chronus":
                return new Chronus(owner);
            case "Hestia":
                return new Hestia(owner);
            case "Poseidon":
                return new Poseidon(owner);
            case "Triton":
                return new Triton(owner);
            case "Zeus":
                return new Zeus(owner);
            default:
                return new God(owner);
        }
    }
}
