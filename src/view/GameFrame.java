/*
 * Alexander Frenette
 * Project 4 : Hunt the Wumpus
 * csc 335
 * Due February 27 2017
 * Description : A recreation of a classical game that moves a hunter to find the Wumpus
 */

package view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import controller.HunterKeyListener;
import model.GameModel;

public class GameFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private GameModel game;

    private HunterKeyListener hunterKeyListener;

    private JTabbedPane jTabb;
    private TextGamePanel textView;
    private GraphicalGamePanel graphicalView;

    public static void main(String[] args) {
	GameFrame g = new GameFrame();
    }

    public GameFrame() {
	this.game = new GameModel();
	this.hunterKeyListener = new HunterKeyListener(this.game);
	this.textView = new TextGamePanel(this.game, this.hunterKeyListener);
	this.graphicalView = new GraphicalGamePanel(this.game, this.hunterKeyListener);

	// add the observers
	this.addObservers();

	/*
	 * Start tabs : the problem with tabs is it becomes the action listener
	 */
	jTabb = new JTabbedPane();
	jTabb.add("Text Game View", this.textView);
	jTabb.addTab("Graphical Game View", this.graphicalView);
	jTabb.addKeyListener(this.hunterKeyListener);
	this.add(jTabb);
	/*
	 * End tabs
	 */

	// this.add(this.graphicalView);

	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(600, 650);
	this.setLocation(200, 100);
	this.setTitle("Hunt The Wumpus");

	// Needed if I am using jpanel alone
	this.addKeyListener(this.hunterKeyListener);

	this.setVisible(true);
    }

    private void addObservers() {
	this.game.addObserver(textView);
	this.game.addObserver(graphicalView);
    }
}
