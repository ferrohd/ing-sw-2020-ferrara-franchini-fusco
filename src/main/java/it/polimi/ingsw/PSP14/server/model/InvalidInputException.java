package it.polimi.ingsw.PSP14.server.model;

/**
 * Exception for generic invalid move.
 */
public class InvalidInputException extends Exception {
    // DO NOT TOUCH!
    private static final long serialVersionUID = 1L;
    
    private String description;
    
    /**
     * Specify the details of what's wrong with
     * the input, keeping in mind that even the end used
     * might read it.
     * @param description describe what's wrong with the input.
     */
    public InvalidInputException(String description) {
        this.description = description;
    }
    
    /**
     * Get the detailed description of what's
     * wrong with input value.
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
