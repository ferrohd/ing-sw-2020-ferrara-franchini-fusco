package it.polimi.ingsw.PSP14.client.view.cli;

/**
 * Implementation of the GodModel.
 */
public class UIGod {
    private final String name, alias, ability, description;

    public UIGod(String name, String alias, String ability, String description) {
        this.name = name;
        this.alias = alias;
        this.ability = ability;
        this.description = description;
    }

    /**
     * @return name of the God
     */
    public String getName() {
        return name;
    }

    /**
     * @return alis of the God
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @return ability of the God
     */
    public String getAbility() {
        return ability;
    }

    /**
     * @return description of the God
     */
    public String getDescription() {
        return description;
    }
}
