package dev.cheercode.connectfour.factory;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.player.Player;
import dev.cheercode.connectfour.model.player.VictoryScoreBasedBotStrategy;

public class VictoryScoreBasedBotFactory extends AbstractBotFactory {
    @Override
    protected Player createBot(Disc disc) {
        return new Player(disc, new VictoryScoreBasedBotStrategy());
    }
}
