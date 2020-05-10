package it.polimi.ingsw.PSP14.server.model.actions;

import it.polimi.ingsw.PSP14.core.proposals.MoveProposal;
import it.polimi.ingsw.PSP14.server.controller.ClientConnection;
import it.polimi.ingsw.PSP14.server.model.Match;
import it.polimi.ingsw.PSP14.server.model.Player;
import it.polimi.ingsw.PSP14.server.model.Point;

import java.io.IOException;
import java.util.List;

public class MoveAction extends Action implements Proposable {
    private Point from;
    private Point to;
    private int workerId = -1;

    public MoveAction(String user, Point from, Point to) {
        super(user);
        this.from = from;
        this.to = to;
    }

    public MoveProposal getProposal() {
        return new MoveProposal(to);
    }

    @Override
    public void execute(Match match) {
        for(Player p: match.getPlayers()) {
            for(int i = 0; i < 2; ++i) {
                if(p.getWorker(i).getPos().equals(from)) {
                    workerId = i;
                    p.getWorker(i).setPos(to);
                }
            }
        }
    }

    @Override
    public void updateClients(List<ClientConnection> clients) throws IOException {
        for (ClientConnection client : clients) {
            client.notifyWorkerMove(to, getUser(), workerId);
        }
    }

    public Point getFrom() {
        return from;
    }

    public Point getTo() {
        return to;
    }

    /**
     * Compare two MoveActions
     * @param obj the action
     * @return true if they match
     */
    public boolean equals(MoveAction obj) {
        return this.user.equals(obj.getUser()) &&
            this.from.equals(obj.getFrom()) &&
            this.to.equals(obj.getTo());
    }

    @Override
    public String toString() {
        return "MoveAction{" +
                "from=" + from +
                ", to=" + to +
                ", user='" + user + '\'' +
                '}';
    }
}
