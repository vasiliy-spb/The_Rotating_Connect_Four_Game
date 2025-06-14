package dev.cheercode.connectfour.factory.board;

import dev.cheercode.connectfour.model.board.Board;

public interface BoardFactory {
    Board create(Board.Size size);
}
