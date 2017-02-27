/*
 * Alexander Frenette
 * Project 4 : Hunt the Wumpus
 * csc 335
 * Due February 27 2017
 * Description : A recreation of a classical game that moves a hunter to find the Wumpus
 */

package model;

public class Room {

    private boolean isHidden;
    private boolean isPit;
    private boolean hasSlime;
    private boolean hasBlood;
    private boolean hasHunter;
    private boolean hasWumpus;

    // TODO
    public Room() {
	this.isHidden = true;
    }

    public void setVisited() {
	this.isHidden = false;
    }

    public boolean isVisited() {
	return !this.isHidden;
    }

    public void setPit(boolean isPit) {
	this.isPit = isPit;
    }

    public boolean isPit() {
	return this.isPit;
    }

    public void setHasSlime(boolean hasSlime) {
	this.hasSlime = hasSlime;
    }

    public boolean hasSlime() {
	return this.hasSlime;
    }

    public void setHasBlood(boolean hasBlood) {
	this.hasBlood = hasBlood;
    }

    public boolean hasBlood() {
	return this.hasBlood;
    }

    // TODO : I could create 2 methods for adding and removing to eliminate
    // unnecessary calls to "isHiden"
    public void setHasHunter(boolean hasHunter) {
	this.isHidden = false;
	this.hasHunter = hasHunter;
    }

    public boolean hasHunter() {
	return this.hasHunter;
    }

    public void setHasWumpus(boolean hasWumpus) {
	this.hasWumpus = hasWumpus;
    }

    public boolean hasWumpus() {
	return this.hasWumpus;
    }

    public GamePiece getGamePiece() {
	if (isHidden) {
	    return GamePiece.HIDDENROOM;
	} else {
	    if (this.hasWumpus) {
		return GamePiece.WUMPUS;
	    } else if (this.hasHunter) {
		// TODO
		/*
		 * In the text view we need to see that it is a hunter all over
		 * anything else, but in graphics view I will need to see all
		 * the things it is
		 */
		return GamePiece.HUNTER;
	    } else if (this.isPit) {
		return GamePiece.PIT;
	    } else if (this.hasSlime) {
		if (this.hasBlood) {
		    return GamePiece.GOOP;
		} else {
		    return GamePiece.SLIME;
		}
	    } else if (this.hasBlood) {
		return GamePiece.BLOOD;
	    } else {
		return GamePiece.EMPTYROOM;

	    }
	}
    }

    public GamePiece getGamePieceGraphicalView() {
	if (this.isPit) {
	    return GamePiece.PIT;
	} else if (this.hasSlime) {
	    if (this.hasBlood) {
		return GamePiece.GOOP;
	    } else {
		return GamePiece.SLIME;
	    }
	} else if (this.hasBlood) {
	    return GamePiece.BLOOD;
	} else {
	    return GamePiece.EMPTYROOM;
	}
    }

    public void setGamePiece(GamePiece gamePiece) {
	if (gamePiece == GamePiece.BLOOD) {
	    this.hasBlood = true;
	} else if (gamePiece == GamePiece.SLIME) {
	    this.hasSlime = true;
	}
    }

    public String toString() {
	return this.getGamePiece().toString();
    }
}
