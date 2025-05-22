package dev.cheercode.connectfour.factory;

import dev.cheercode.connectfour.dialog.Dialog;
import dev.cheercode.connectfour.dialog.impl.CharacterDialog;
import dev.cheercode.connectfour.dialog.impl.StringDialog;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.Player;

import java.util.*;
import java.util.stream.Collectors;

public class ConsolePlayerFactory implements PlayerFactory {
    private final Set<String> usedNames;
    private final Set<Disc> usedColors;
    private final Map<Character, Disc> colorMap;

    public ConsolePlayerFactory() {
        this.usedNames = new HashSet<>();
        this.usedColors = new HashSet<>();
        this.colorMap = new HashMap<>();
        Arrays.stream(Disc.values())
                .forEach(d -> colorMap.put(d.name().toLowerCase().charAt(0), d));
    }

    @Override
    public Player create(int playerNumber) {
        String name = promptForName(playerNumber);
        Disc color = promptForColor(playerNumber);
        return new Player(name, color);
    }

    private String promptForName(int playerNumber) {
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

    private Disc promptForColor(int playerNumber) {
        Dialog<Character> dialog = new CharacterDialog(
                String.format("Выберите цвет для игрока %d:\n%s",
                        playerNumber, getAvailableColors()),
                "Неправильный ввод или цвет уже занят.",
                getAvailableColorKeys()
        );
        Disc color = colorMap.get(dialog.input());
        usedColors.add(color);
        return color;
    }

    private String getAvailableColors() {
        return colorMap.entrySet().stream()
                .filter(e -> !usedColors.contains(e.getValue()))
                .map(e -> String.format("%c — %s", e.getKey(), e.getValue().name()))
                .collect(Collectors.joining("\n"));
    }

    private Set<Character> getAvailableColorKeys() {
        return colorMap.entrySet().stream()
                .filter(e -> !usedColors.contains(e.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
