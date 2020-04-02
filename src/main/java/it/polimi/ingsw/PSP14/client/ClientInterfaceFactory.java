package it.polimi.ingsw.PSP14.client;

public class ClientInterfaceFactory {
    private ClientInterfaceFactory() {}

    public static ClientInterface getClientInterface(String option) throws InvalidSettingsException {
        switch(option) {
            case "cli":
                return new CommandLineClientInterface();
            default:
                throw new InvalidSettingsException();
        }
    }
}
