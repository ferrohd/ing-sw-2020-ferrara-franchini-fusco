package it.polimi.ingsw.PSP14.server.actions;

import it.polimi.ingsw.PSP14.core.messages.updates.DomeBuildMessage;
import it.polimi.ingsw.PSP14.core.messages.updates.TowerIncrementMessage;
import it.polimi.ingsw.PSP14.core.messages.updates.UIUpdateMessage;
import it.polimi.ingsw.PSP14.core.messages.updates.WorkerMoveMessage;
import it.polimi.ingsw.PSP14.core.proposals.BuildProposal;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Cell;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.Point;
import it.polimi.ingsw.PSP14.server.model.TowerSizeException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class BuildAction extends Action implements Proposable {
    private Point point;
    private boolean dome;
    private int amount;

    public BuildAction(String user, Point point, boolean dome, int amount) {
        super(user);
        this.point = point;
        this.dome = dome;
        this.amount = amount;
    }

    public BuildProposal getProposal() {
        return new BuildProposal(point, dome, amount);
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public void execute(Match match) {
        Cell cell = match.getBoard().getCell(point);
        if(dome) {
            cell.setAsCompleted();
        } else {
            try {
                for(int i = 0; i < amount; ++i)
                    cell.incrementTowerSize();
            } catch(TowerSizeException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    @Override
    public void updateClients(List<ClientConnection> clients) throws IOException {
        UIUpdateMessage message;
        if(dome) {
            message = new DomeBuildMessage(point);
            for (ClientConnection client : clients) {
                client.sendMessage(message);
            }
        } else {
            message = new TowerIncrementMessage(point);
            for (ClientConnection client : clients)
                for(int i = 0; i <amount; ++i)
                    client.sendMessage(message);
        }
    }

    public boolean equals(BuildAction obj) {
        return user.equals(obj.user) &&
                dome == obj.dome &&
                point.equals(obj.point);
    }

    @Override
    public String toString() {
        return "BuildAction{" +
                "point=" + point +
                ", dome=" + dome +
                ", user='" + user + '\'' +
                '}';
    }
}
