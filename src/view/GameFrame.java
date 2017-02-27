package view;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import controller.HunterKeyListener;
import model.Direction;
import model.GameModel;

public class GameFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    // Keep game model
    private GameModel game;

    private HunterKeyListener hunterKeyListener;

    JTabbedPane jTabb;
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

	/*
	 * Start tabs : the problem with tabs is it becomes the action listener
	 */
	jTabb = new JTabbedPane();
	// TODO
	jTabb.addKeyListener(this.hunterKeyListener);
	jTabb.add("Text Game View", this.textView);
	jTabb.addTab("Graphical Game View", this.graphicalView);
	jTabb.setBackground(Color.RED);
	this.add(jTabb);

	// this.add(this.graphicalView);

	/*
	 * End tabs
	 */

	// add the observers
	this.addObservers();

	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(600, 600);
	this.setLocation(100, 40);
	this.setTitle("Hunt The Wumpus");

	// TODO
	this.addKeyListener(this.hunterKeyListener);

	this.setVisible(true);
    }

    private void addObservers() {
	this.game.addObserver(textView);
	this.game.addObserver(graphicalView);
    }
}
