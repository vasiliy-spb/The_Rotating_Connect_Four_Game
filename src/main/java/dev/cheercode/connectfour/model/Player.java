package dev.cheercode.connectfour.model;

public class Player {
    private final String name;
    private final Disc disc;

    public Player(String name, Disc disc) {
        this.name = name;
        this.disc = disc;
    }

    public Player(Disc disc) {
        this.name = disc.name();
        this.disc = disc;
    }

    public String getName() {
        return name;
    }

    public Disc getDisc() {
        return disc;
    }
}
