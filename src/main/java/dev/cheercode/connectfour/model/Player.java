package dev.cheercode.connectfour.model;

import dev.cheercode.connectfour.model.board.Board;

public class Player {
    private final String name;
    private final Disc disc;
    private final MoveStrategy moveStrategy;

    public Player(String name, Disc disc, MoveStrategy moveStrategy) {
        this.name = name;
        this.disc = disc;
        this.moveStrategy = moveStrategy;
    }

    public Player(Disc disc, MoveStrategy moveStrategy) {
        this.name = disc.name();
        this.disc = disc;
        this.moveStrategy = moveStrategy;
    }

    public int makeMove(Board board) {
        return moveStrategy.chooseColumn(this, board);
    }

    public String getName() {
        return name;
    }

    public Disc getDisc() {
        return disc;
    }
}
