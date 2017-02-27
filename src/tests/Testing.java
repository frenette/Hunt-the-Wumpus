package tests;

import org.junit.Test;

import model.GameModel;
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

	System.out.println(game);

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
