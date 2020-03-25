package it.polimi.ingsw.PSP14.server.Match;

public class Player {
    private String username;
    //private God god;
    private Worker[] workers = new Worker[2];

    Player(String username /*, God god */, Worker maleWorker, Worker femaleWorker) {
        this.username = username;
        // this.god = god;
        workers[0] = maleWorker;
        workers[1] = femaleWorker;
    }

    public void moveWorker(int index, Direction dir) throws InvalidActionException, IndexOutOfBoundsException {
        workers[index].move(dir);
    }

    public String getUsername() {
        return username;
    }

    /* public God getGod() {
        return god;
    } */
}
