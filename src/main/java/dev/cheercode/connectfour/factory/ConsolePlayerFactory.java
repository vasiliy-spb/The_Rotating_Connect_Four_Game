package dev.cheercode.connectfour.factory;

import dev.cheercode.connectfour.dialog.Dialog;
import dev.cheercode.connectfour.dialog.impl.CharacterDialog;
import dev.cheercode.connectfour.dialog.impl.StringDialog;
import dev.cheercode.connectfour.game.PlayerQueue;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.player.HumanConsoleInputStrategy;
import dev.cheercode.connectfour.model.player.Player;

import java.util.*;
import java.util.stream.Collectors;

public class ConsolePlayerFactory implements PlayerFactory {
    private final Map<Integer, Disc> colorMap;
    private Set<String> usedNames;
    private Set<Disc> usedColors;

    public ConsolePlayerFactory() {
        this.colorMap = new HashMap<>();
        this.usedNames = new HashSet<>();
        this.usedColors = new HashSet<>();
    }

    @Override
    public Player create(PlayerQueue playerQueue) {
        List<Player> players = playerQueue.toList();
        players.stream()
                .map(p -> p.getName().toLowerCase())
                .forEach(usedNames::add);
        players.stream()
                .map(Player::getDisc)
                .forEach(usedColors::add);
        Arrays.stream(Disc.values())
                .filter(d -> !usedColors.contains(d))
                .forEach(d -> colorMap.put(d.ordinal() + 1, d));

        int playerNumber = playerQueue.size() + 1;
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
