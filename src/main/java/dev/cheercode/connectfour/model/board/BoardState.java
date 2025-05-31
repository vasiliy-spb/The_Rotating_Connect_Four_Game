package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

public interface BoardState {
    int getHeight();

    int getWidth();

    Disc get(int row, int column);

    void drop(int column, Disc disc);

    boolean isEmptySlot(int row, int column);

    boolean isColumnFilled(int column);

    boolean isBoardFilled();

    BoardState rotate(Direction direction);

    boolean isOnField(int row, int column);
}
