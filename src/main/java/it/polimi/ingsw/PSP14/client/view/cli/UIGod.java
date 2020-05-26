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

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getAbility() {
        return ability;
    }

    public String getDescription() {
        return description;
    }
}
