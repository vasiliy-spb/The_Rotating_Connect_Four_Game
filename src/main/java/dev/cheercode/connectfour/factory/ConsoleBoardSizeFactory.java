package dev.cheercode.connectfour.factory;

import dev.cheercode.connectfour.dialog.Dialog;
import dev.cheercode.connectfour.dialog.impl.IntegerMinMaxDialog;
import dev.cheercode.connectfour.model.board.Board;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConsoleBoardSizeFactory implements BoardSizeFactory {
    @Override
    public Board.Size create() {
        List<Board.Size> boardSizes = List.of(Board.Size.values());
        String title = "Выберите размеры поля:\n" +
                       IntStream.range(0, boardSizes.size())
                               .mapToObj(i -> String.format("%d — %d x %d",
                                       i + 1, boardSizes.get(i).getHeight(), boardSizes.get(i).getWidth()))
                               .collect(Collectors.joining("\n"));
        Dialog<Integer> dialog = new IntegerMinMaxDialog(
                title,
                "Неправильный ввод.",
                1,
                boardSizes.size()
        );
        int index = dialog.input() - 1;
        return boardSizes.get(index);
    }
}
