package it.polimi.ingsw.PSP14.server.model;


import java.io.IOException;
import java.util.ArrayList;

public class FakeMatch extends Match {
    public boolean flag;
    public int num;

    public FakeMatch() throws IOException {
        super(new ArrayList<>());
        flag = false;
        num = 0;
    }
}
