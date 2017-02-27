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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import controller.HunterKeyListener;
import model.GameModel;
import model.GamePiece;
import model.Room;

public class GraphicalGamePanel extends JPanel implements Observer {

    private static final long serialVersionUID = 1L;

    private GameModel game;
    private HunterKeyListener hunterKeyListener;

    public GraphicalGamePanel(GameModel game, HunterKeyListener hunterKeyListener) {
	this.game = game;
	this.hunterKeyListener = hunterKeyListener;
	this.addKeyListener(this.hunterKeyListener);
    }

    public void paintComponent(Graphics g) {
	Graphics2D graphics = (Graphics2D) g;

	int imgHeight = 50;
	int imgWidth = 50;

	int imgX = 0;
	int imgY = 0;

	int rowCount = 10;
	int columnCount = 10;

	try {
	    BufferedImage img = null;

	    BufferedImage wumpusImg = ImageIO.read(new File("images/Wumpus.png"));
	    BufferedImage hunterImg = ImageIO.read(new File("images/TheHunter.png"));
	    BufferedImage pitImg = ImageIO.read(new File("images/SlimePit.png"));
	    BufferedImage goopImg = ImageIO.read(new File("images/Goop.png"));
	    BufferedImage slimeImg = ImageIO.read(new File("images/Slime.png"));
	    BufferedImage bloodImg = ImageIO.read(new File("images/Blood.png"));
	    BufferedImage emptyRoomImg = ImageIO.read(new File("images/Ground.png"));
	    BufferedImage black = ImageIO.read(new File("images/Black.png"));

	    // Set the state of Y = 0
	    for (int row = 0; row < rowCount; row++, imgY += imgHeight, imgX = 0) {
		for (int column = 0; column < columnCount; column++, imgX += imgWidth) {
		    // get the state of the room
		    Room thisRoom = this.game.getRoom(row, column);

		    if (thisRoom.isVisited()) {
			// get the GamePiece in the room
			GamePiece gamePiece = thisRoom.getGamePieceGraphicalView();

			switch (gamePiece) {
			case PIT:
			    img = pitImg;
			    break;
			case GOOP:
			    img = goopImg;
			    break;
			case SLIME:
			    img = slimeImg;
			    break;
			case BLOOD:
			    img = bloodImg;
			    break;
			case EMPTYROOM:
			    img = emptyRoomImg;
			    break;
			default:
			    img = null;
			    break;
			}

			// always draw the floor
			graphics.drawImage(emptyRoomImg, imgX, imgY, imgHeight, imgWidth, null);
			// draw the element in the room
			graphics.drawImage(img, imgX, imgY, imgHeight, imgWidth, null);

			// draw the hunter
			if (thisRoom.hasHunter()) {
			    graphics.drawImage(hunterImg, imgX, imgY, imgHeight, imgWidth, null);
			}

			// draw the wumpus
			if (thisRoom.hasWumpus()) {
			    graphics.drawImage(wumpusImg, imgX, imgY, imgHeight, imgWidth, null);
			}
		    } else {
			// draw the black room for those not visited
			graphics.drawImage(black, imgX, imgY, imgHeight, imgWidth, null);
		    }
		}
	    }

	    /*
	     * TODO : we have drawn the board, now display if we have won or not
	     */
	    if (!this.game.isStillRunning()) {
		String message;
		Dimension thisDimension = this.getSize();
		int thisHeight = (int) thisDimension.getHeight();
		int thisWidth = (int) thisDimension.getWidth();
		
		int newFontSize = (int) (26);
		
		Font thisFont = new Font("Courier", Font.BOLD, newFontSize);
		FontMetrics metrics = graphics.getFontMetrics(thisFont);
		graphics.setFont(thisFont);
		graphics.setColor(Color.WHITE);

		int centerX = thisWidth / 2;
		int centerY = thisHeight / 2;

		if (this.game.hunterWon()) {
		    message = "The Hunter Has WON!";
		} else {
		    message = "The Hunter Has Been Defeated!";
		}

		graphics.drawString(message, (centerX - (metrics.stringWidth(message) / 2)),
			(centerY + (metrics.getHeight() / 4)));
	    }
	} catch (Exception e) {
	    System.out.println("ERROR : painting");
	}
    }

    @Override
    public void update(Observable o, Object arg) {
	this.repaint();
    }
}
