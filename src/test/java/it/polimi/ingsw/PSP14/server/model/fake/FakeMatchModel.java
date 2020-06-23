package it.polimi.ingsw.PSP14.server.model.fake;


import it.polimi.ingsw.PSP14.server.controller.MatchController;
import it.polimi.ingsw.PSP14.server.model.MatchModel;

import java.io.IOException;
import java.util.ArrayList;

public class FakeMatchModel extends MatchModel {
    public boolean flag;
    public int num;
    public String s;

    public FakeMatchModel() throws IOException {
        super(new MatchController(new ArrayList<>()));
        flag = false;
        num = 0;
        s = "";
    }
}
