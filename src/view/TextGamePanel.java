/*
 * Alexander Frenette
 * Project 4 : Hunt the Wumpus
 * csc 335
 * Due February 27 2017
 * Description : A recreation of a classical game that moves a hunter to find the Wumpus
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.HunterKeyListener;
import model.GameModel;

public class TextGamePanel extends JPanel implements Observer {

    private static final long serialVersionUID = 1L;

    private final int rowCount = 39;
    private final int columnCount = 39;

    private GameModel game;
    private HunterKeyListener hunterKeyListener;
    private JTextArea textArea;
    private JLabel warningMessage;

    public TextGamePanel(GameModel game, HunterKeyListener hunterKeyListener) {
	this.game = game;
	this.hunterKeyListener = hunterKeyListener;
	this.addKeyListener(this.hunterKeyListener);
	this.initilizeWarningJLabel();
	this.initilizeJTextArea();
    }
    
    private void initilizeWarningJLabel() {
	this.warningMessage = new JLabel(this.game.getWarningMessage());
	
	/*
	 *  TODO : set the font size, set the proffered size etc.
	 */
	int newFontSize = (int) (26);
	Font thisFont = new Font("Courier", Font.BOLD, newFontSize);
	this.warningMessage.setFont(thisFont);
	
	this.add(this.warningMessage);
    }

    private void initilizeJTextArea() {
	this.textArea = new JTextArea(this.rowCount, this.columnCount);
	this.textArea.addKeyListener(this.hunterKeyListener);
	
	int fontSize = 20;
	int width;
	int newFontSize = (int) (fontSize - (fontSize * 0.3));
	Font thisFont = new Font("Courier", Font.BOLD, newFontSize);
	FontMetrics metrics = this.textArea.getFontMetrics(thisFont);
	// TODO
	width = metrics.stringWidth("[ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]");

	this.textArea.setMaximumSize(new Dimension(width * 2, width * 2));
	// TODO : chance font-size to be dynamic based off of the size of the
	// gamePanel
	this.textArea.setFont(new Font("Courier", Font.BOLD, 20));
	this.textArea.setText(this.game.toString());
	// TODO
	// disable modification of textArea
	this.textArea.setEditable(false);
	// add the textArea to the panel
	this.add(textArea);
	
	
    }

    @Override
    public void update(Observable o, Object arg) {
	/*
	 * TODO : called whenever the GameModel has changed. Re-render the game
	 * state.
	 */
	if (this.game.isStillRunning()) {
	    this.warningMessage.setText(this.game.getWarningMessage());
	} else {
	    if (this.game.hunterWon()) {
		this.warningMessage.setText("The Hunter WON!");
	    } else {
		this.warningMessage.setText("The Hunter has been Defeated!");
	    }
	}
	
	this.textArea.setText(this.game.toString());
    }

}
