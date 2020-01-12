package tennis;

public interface IGame {
    boolean scores(Player player);
    boolean isFinished();
    Player getWinner();
    Object getLatestScore(Player player);
    Object[] getScores(Player player);
}
