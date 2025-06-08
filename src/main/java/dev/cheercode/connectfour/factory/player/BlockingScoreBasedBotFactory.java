package dev.cheercode.connectfour.factory.player;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.player.move_strategy.BlockingScoreBasedBotStrategy;
import dev.cheercode.connectfour.model.player.Player;

public class BlockingScoreBasedBotFactory extends AbstractBotFactory {
    @Override
    protected Player createBot(Disc disc) {
        return new Player(disc, new BlockingScoreBasedBotStrategy());
    }
}
