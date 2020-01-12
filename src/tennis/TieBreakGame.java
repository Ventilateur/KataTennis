package tennis;

import tennis.exceptions.AlreadyOverException;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Deque;

public class TieBreakGame implements IGame {

    private Map<Player, Deque<Integer>> scores;
    private Player winner;


    public TieBreakGame() {
        scores = new HashMap<>();
        scores.put(Player.P1, new ArrayDeque<>());
        scores.put(Player.P2, new ArrayDeque<>());
    }


    @Override
    public boolean scores(Player player) {
        if (!isFinished()) {
            Player opponent = player.getOpponent();
            Integer playerScore = scores.get(player).isEmpty() ? 1 : (scores.get(player).getLast() + 1);
            Integer opponentScore = scores.get(player).isEmpty() ? 0 : scores.get(opponent).getLast();

            scores.get(player).addLast(playerScore);
            scores.get(opponent).addLast(opponentScore);

            if (playerScore >= 7 && playerScore - opponentScore >= 2) {
                winner = player;
            }
        } else {
            throw new AlreadyOverException();
        }
        return isFinished();
    }


    @Override
    public boolean isFinished() {
        return winner != null;
    }


    @Override
    public Player getWinner() {
        return winner;
    }


    @Override
    public Object[] getScores(Player player) {
        return scores.get(player).toArray();
    }

    @Override
    public Integer getLatestScore(Player player) {
        return scores.get(player).getLast();
    }
}
