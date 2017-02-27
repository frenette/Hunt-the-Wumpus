package view;

import java.awt.Color;
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
	
	// set background color
	this.setBackground(Color.RED);
	revalidate();
	repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
	/*
	 * TODO : called whenever the GameModel has changed. Rerender the game
	 * state.
	 */
	revalidate();
	repaint();
    }

    public void paintComponent(Graphics g) {
	Graphics2D graphics = (Graphics2D) g;

	// Read from images folder parallel to src in your project
	int imgHeight = 50;
	int imgWidth = 50;

	int imgX = 0;
	int imgY = 0;

	int rowCount = 10;
	int columnCount = 10;

	// set background color
	this.setBackground(Color.RED);

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

	    for (int row = 0; row < rowCount; row++, imgX += imgWidth) {
		// reset imgY back to 0;
		imgY = 0;

		for (int column = 0; column < columnCount; column++, imgY += imgWidth) {
		    // get the state of the room
		    Room thisRoom = this.game.getRoom(row, column);

		    if (thisRoom.isVisited()) {
			// we can draw it
			// get the GamePiece in the room
			GamePiece gamePiece = thisRoom.getGamePieceGraphicalView();
			// System.out.println(gamePiece.toString());

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

			// draw the piece
			// System.out.println("Does it equal : " + (img ==
			// slimeImg));
			graphics.drawImage(emptyRoomImg, imgY, imgX, imgHeight, imgWidth, null);
			graphics.drawImage(img, imgY, imgX, imgHeight, imgWidth, null);
			if (thisRoom.hasHunter()) {
			    graphics.drawImage(hunterImg, imgY, imgX, imgHeight, imgWidth, null);
			}
			if (thisRoom.hasWumpus()) {
			    graphics.drawImage(wumpusImg, imgY, imgX, imgHeight, imgWidth, null);
			}
		    } else {
			// do not draw it
			graphics.drawImage(black, imgY, imgX, imgHeight, imgWidth, null);
			continue;
		    }
		}
	    }

	    /*
	     * TODO : we have drawn the board, now display if we have won or not
	     */
	    if (!this.game.isStillRunning()) {
		String message;
		int newFontSize = (int) (42);
		Font thisFont = new Font("Courier", Font.BOLD, newFontSize);
		FontMetrics metrics = graphics.getFontMetrics(thisFont);
		graphics.setFont(thisFont);
		graphics.setColor(Color.WHITE);

		int centerX = 500 / 2;
		int centerY = 500 / 2;

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
}
