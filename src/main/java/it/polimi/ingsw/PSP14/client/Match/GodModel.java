package it.polimi.ingsw.PSP14.client.Match;

/**
 * Contains God data.
 */
public enum GodModel {
    APOLLO(),
    ARTEMIS(),
    ATHENA(),
    ATLAS(),
    DEMETER(),
    HEPHAESTUS(),
    MINOTAUR(),
    PAN(),
    PROMETHEUS(),
    CHRONUS(),
    ZEUS();

    private String name;
    private String description;

    /**
     * @param name name of the God, which has to be found in the God configuration file.
     */
    GodModel() {
        this.name = formatName();
        //TODO using name, get description from file
    }

    private String formatName() {
        return name().substring(0, 1) + name().substring(1).toLowerCase();
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
