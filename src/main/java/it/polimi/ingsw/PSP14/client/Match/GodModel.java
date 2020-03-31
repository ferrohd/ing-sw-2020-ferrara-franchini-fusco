package it.polimi.ingsw.PSP14.client.Match;

/**
 * Contains God data.
 */
public enum GodModel {
    APOLLO("apollo"),
    ARTEMIS("artemis"),
    ATHENA("athena"),
    ATLAS("atlas"),
    DEMETER("demeter"),
    HEPHAESTUS("hephaestus"),
    MINOTAUR("minotaur"),
    PAN("pan"),
    PROMETHEUS("prometheus"),
    CHRONUS("chronus"),
    ZEUS("zeus");

    private String name;
    private String description;

    /**
     * @param name name of the God, which has to be found in the God configuration file.
     */
    GodModel(String name) {
        this.name = name;
        //TODO using name, get description from file
    }

    /**
     * @return the name of the God
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description of the God
     */
    public String getDescription() {
        return description;
    }
}
