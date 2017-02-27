/*
 * Alexander Frenette
 * Project 4 : Hunt the Wumpus
 * csc 335
 * Due February 27 2017
 * Description : A recreation of a classical game that moves a hunter to find the Wumpus
 */

package tests;

import org.junit.Test;

import model.Direction;
import model.GameModel;
import model.Position;
import model.Room;
import view.GameFrame;

public class Testing {

    @Test
    public void testGUI() {
	GameFrame frame = new GameFrame();
    }

    @Test
    public void testModulus() {
	System.out.println(-1 % 10);

    }

    @Test
    public void testBoard() {
	GameModel game = new GameModel();

	game.isStillRunning();
	game.hunterWon();
	game.getSurroundingRooms(new Position(3, 3), 4);
	game.getWarningMessage();
	// game.isWumpusDead();
	game.moveHunter(Direction.NORTH);
	game.moveHunter(Direction.SOUTH);
	game.moveHunter(Direction.EAST);
	game.moveHunter(Direction.WEST);
	game.makeAllRoomsVissible();
	game.shootArrow(Direction.NORTH);
	System.out.println(game);

	// new game
	for (int count = 0; count < 5000; count++) {
	    game.isStillRunning();
	    game.hunterWon();
	    game.getSurroundingRooms(new Position(3, 3), 4);
	    game.getWarningMessage();
	    // game.isWumpusDead();
	    game.moveHunter(Direction.NORTH);
	    game.moveHunter(Direction.SOUTH);
	    for (int index = 0; index < 10; index++) {
		game.moveHunter(Direction.NORTH);
		game.getWarningMessage();
		game.moveHunter(Direction.WEST);
		game.getWarningMessage();
		game.moveHunter(Direction.WEST);
		game.getWarningMessage();
		game.moveHunter(Direction.WEST);
		game.getWarningMessage();
		game.moveHunter(Direction.WEST);
		game.getWarningMessage();
		game.moveHunter(Direction.WEST);
		game.getWarningMessage();
		game.moveHunter(Direction.WEST);
		game.getWarningMessage();
		game.moveHunter(Direction.WEST);
		game.getWarningMessage();
		game.moveHunter(Direction.WEST);
		game.getWarningMessage();
		game.moveHunter(Direction.WEST);
		game.getWarningMessage();
		game.moveHunter(Direction.WEST);
		game.getWarningMessage();
	    }
	    game.makeAllRoomsVissible();
	    if (count <= 1000) {
		game.shootArrow(Direction.NORTH);
	    } else if (count <= 2000) {
		game.shootArrow(Direction.SOUTH);
	    } else if (count <= 3000) {
		game.shootArrow(Direction.EAST);
	    } else {
		game.shootArrow(Direction.WEST);
	    }
	    System.out.println(game);
	}

    }

    @Test
    public void testingRoom() {
	// HUNTER('O'), HIDDENROOM('X'), SLIME('S'), BLOOD('B'), GOOP('G'),
	// WUMPUS('W'), PIT('P'), EMPTYROOM(' ');

	Room blood = new Room();
	blood.setVisited();
	blood.setHasBlood(true);
	System.out.println("blood ('B') : " + blood);

	Room slime = new Room();
	slime.setVisited();
	slime.setHasSlime(true);
	System.out.println("slime ('S') : " + slime);

	Room pit = new Room();
	pit.setVisited();
	pit.setHasBlood(true);
	System.out.println("pit ('P') : " + pit);

	Room wumpus = new Room();
	wumpus.setVisited();
	wumpus.setHasWumpus(true);
	System.out.println("wumpus ('W') : " + wumpus);

	Room hunter = new Room();
	hunter.setVisited();
	hunter.setHasHunter(true);
	System.out.println("hunter ('O') : " + hunter);

	Room goop = new Room();
	goop.setVisited();
	goop.setHasBlood(true);
	goop.setHasSlime(true);
	System.out.println("goop ('G') : " + goop);

	Room empty = new Room();
	empty.setVisited();
	System.out.println("empty (' ') : " + empty);

	Room hidden = new Room();
	System.out.println("hidden ('X') : " + hidden);

    }
}
