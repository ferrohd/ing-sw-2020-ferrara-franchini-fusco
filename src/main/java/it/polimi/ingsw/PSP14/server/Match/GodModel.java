package it.polimi.ingsw.PSP14.server.Match;

public enum GodModel {
    APOLLO("apollo"),
    ARTEMIS("artemis"),
    ATHENA("athena"),
    ATLAS("atlas"),
    DEMETER("demeter"),
    HEPHAESTUS("hephaestus"),
    MINOTAUR("minotaur"),
    PAN("pan"),
    PROMETHEUS("prometheus");

    private String name;
    private String description;

    GodModel(String name) {
        this.name = name;
        //TODO using name, get description from file
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
