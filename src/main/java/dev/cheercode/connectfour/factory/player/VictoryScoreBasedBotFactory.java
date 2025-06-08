package dev.cheercode.connectfour.factory.player;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.player.Player;
import dev.cheercode.connectfour.model.player.move_strategy.VictoryScoreBasedBotStrategy;

public class VictoryScoreBasedBotFactory extends AbstractBotFactory {
    @Override
    protected Player createBot(Disc disc) {
        return new Player(disc, new VictoryScoreBasedBotStrategy());
    }
}
