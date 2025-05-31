package dev.cheercode.connectfour.factory;

import dev.cheercode.connectfour.dialog.Dialog;
import dev.cheercode.connectfour.dialog.impl.CharacterDialog;
import dev.cheercode.connectfour.dialog.impl.StringDialog;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.player.HumanConsoleInputStrategy;
import dev.cheercode.connectfour.model.player.Player;

import java.util.*;
import java.util.stream.Collectors;

public class ConsolePlayerFactory implements PlayerFactory {
    private final Set<String> usedNames;
    private final Set<Disc> usedColors;
    private final Map<Integer, Disc> colorMap;

    public ConsolePlayerFactory() {
        this.usedNames = new HashSet<>();
        this.usedColors = new HashSet<>();
        this.colorMap = new HashMap<>();
        Arrays.stream(Disc.values())
                .forEach(d -> colorMap.put(d.ordinal() + 1, d));
    }

    @Override
    public Player create(int playerNumber) {
        String name = askName(playerNumber);
        Disc color = askColor(playerNumber);
        return new Player(name, color, new HumanConsoleInputStrategy());
    }

    private String askName(int playerNumber) {
        Dialog<String> dialog = new StringDialog(
                String.format("Введите имя игрока %d: ", playerNumber),
                "Имя не может быть пустым или повторяться.\nУже используются имена: " +
                String.join(", ", usedNames),
                usedNames
        );
        String name = dialog.input();
        usedNames.add(name.toLowerCase());
        return name;
    }

    private Disc askColor(int playerNumber) {
        Dialog<Character> dialog = new CharacterDialog(
                String.format("Выберите цвет для игрока %d:\n%s",
                        playerNumber, getAvailableColors()),
                "Неправильный ввод или цвет уже занят.",
                getAvailableColorKeys()
        );
        Disc color = colorMap.get(dialog.input() - '0');
        usedColors.add(color);
        return color;
    }

    private String getAvailableColors() {
        return colorMap.entrySet().stream()
                .filter(e -> !usedColors.contains(e.getValue()))
                .map(e -> String.format("%d — %s", e.getKey(), getNameFor(e.getValue())))
                .collect(Collectors.joining("\n"));
    }

    private String getNameFor(Disc disc) {
        return switch (disc) {
            case BLACK -> "Чёрный";
            case RED -> "Красный";
            case GREEN -> "Зелёный";
            case YELLOW -> "Жёлтый";
            case BLUE -> "Синий";
            case PURPLE -> "Фиолетовый";
            case CYAN -> "Голубой";
            case WHITE -> "Белый";
        };
    }

    private Set<Character> getAvailableColorKeys() {
        return colorMap.entrySet().stream()
                .filter(e -> !usedColors.contains(e.getValue()))
                .map(e -> (char) (e.getKey() + '0'))
                .collect(Collectors.toSet());
    }
}
