package tennis;

public enum Player {
    P1, P2;

    Player getOpponent() {
        return Player.values()[1 - this.ordinal()];
    }
}
