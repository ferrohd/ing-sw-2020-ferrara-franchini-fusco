package it.polimi.ingsw.PSP14.client;

public class CommandLineClientInterface implements ClientInterface {
    public CommandLineClientInterface() {
    }

    public void welcome() {
        System.out.println("CLI Started");
    }
}
