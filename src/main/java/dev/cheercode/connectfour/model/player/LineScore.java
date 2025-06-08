package dev.cheercode.connectfour.model.player;

public record LineScore(
        int length,
        int score,
        int adjacentCount
) {
}
