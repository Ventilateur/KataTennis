package tennis;

import org.junit.jupiter.api.Test;
import tennis.exceptions.AlreadyOverException;

import static org.junit.jupiter.api.Assertions.*;
import static tennis.TestUtils.printScores;

class GameTests {

    @Test
    public void testNormalGame1() {
        IGame game = new NormalGame();
        for (int i = 0; i < 4; i++) {
            assertFalse(game.isFinished());
            game.scores(Player.P1);
        }
        printScores(game);
        assertTrue(game.isFinished());
        assertEquals(Player.P1, game.getWinner());
        assertEquals(NormalGame.GameScore.FORTY, game.getLatestScore(Player.P1));
        assertEquals(NormalGame.GameScore.ZERO, game.getLatestScore(Player.P2));
    }

    @Test
    public void testNormalGame2() {
        IGame game = new NormalGame();
        for (int i = 0; i < 4; i++) {
            assertFalse(game.isFinished());
            game.scores(Player.P2);
        }
        printScores(game);
        assertTrue(game.isFinished());
        assertEquals(Player.P2, game.getWinner());
        assertEquals(NormalGame.GameScore.ZERO, game.getLatestScore(Player.P1));
        assertEquals(NormalGame.GameScore.FORTY, game.getLatestScore(Player.P2));
    }

    @Test
    public void testNormalGameDeuce1() {
        IGame game = new NormalGame();
        game.scores(Player.P1);
        game.scores(Player.P1);
        game.scores(Player.P2);
        game.scores(Player.P1);
        game.scores(Player.P2);
        game.scores(Player.P2);

        // Deuce
        game.scores(Player.P2);
        game.scores(Player.P1);
        game.scores(Player.P1);
        game.scores(Player.P1);

        printScores(game);

        assertTrue(game.isFinished());
        assertEquals(Player.P1, game.getWinner());
        assertEquals(NormalGame.GameScore.ADV, game.getLatestScore(Player.P1));
        assertEquals(NormalGame.GameScore.FORTY, game.getLatestScore(Player.P2));
    }

    @Test
    public void testNormalGameDeuce2() {
        IGame game = new NormalGame();
        game.scores(Player.P1);
        game.scores(Player.P1);
        game.scores(Player.P2);
        game.scores(Player.P1);
        game.scores(Player.P2);
        game.scores(Player.P2);

        // Deuce
        game.scores(Player.P2);
        game.scores(Player.P1);
        game.scores(Player.P1);
        game.scores(Player.P2);
        game.scores(Player.P2);
        game.scores(Player.P1);
        game.scores(Player.P2);
        game.scores(Player.P2);

        printScores(game);

        assertTrue(game.isFinished());
        assertEquals(Player.P2, game.getWinner());
        assertEquals(NormalGame.GameScore.FORTY, game.getLatestScore(Player.P1));
        assertEquals(NormalGame.GameScore.ADV, game.getLatestScore(Player.P2));
    }

    @Test
    public void testTieBreakGame1() {
        IGame game = new TieBreakGame();
        game.scores(Player.P1);
        game.scores(Player.P1);
        game.scores(Player.P2);
        game.scores(Player.P2);
        game.scores(Player.P2);
        game.scores(Player.P1);
        game.scores(Player.P1);
        game.scores(Player.P1);
        game.scores(Player.P1);
        game.scores(Player.P1);

        printScores(game);

        assertTrue(game.isFinished());
        assertEquals(Player.P1, game.getWinner());
        assertEquals(7, game.getLatestScore(Player.P1));
        assertEquals(3, game.getLatestScore(Player.P2));
    }

    @Test
    public void testTieBreakGame2() {
        IGame game = new TieBreakGame();
        game.scores(Player.P1);
        game.scores(Player.P2);
        game.scores(Player.P1);
        game.scores(Player.P2);
        game.scores(Player.P1);
        game.scores(Player.P2);
        game.scores(Player.P1);
        game.scores(Player.P2);
        game.scores(Player.P1);
        game.scores(Player.P2);
        game.scores(Player.P1);
        game.scores(Player.P2);
        game.scores(Player.P1);
        game.scores(Player.P2);
        game.scores(Player.P1);
        game.scores(Player.P1);

        printScores(game);

        assertTrue(game.isFinished());
        assertEquals(Player.P1, game.getWinner());
        assertEquals(9, game.getLatestScore(Player.P1));
        assertEquals(7, game.getLatestScore(Player.P2));
    }

    @Test
    public void testUnfinishedGames() {
        IGame game = new NormalGame();
        game.scores(Player.P1);
        game.scores(Player.P1);
        game.scores(Player.P2);
        game.scores(Player.P1);

        printScores(game);

        assertFalse(game.isFinished());
        assertEquals(NormalGame.GameScore.FORTY, game.getLatestScore(Player.P1));
        assertEquals(NormalGame.GameScore.FIFTEEN, game.getLatestScore(Player.P2));

        IGame tieBreak = new TieBreakGame();
        tieBreak.scores(Player.P1);
        tieBreak.scores(Player.P1);
        tieBreak.scores(Player.P2);
        tieBreak.scores(Player.P1);

        printScores(tieBreak);

        assertFalse(tieBreak.isFinished());
        assertEquals(3, tieBreak.getLatestScore(Player.P1));
        assertEquals(1, tieBreak.getLatestScore(Player.P2));
    }

    @Test
    public void testGameAlreadyOver() {
        assertThrows(AlreadyOverException.class, () -> {
            IGame game = new NormalGame();
            for (int i = 0; i < 5; i++) {
                game.scores(Player.P1);
            }
        });

        assertThrows(AlreadyOverException.class, () -> {
           IGame game = new TieBreakGame();
            for (int i = 0; i < 8; i++) {
                game.scores(Player.P1);
            }
        });
    }
}
