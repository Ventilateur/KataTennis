package tennis;

import tennis.exceptions.AlreadyOverException;
import tennis.exceptions.MustPlayTieBreakException;

import java.util.*;

public class Set {

    private List<IGame> games;
    private Map<Player, Deque<Integer>> scores;
    private boolean isTieBreakMode;
    private boolean mustPlayTieBreak;
    private Player winner;


    public Set(boolean isTieBreakMode) {
        this.isTieBreakMode = isTieBreakMode;
        games = new ArrayList<>();
        scores = new HashMap<>();
        scores.put(Player.P1, new ArrayDeque<>());
        scores.put(Player.P2, new ArrayDeque<>());
    }


    public boolean addGame(IGame game) {
        if (mustPlayTieBreak && !(game instanceof TieBreakGame)) {
            throw new MustPlayTieBreakException();
        }

        if (!isFinished()) {
            games.add(game);

            Player gameWinner = game.getWinner();
            Player gameLoser = gameWinner.getOpponent();

            int winnerSetScore = scores.get(gameWinner).isEmpty() ? 1 : (getLatestScore(gameWinner) + 1);
            int loserSetScore = scores.get(gameLoser).isEmpty() ? 0 : getLatestScore(gameLoser);

            scores.get(gameWinner).addLast(winnerSetScore);
            scores.get(gameLoser).addLast(loserSetScore);

            if (mustPlayTieBreak || winnerSetScore >= 6 && winnerSetScore - loserSetScore >= 2) {
                winner = gameWinner;
            }

            if (isTieBreakMode && winnerSetScore == 6 && loserSetScore == 6) {
                mustPlayTieBreak = true;
            }
        } else {
            throw new AlreadyOverException();
        }
        return isFinished();
    }


    public boolean isFinished() {
        return winner != null;
    }


    public Player getWinner() {
        return winner;
    }
    
    
    public Integer getLatestScore(Player player) {
        return scores.get(player).getLast();
    }


    public Integer[] getScores(Player player) {
        return scores.get(player).toArray(new Integer[0]);
    }


    public boolean mustPlayTieBreak() {
        return mustPlayTieBreak;
    }

    public List<IGame> getGames() {
        return games;
    }
}
