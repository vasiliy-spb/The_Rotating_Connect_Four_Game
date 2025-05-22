package dev.cheercode.connectfour;

import dev.cheercode.connectfour.dialog.Dialog;
import dev.cheercode.connectfour.dialog.impl.IntegerMinMaxDialog;
import dev.cheercode.connectfour.dialog.impl.StringDialog;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.Player;

import java.util.*;

public class Menu {
    private final PlayerCreator playerCreator;
    private final ColorSelector colorSelector;
    private final Set<String> usedNames = new HashSet<>();

    public Menu() {
        this.playerCreator = new PlayerCreator();
        this.colorSelector = new ColorSelector();
    }

    public Queue<Player> createPlayers() {
        int playerCount = getPlayerCount();
        Queue<Player> players = new ArrayDeque<>();

        for (int i = 1; i <= playerCount; i++) {
            String name = getUniquePlayerName(i);
            Disc color = colorSelector.selectColor(i);
            players.add(playerCreator.createPlayer(name, color));
        }

        return players;
    }

    private int getPlayerCount() {
        int min = 2;
        int max = Disc.values().length;
        Dialog<Integer> dialog = new IntegerMinMaxDialog(
                String.format("Выберите количество игроков (%d - %d)", min, max),
                "Неправильный ввод.",
                min, max
        );
        return dialog.input();
    }

    private String getUniquePlayerName(int playerNumber) {
        Dialog<String> dialog = new StringDialog(
                String.format("Введите имя игрока %s: ", playerNumber),
                "Имя не может быть пустым или повторяться." + "\nУже использованные имена: " + String.join(", ", usedNames),
                new HashSet<>(usedNames)
        );
        String selectedName = dialog.input();
        usedNames.add(selectedName.trim().toLowerCase());
        return selectedName;
    }
}
