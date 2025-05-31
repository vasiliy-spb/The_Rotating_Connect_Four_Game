package dev.cheercode.connectfour.game;

import dev.cheercode.connectfour.model.player.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class PlayerQueue {
    private final Queue<Player> players;

    public PlayerQueue() {
        this.players = new ArrayDeque<>();
    }

    public void add(Player player) {
        players.offer(player);
    }

    public Player peekNext() {
        return players.peek();
    }

    public Player pollNext() {
        return players.poll();
    }

    public int size() {
        return players.size();
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public List<Player> toList() {
        return new ArrayList<>(players);
    }
}
