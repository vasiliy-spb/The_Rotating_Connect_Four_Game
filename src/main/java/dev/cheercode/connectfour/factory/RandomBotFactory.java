package dev.cheercode.connectfour.factory;

import dev.cheercode.connectfour.game.PlayerQueue;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.player.BotRandomStrategy;
import dev.cheercode.connectfour.model.player.Player;

import java.util.*;

public class RandomBotFactory implements PlayerFactory {
    private final Random random;
    private Set<Disc> usedColors;

    public RandomBotFactory() {
        this.usedColors = new HashSet<>();
        this.random = new Random();
    }

    @Override
    public Player create(PlayerQueue playerQueue) {
        List<Player> players = playerQueue.toList();
        players.stream()
                .map(Player::getDisc)
                .forEach(usedColors::add);
        List<Disc> availableDiscs = Arrays.stream(Disc.values())
                .filter(d -> !usedColors.contains(d))
                .toList();

        if (availableDiscs.isEmpty()) {
            throw new IllegalStateException("No available discs left.");
        }

        int diskIndex = random.nextInt(availableDiscs.size());
        Disc disc = availableDiscs.get(diskIndex);
        return new Player(disc, new BotRandomStrategy());
    }
}
