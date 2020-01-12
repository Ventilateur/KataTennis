package tennis;

import tennis.exceptions.AlreadyOverException;

import java.util.*;

/**
 * Created by c05802 on 1/10/2020.
 */
public class NormalGame implements IGame {

    enum GameScore {
        ZERO, FIFTEEN, THIRTY, FORTY, ADV, DEUCE, WIN;

        GameScore getNextPoint(GameScore opponent) {
            switch (this) {
                case ZERO:
                case FIFTEEN:
                case THIRTY:
                    return GameScore.values()[this.ordinal() + 1];
                case FORTY:
                    switch (opponent) {
                        case ZERO:
                        case FIFTEEN:
                        case THIRTY:
                            return GameScore.WIN;
                        case FORTY:
                        case DEUCE:
                            return GameScore.ADV;
                        case ADV:
                            return GameScore.DEUCE;
                        default:
                            throw new AlreadyOverException();
                    }
                case DEUCE:
                    return GameScore.ADV;
                case ADV:
                    return GameScore.WIN;
                default:
                    throw new AlreadyOverException();
            }
        }

        GameScore getKeepPoint() {
            switch (this) {
                case ZERO:
                case FIFTEEN:
                case THIRTY:
                case FORTY:
                    return this;
                case ADV:
                    return GameScore.DEUCE;
                case DEUCE:
                    return GameScore.FORTY;
                default:
                    throw new AlreadyOverException();
            }
        }

        @Override
        public String toString() {
            switch (this) {
                case ZERO:
                    return "0";
                case FIFTEEN:
                    return "15";
                case THIRTY:
                    return "30";
                case FORTY:
                    return "40";
                default:
                    return this.name();
            }
        }
    }


    private Map<Player, Deque<GameScore>> scores;
    private boolean isFinished;
    private Player winner;


    public NormalGame() {
        scores = new HashMap<>();
        scores.put(Player.P1, new ArrayDeque<>());
        scores.get(Player.P1).addLast(GameScore.ZERO);
        scores.put(Player.P2, new ArrayDeque<>());
        scores.get(Player.P2).addLast(GameScore.ZERO);
    }


    @Override
    public boolean scores(Player player) {
        if (!isFinished) {
            Player opponent = player.getOpponent();
            Deque<GameScore> playerScores = scores.get(player);
            Deque<GameScore> opponentScores = scores.get(opponent);

            GameScore nextPoint = playerScores.getLast().getNextPoint(opponentScores.getLast());
            if (GameScore.WIN == nextPoint) {
                winner = player;
                isFinished = true;
            } else {
                playerScores.addLast(nextPoint);
                opponentScores.addLast(opponentScores.getLast().getKeepPoint());
            }
        } else {
            throw new AlreadyOverException();
        }
        return isFinished;
    }


    @Override
    public boolean isFinished() {
        return isFinished;
    }


    @Override
    public GameScore getLatestScore(Player player) {
        return scores.get(player).getLast();
    }


    @Override
    public Object[] getScores(Player player) {
        return scores.get(player).toArray();
    }

    @Override
    public Player getWinner() {
        return winner;
    }
}

