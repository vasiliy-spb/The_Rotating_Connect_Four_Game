package dev.cheercode.connectfour.factory;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.player.BotRandomStrategy;
import dev.cheercode.connectfour.model.player.Player;

import java.util.*;

public class RandomBotFactory implements PlayerFactory {
    private final Set<Disc> usedColors;
    private final Random random;

    public RandomBotFactory(Set<Disc> usedColors) {
        this.usedColors = new HashSet<>(usedColors);
        this.random = new Random();
    }

    @Override
    public Player create(int playerNumber) {
        List<Disc> availableDiscs = Arrays.stream(Disc.values())
                .filter(d -> !usedColors.contains(d))
                .toList();

        if (availableDiscs.isEmpty()) {
            throw new IllegalStateException("No available discs left.");
        }

        int diskIndex = random.nextInt(availableDiscs.size());
        Disc disc = availableDiscs.get(diskIndex);
        usedColors.add(disc);
        return new Player(disc, new BotRandomStrategy());
    }
}
