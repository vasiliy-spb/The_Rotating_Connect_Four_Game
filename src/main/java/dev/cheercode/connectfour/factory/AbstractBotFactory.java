package dev.cheercode.connectfour.factory;

import dev.cheercode.connectfour.game.PlayerQueue;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractBotFactory implements PlayerFactory {
    private final Random random;

    public AbstractBotFactory() {
        this.random = new Random();
    }

    @Override
    public Player create(PlayerQueue playerQueue) {
        List<Player> players = playerQueue.toList();
        Set<Disc> usedColors = players.stream()
                .map(Player::getDisc)
                .collect(Collectors.toSet());
        List<Disc> availableDiscs = Arrays.stream(Disc.values())
                .filter(d -> !usedColors.contains(d))
                .toList();

        if (availableDiscs.isEmpty()) {
            throw new IllegalStateException("No available discs left.");
        }

        int diskIndex = random.nextInt(availableDiscs.size());
        Disc disc = availableDiscs.get(diskIndex);
        return createBot(disc);
    }

    protected abstract Player createBot(Disc disc);
}
