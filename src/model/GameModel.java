/*
 * Alexander Frenette
 * Project 4 : Hunt the Wumpus
 * csc 335
 * Due February 27 2017
 * Description : A recreation of a classical game that moves a hunter to find the Wumpus
 */

package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class GameModel extends Observable {

    private final int boardRow = 10;
    private final int boardColumn = 10;
    private final Random random = new Random();
    private final int bloodRadius = 2;

    private Room[][] board;
    private Position hunterPosition;
    private Position wumpusPosition;
    private boolean hasWon;
    private boolean hasLost;
    private boolean gameOver;
    private boolean hunterDead;
    private boolean wompasDead;

    public GameModel() {
	initilizeBoard();
    }

    private void initilizeBoard() {
	this.initilizeRooms();
	this.initilizeWumpus();
	this.initilizePits();
	this.initilizeHunter();
    }

    private void initilizeRooms() {
	this.board = new Room[this.boardRow][this.boardColumn];

	for (int row = 0; row < this.boardRow; row++) {
	    for (int column = 0; column < this.boardColumn; column++) {
		// this.board[row][column] = new Room();

		/*
		 * START : used for testing
		 */
		Room tempRoom = new Room();
		// TESTING
		// tempRoom.setVisited();
		this.board[row][column] = tempRoom;
		/*
		 * END : used for testing
		 */
	    }
	}
    }

    private void initilizeWumpus() {
	this.wumpusPosition = new Position(this.random.nextInt(10), this.random.nextInt(10));

	// add the Wumpus to the board and to the room
	this.getRoom(this.wumpusPosition).setHasWumpus(true);

	// place the blood around the Wumpus with a radius of two
	this.setPointsWithinRadius(this.wumpusPosition, this.bloodRadius, GamePiece.BLOOD);
    }

    private void initilizePits() {
	/*
	 * Set the number of pits (between 3 and 5) and the slime around them
	 */
	int pitCount = 3 + this.random.nextInt(2);
	ArrayList<Position> pitPositions = new ArrayList<>();

	for (int index = 0; index < pitCount; index++) {
	    Position pitPosition;
	    int pitRow = this.random.nextInt(10);
	    int pitColumn = this.random.nextInt(10);
	    pitPosition = new Position(pitRow, pitColumn);

	    while ((pitRow == this.wumpusPosition.getRow() && pitColumn == this.wumpusPosition.getColumn())
		    || this.isDupilcatePit(pitPosition, pitPositions)) {
		pitRow = this.random.nextInt(10);
		pitColumn = this.random.nextInt(10);
		pitPosition = new Position(pitRow, pitColumn);
	    }

	    this.getRoom(pitPosition.getRow(), pitPosition.getColumn()).setPit(true);
	    pitPositions.add(pitPosition);
	    // add the slime around the position
	    this.setPointsWithinRadius(pitPosition, 1, GamePiece.SLIME);
	}
    }

    private void initilizeHunter() {
	Position tempPosition;

	do {
	    tempPosition = new Position(this.random.nextInt(10), this.random.nextInt(10));
	} while (!this.isSafePosition(tempPosition));

	// once it is a safe position we need to place it on the board
	this.addHunterToPosition(tempPosition);
    }

    private boolean isSafePosition(Position position) {
	Room room = this.getRoom(position);

	if (room.hasSlime() || room.hasBlood() || room.isPit() || room.hasWumpus()) {
	    return false;
	} else {
	    return true;
	}
    }

    /*
     * We are not actually setting a circle, we are doing a square that is
     * rotated 45 degrees
     */
    private void setPointsWithinRadius(Position center, int radius, GamePiece gamePiece) {
	for (int column = -radius, height = 0; column <= radius; column++) {
	    // set the center line
	    this.getRoom(center.getRow(), center.getColumn() + column).setGamePiece(gamePiece);

	    // set the top
	    for (int up = height; up > 0; up--) {
		this.getRoom(center.getRow() + up, center.getColumn() + column).setGamePiece(gamePiece);
	    }

	    // set the bottom
	    for (int down = height; down > 0; down--) {
		this.getRoom(center.getRow() - down, center.getColumn() + column).setGamePiece(gamePiece);
	    }

	    if (column < 0) {
		++height;
	    } else {
		--height;
	    }
	}
    }

    private boolean isDupilcatePit(Position pit, ArrayList<Position> pitPositions) {
	// System.out.println("Size : " + pitPositions.size());
	for (int index = 0; index < pitPositions.size(); index++) {
	    Position currentPosition = pitPositions.get(index);

	    if (currentPosition.getRow() == pit.getRow() && currentPosition.getColumn() == pit.getColumn()) {
		return true;
	    }
	}

	return false;
    }

    public void moveHunter(Direction direction) {
	Position tempPosition;

	if (direction == Direction.NORTH) {
	    // UP
	    tempPosition = new Position(this.hunterPosition.getRow() - 1, this.hunterPosition.getColumn());
	} else if (direction == Direction.SOUTH) {
	    // DOWN
	    tempPosition = new Position(this.hunterPosition.getRow() + 1, this.hunterPosition.getColumn());
	} else if (direction == Direction.WEST) {
	    // LEFT
	    tempPosition = new Position(this.hunterPosition.getRow(), this.hunterPosition.getColumn() - 1);
	} else {
	    // RIGHT
	    tempPosition = new Position(this.hunterPosition.getRow(), this.hunterPosition.getColumn() + 1);
	}

	// at this point I could remove the previous position information

	/*
	 * Remove the hunter from the current room, add the hunter to the new
	 * room, and then set the global hunter position to the tempPosition
	 */
	this.getRoom(this.hunterPosition).setHasHunter(false);

	this.getRoom(tempPosition).setHasHunter(true);

	// update the player's position
	this.hunterPosition = tempPosition;

	this.setChanged();
	this.notifyObservers();

	// check if hunter is dead
	if (this.isHunterDead()) {
	    this.hunterDead = true;
	    this.hasLost = true;
	    this.gameOver = true;

	    /*
	     * Make all rooms visible
	     */
	    this.makeAllRoomsVissible();
	    this.setChanged();
	    this.notifyObservers();
	}
    }

    public void makeAllRoomsVissible() {
	for (int row = 0; row < this.boardRow; row++) {
	    for (int column = 0; column < this.boardColumn; column++) {
		this.getRoom(row, column).setVisited();
	    }
	}
    }

    /*
     * Accounts for wrap around
     */
    public Room getRoom(int row, int column) {
	int tempRow = row % this.boardRow;
	if (tempRow < 0) {
	    tempRow += this.boardRow;
	}

	int tempColumn = column % this.boardColumn;
	if (tempColumn < 0) {
	    tempColumn += this.boardColumn;
	}

	return this.board[tempRow][tempColumn];
    }

    /*
     * Accounts for wrap around
     */
    public Room getRoom(Position position) {
	int tempRow = position.getRow() % this.boardRow;
	if (tempRow < 0) {
	    tempRow += this.boardRow;
	}

	int tempColumn = position.getColumn() % this.boardColumn;
	if (tempColumn < 0) {
	    tempColumn += this.boardColumn;
	}

	return this.board[tempRow][tempColumn];
    }

    private void addHunterToPosition(Position position) {
	Room roomOfHunter = this.getRoom(position);

	roomOfHunter.setHasHunter(true);

	// keep track of hunter in global variable
	this.hunterPosition = position;
    }

    public void shootArrow(Direction direction) {
	boolean killedWumpus = false;

	if (this.hunterPosition.getRow() == this.wumpusPosition.getRow()) {
	    if (direction == Direction.WEST || direction == Direction.EAST) {
		killedWumpus = true;
	    }
	} else if (this.hunterPosition.getColumn() == this.wumpusPosition.getColumn()) {
	    if (direction == Direction.NORTH || direction == Direction.SOUTH) {
		killedWumpus = true;
	    }
	}

	if (killedWumpus) {
	    this.hasWon = true;
	    this.wompasDead = true;

	} else {
	    this.hunterDead = true;
	    this.hasLost = true;
	    this.gameOver = true;
	    this.hasWon = false;
	    this.wompasDead = false;
	}
	
	this.makeAllRoomsVissible();
	this.setChanged();
	this.notifyObservers();
	this.gameOver = true;
    }

    @Override
    public String toString() {
	String returnString = new String();

	for (int row = 0; row < this.boardRow; row++) {
	    for (int column = 0; column < this.boardColumn; column++) {

		returnString += "[";
		returnString += this.board[row][column].toString();
		returnString += "]";

		if (column != (this.boardColumn - 1)) {
		    returnString += " ";
		} else {
		    if (row != (this.boardRow - 1)) {
			returnString += "\n\n";
		    }
		}
	    }
	}

	return returnString;
    }

    private boolean isHunterDead() {
	Room huntersRoom = this.getRoom(this.hunterPosition);
	if (huntersRoom.isPit() || huntersRoom.hasWumpus()) {
	    return true;
	} else {
	    return false;
	}
    }

//    public boolean isWumpusDead() {
//	Room huntersRoom = this.getRoom(this.hunterPosition);
//	if (huntersRoom.isPit() || huntersRoom.hasWumpus()) {
//	    return true;
//	} else {
//	    return false;
//	}
//    }

    public boolean isStillRunning() {
	if (this.hunterDead || this.wompasDead == true) {
	    return false;
	} else {
	    return true;
	}
    }

    /*
     * Alert the player of what they just stepped in for TextGamePanel
     */
    public String getWarningMessage() {
	GamePiece thisGamePiece = this.getRoom(this.hunterPosition).getGamePieceGraphicalView();

	if (thisGamePiece == GamePiece.BLOOD) {
	    return "The blood is still warm...";
	} else if (thisGamePiece == GamePiece.SLIME) {
	    return "I can feel the wind...";
	} else if (thisGamePiece == GamePiece.GOOP) {
	    return "A foul mixture is under foot...";
	} else if (thisGamePiece == GamePiece.EMPTYROOM) {
	    return "I will find you foul beast!";
	} else {
	    return null;
	}
    }

    /*
     * We are not actually getting a circle, we are doing a square that is
     * rotated 45 degrees
     */
    public ArrayList<Room> getSurroundingRooms(Position center, int radius) {
	ArrayList<Room> rooms = new ArrayList<>();

	for (int column = -radius, height = 0; column <= radius; column++) {
	    // get the center line
	    rooms.add(this.getRoom(center.getRow(), center.getColumn() + column));

	    // get the top
	    for (int up = height; up > 0; up--) {
		rooms.add(this.getRoom(center.getRow() + up, center.getColumn() + column));
	    }

	    // get the bottom
	    for (int down = height; down > 0; down--) {
		rooms.add(this.getRoom(center.getRow() - down, center.getColumn() + column));
	    }

	    if (column < 0) {
		++height;
	    } else {
		--height;
	    }
	}

	return rooms;
    }

    public boolean hunterWon() {
	return this.wompasDead;
    }
}
