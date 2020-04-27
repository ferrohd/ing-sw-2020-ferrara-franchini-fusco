package it.polimi.ingsw.PSP14.server.model;

import it.polimi.ingsw.PSP14.server.actions.Action;
import it.polimi.ingsw.PSP14.server.actions.BuildAction;
import it.polimi.ingsw.PSP14.server.actions.MoveAction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class MatchTest {

    Match match;

    @BeforeEach
    void setUp() {
        Set<String> players = new HashSet<>();
        players.add("Ada");
        players.add("Bob");
        players.add("Carl");

        match = new Match(players);
    }

    @Test
    void actionHistoryShouldWork() {
        List<Action> actions = new LinkedList<>();
        actions.add(new MoveAction("Ada", new Point(0,0), new Point(1,1)));
        actions.add(new BuildAction("Ada", new Point(0,0), false));
        actions.add(new MoveAction("Bob", new Point(1,2), new Point(2,2)));

        for( Action action : actions) {
            match.addActionToHistory(action);
        }

        assertIterableEquals(actions, match.getHistory());
    }

    @Test
    void getPlayers() {
        String[] players = {"Ada", "Bob", "Carl"};
        for (int i = 0; i < 3; i++) {
            assertTrue(Arrays.asList(players).contains(match.getPlayers().get(i).getUsername()));
        }
    }

    @Test
    void getBoard() {
    }

    @Test
    void getPlayerByUsername() {
        assertEquals("Ada", match.getPlayerByUsername("Ada").getUsername());
    }

    @Test
    void getWorkerPositionsShouldFailIfNoWorkersAreSet() {
        assertThrows(NullPointerException.class, () -> match.getWorkerPositions());
    }

    @Test
    void getWorkerPositionsShouldWorkIfWorkersAreSet() {
        try {
            HashSet points = new HashSet<>(setupWorkers());
            assertEquals(points, new HashSet<>(match.getWorkerPositions()));
        } catch (PlayerNotFoundException e) {
            fail();
        }
    }

    @Test
    void isCellFree() {
        try {
            setupWorkers();
        } catch (PlayerNotFoundException e) {
            fail();
        }
        assertFalse(match.isCellFree(new Point(0,0)));
        assertTrue(match.isCellFree(new Point(2,4)));

    }

    @Test
    void getMovements() {
        assertDoesNotThrow(() -> {
            setupWorkers();
            List<MoveAction> actual = match.getMovements("Ada", 0);
            List<MoveAction> expected = new ArrayList<>();

            // The order is important in the tests, but not in general
            // I can't find a suitable way to compare two Collections
            // ignoring the order of the elements. Sorting is an option
            // I'm too lazy to do right now.
            expected.add(new MoveAction("Ada", new Point(0, 0), new Point(1, 1)));
            expected.add(new MoveAction("Ada", new Point(0, 0), new Point(1, 0)));

            for (int i = 0; i < expected.size(); i++) {
                System.out.println(expected.get(i).toString() + " vs \n" + actual.get(i).toString());
                assertTrue(expected.get(i).equals(actual.get(i)));
            }
        });
    }

    @Test
    void getBuildable() {
        assertDoesNotThrow(() -> {
            setupWorkers();
            List<BuildAction> actual = match.getBuildable("Ada", 0);
            List<BuildAction> expected = new ArrayList<>();

            // The order is important in the tests, but not in general
            // I can't find a suitable way to compare two Collections
            // ignoring the order of the elements. Sorting is an option
            // I'm too lazy to do right now.
            expected.add(new BuildAction("Ada", new Point(1, 1), false));
            expected.add(new BuildAction("Ada", new Point(1, 0), false));

            for (int i = 0; i < expected.size(); i++) {
                System.out.println(expected.get(i).toString() + " vs \n" + actual.get(i).toString());
                assertTrue(expected.get(i).equals(actual.get(i)));
            }
        });
    }

    /**
     * Return the points of the workers
     * @return list of worker positions
     */
    private List setupWorkers() throws PlayerNotFoundException {
        List<Player> p = match.getPlayers();
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(0, 1));
        points.add(new Point(4, 0));
        points.add(new Point(4, 1));
        points.add(new Point(3, 0));
        points.add(new Point(3, 1));
        match.getPlayerByUsername("Ada").setWorker(0, points.get(0));
        match.getPlayerByUsername("Ada").setWorker(1, points.get(1));
        match.getPlayerByUsername("Bob").setWorker(0, points.get(2));
        match.getPlayerByUsername("Bob").setWorker(1, points.get(3));
        match.getPlayerByUsername("Carl").setWorker(0, points.get(4));
        match.getPlayerByUsername("Carl").setWorker(1, points.get(5));

        return points;
    }
}
