package dev.cheercode.connectfour.factory;

import dev.cheercode.connectfour.game.PlayerQueue;
import dev.cheercode.connectfour.model.player.Player;

public interface PlayerFactory {
    Player create(PlayerQueue playerQueue);
}
