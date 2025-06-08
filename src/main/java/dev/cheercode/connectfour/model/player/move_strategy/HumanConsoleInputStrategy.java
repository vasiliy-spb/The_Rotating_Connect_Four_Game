package dev.cheercode.connectfour.model.player.move_strategy;

import dev.cheercode.connectfour.dialog.Dialog;
import dev.cheercode.connectfour.dialog.impl.IntegerMinMaxDialog;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.player.Player;

public class HumanConsoleInputStrategy implements MoveStrategy {
    private static final String DIALOG_TITLE_TEMPLATE = "%s, сделайте ход (%d - %d)\n";
    private static final String DIALOG_ERROR_MESSAGE = "Неправильный ввод";

    @Override
    public int chooseColumn(Player player, Board board) {
        int min = 1;
        int max = board.getWidth();
        String title = String.format(DIALOG_TITLE_TEMPLATE, player.getName(), min, max);
        Dialog<Integer> dialog = new IntegerMinMaxDialog(title, DIALOG_ERROR_MESSAGE, min, max);
        return dialog.input() - 1;
    }
}
