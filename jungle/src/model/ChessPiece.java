package model;


import java.io.Serializable;

public class ChessPiece implements Serializable {
    // the owner of the chess
    private PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private String name;
    private int rank;

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        if (this.owner.equals(target.owner)) return false;
        if (this.name.equals("Elephant")&&target.name.equals("Rat")) return false;
        else if (this.name.equals("Rat")&&target.name.equals("Elephant")) return true;
        return this.rank >= target.rank;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }
}
