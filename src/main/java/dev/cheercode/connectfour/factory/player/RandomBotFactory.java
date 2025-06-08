package dev.cheercode.connectfour.factory.player;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.player.move_strategy.RandomBotStrategy;
import dev.cheercode.connectfour.model.player.Player;

public class RandomBotFactory extends AbstractBotFactory {
    @Override
    protected Player createBot(Disc disc) {
        return new Player(disc, new RandomBotStrategy());
    }
}
