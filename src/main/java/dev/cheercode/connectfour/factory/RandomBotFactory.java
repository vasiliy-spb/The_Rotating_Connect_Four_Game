package dev.cheercode.connectfour.factory;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.player.RandomBotStrategy;
import dev.cheercode.connectfour.model.player.Player;

public class RandomBotFactory extends AbstractBotFactory {
    @Override
    protected Player createBot(Disc disc) {
        return new Player(disc, new RandomBotStrategy());
    }
}
