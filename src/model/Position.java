/*
 * Alexander Frenette
 * Project 4 : Hunt the Wumpus
 * csc 335
 * Due February 27 2017
 * Description : A recreation of a classical game that moves a hunter to find the Wumpus
 */

package model;

public class Position {

    private final int boardRow = 10;
    private final int boardColumn = 10;
    
    private int row;
    private int column;

    /*
     * Because of the wrap around I will be taking care of the position in here
     */
    public Position(int row, int column) {
	this.row = row % this.boardRow;
	if (this.row < 0) {
	    this.row += this.boardRow;
	}
	
	this.column = column % this.boardColumn;
	if (this.column < 0) {
	    this.column += this.boardColumn;
	}
	
    }

    public int getRow() {
	return this.row;
    }

    public int getColumn() {
	return this.column;
    }

}
