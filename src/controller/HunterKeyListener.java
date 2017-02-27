/*
 * Alexander Frenette
 * Project 4 : Hunt the Wumpus
 * csc 335
 * Due February 27 2017
 * Description : A recreation of a classical game that moves a hunter to find the Wumpus
 */

package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Direction;
import model.GameModel;

public class HunterKeyListener implements KeyListener {
    
    private GameModel game;
    
    public HunterKeyListener(GameModel game) {
	this.game = game;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {

	/*
	 * Only make the move if the game isn't over
	 */
	if (this.game.isStillRunning()) {
	    char keyPressed = Character.toLowerCase(e.getKeyChar());
	    Direction moveDirection;

	    if (keyPressed == 'w') {
		moveDirection = Direction.NORTH;
	    } else if (keyPressed == 's') {
		moveDirection = Direction.SOUTH;
	    } else if (keyPressed == 'a') {
		moveDirection = Direction.WEST;
	    } else if (keyPressed == 'd') {
		moveDirection = Direction.EAST;
	    } else {
		moveDirection = Direction.UNKNOWN;
	    }

	    // TODO
	    if (moveDirection != Direction.UNKNOWN) {
		System.out.println("Moving : " + moveDirection);
		System.out.println(e);
		System.out.println(e.getKeyChar());
		this.game.moveHunter(moveDirection);
		//this.game.setChanged();
		System.out.println(this.game.hasChanged());
		
	    }
	}
    }

    @Override
    public void keyPressed(KeyEvent e) {

	/*
	 * Only make the move if the game isn't over
	 */
	if (this.game.isStillRunning()) {
	    int keyPressed = e.getKeyCode();
	    Direction moveDirection;

	    if (keyPressed == KeyEvent.VK_UP) {
		moveDirection = Direction.NORTH;
	    } else if (keyPressed == KeyEvent.VK_DOWN) {
		moveDirection = Direction.SOUTH;
	    } else if (keyPressed == KeyEvent.VK_LEFT) {
		moveDirection = Direction.WEST;
	    } else if (keyPressed == KeyEvent.VK_RIGHT) {
		moveDirection = Direction.EAST;
	    } else {
		moveDirection = Direction.UNKNOWN;
	    }

	    // TODO
	    if (moveDirection != Direction.UNKNOWN) {
		System.out.println("Shooting arrow : " + moveDirection);
		System.out.println(e);
		System.out.println(e.getKeyChar());
		this.game.shootArrow(moveDirection);
	    }
	}
    }

    @Override
    public void keyReleased(KeyEvent e) {
	/*
	 * Do nothing
	 */
    }

}
