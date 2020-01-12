package tennis;

import java.util.Random;
import java.util.Scanner;

public class TennisMatch {

    private Set set;
    private Player winner;
    private final Random RANDOM = new Random(System.currentTimeMillis());

    public TennisMatch(Set set) {
        this.set = set;
    }

    public Set getSet() {
        return set;
    }

    public Player getRandomPlayer() {
        return Player.values()[RANDOM.nextInt(2)];
    }

    public static void main(String[] args) {

        System.out.println("Welcome to my tennis match. Let's begin!");
        System.out.println("Will this match be in tie-break mode? (Y/N)");

        Scanner scan = new Scanner(System.in);

        String answer = scan.next();
        while (!"Y".equalsIgnoreCase(answer) && !"N".equalsIgnoreCase(answer)) {
            System.out.println("Unknown answer. Will this match be in tie-break mode? (Y/N)");
            answer = scan.next();
        }

        TennisMatch match = new TennisMatch(new Set("Y".equalsIgnoreCase(answer)));

        int nbSet = 1;
        while (!match.getSet().isFinished()) {
            System.out.println("\r\n\r\nSet " + nbSet++ + " starts!");

            IGame game = match.getSet().mustPlayTieBreak() ? new TieBreakGame() : new NormalGame();

            int nbGame = 1;
            while (!game.isFinished()) {
                System.out.println("\r\nGame " + nbGame++ + " starts!\r\n");

                Player winner = match.getRandomPlayer();
                game.scores(winner);

                System.out.println(winner.name() + " wins 1 point.");
                System.out.println("The game score is " +
                        game.getLatestScore(Player.P1) + " - " + game.getLatestScore(Player.P2));
            }

            match.getSet().addGame(game);

            System.out.println("\r\nGame " + (nbGame - 1) + " is over. The winner is " + game.getWinner().name());
            System.out.println("The set score is " +
                    match.getSet().getLatestScore(Player.P1) + " - " + match.getSet().getLatestScore(Player.P2));
        }

        System.out.println("The match is over. The final winner is " + match.getSet().getWinner().name());
    }
}
