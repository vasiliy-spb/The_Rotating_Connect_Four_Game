package dev.cheercode.connectfour;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.Player;

public class PlayerCreator {
    public Player createPlayer(String name, Disc color) {
        return new Player(name, color);
    }
}
