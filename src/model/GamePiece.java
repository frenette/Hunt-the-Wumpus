/*
 * Alexander Frenette
 * Project 4 : Hunt the Wumpus
 * csc 335
 * Due February 27 2017
 * Description : A recreation of a classical game that moves a hunter to find the Wumpus
 */

package model;

public enum GamePiece {
    
    HUNTER("O"), HIDDENROOM("X"), SLIME("S"), BLOOD("B"), GOOP("G"), WUMPUS("W"), PIT("P"), EMPTYROOM(" ");

    private String gamePieceString;
    
    GamePiece(String gamePieceString) {
	this.gamePieceString = gamePieceString;
    }
    
    public String toString() {
	return this.gamePieceString;
    }
    
    // [O] Hunter
    // [X] Hidden Room (not yet visited)
    // [S] Slime
    // [B] Blood
    // [G] Goop (blood and slime in the same room)
    // [W] Wumpus
    // [P] Pit
    // [ ] Visited room with nothing in it
}
