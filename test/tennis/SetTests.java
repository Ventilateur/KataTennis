package tennis;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static tennis.TestUtils.printScores;

import tennis.exceptions.AlreadyOverException;
import tennis.exceptions.MustPlayTieBreakException;

public class SetTests {

    private IGame getNormalGame(Player winner) {
        IGame game = new NormalGame();
        for (int i = 0; i < 4; i++) {
            game.scores(winner);
        }
        return game;
    }
    
    private IGame getTieBreakGame(Player winner) {
        IGame game = new TieBreakGame();
        for (int i = 0; i < 7; i++) {
            game.scores(winner);
        }
        return game;
    }

    @Test
    public void testAdvSet1() {
        Set set = new Set(false);
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P1));

        printScores(set);

        assertTrue(set.isFinished());
        assertEquals(Player.P1, set.getWinner());
        assertEquals(6, set.getLatestScore(Player.P1));
        assertEquals(2, set.getLatestScore(Player.P2));
    }

    @Test
    public void testAdvSet2() {
        Set set = new Set(false);
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P2));

        printScores(set);

        assertTrue(set.isFinished());
        assertEquals(Player.P2, set.getWinner());
        assertEquals(6, set.getLatestScore(Player.P1));
        assertEquals(8, set.getLatestScore(Player.P2));
    }

    @Test
    public void testTieBreakSet() {
        Set set = new Set(true);
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P2));

        // At this point, a game must be a tie-break game
        assertThrows(MustPlayTieBreakException.class, () -> set.addGame(getNormalGame(Player.P1)));

        set.addGame(getTieBreakGame(Player.P1));

        printScores(set);

        assertTrue(set.isFinished());
        assertEquals(Player.P1, set.getWinner());
        assertEquals(7, set.getLatestScore(Player.P1));
        assertEquals(6, set.getLatestScore(Player.P2));
    }

    @Test
    public void testAlreadyOverSet() {
        Set set = new Set(true);

        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getTieBreakGame(Player.P1));

        printScores(set);

        assertThrows(AlreadyOverException.class, () -> set.addGame(getTieBreakGame(Player.P2)));
    }

    @Test
    public void testUnfinishedSet() {
        Set set = new Set(false);
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P2));
        set.addGame(getNormalGame(Player.P1));
        set.addGame(getNormalGame(Player.P1));

        assertFalse(set.isFinished());
    }
}
