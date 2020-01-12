package tennis;

public class TestUtils {

    public static void printScores(IGame game) {
        StringBuilder sb = new StringBuilder();
        sb.append("P1 : ");
        for (Object score : game.getScores(Player.P1)) {
            sb.append(score).append("\t");
        }
        sb.append("\r\nP2 : ");
        for (Object score : game.getScores(Player.P2)) {
            sb.append(score).append("\t");
        }
        sb.append("\r\n");
        System.out.println(sb.toString());
    }


    public static void printScores(Set set) {
        StringBuilder sb = new StringBuilder();
        sb.append("P1 : ");
        for (Object score : set.getScores(Player.P1)) {
            sb.append(score).append("\t");
        }
        sb.append("\r\nP2 : ");
        for (Object score : set.getScores(Player.P2)) {
            sb.append(score).append("\t");
        }
        sb.append("\r\n");
        System.out.println(sb.toString());
    }
}
