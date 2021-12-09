
/**
 * @filename Queen.java
 * @author Garrison Mendlik 12/8/2021
 * TODO: Add your names
 * @purpose Queen chess piece implementation.
 */

package model.pieces;

import java.util.ArrayList;

public class Queen extends ChessPiece {

	/**
	 * Creates a new queen piece.
	 * 
	 * @param row   Row to place the queen at
	 * @param col   Column to place the queen at
	 * @param color Team color of the queen
	 */
	public Queen(int row, int col, int color) {
		super(row, col, color);
		name = "Queen";
	}

	@Override
	/**
	 * Checks if the move is valid for the queen piece
	 * 
	 * @param row    Row to attempt to move to
	 * @param col    Column to attempt to move to
	 * @param pieces ChessPiece[][] array containing the board's pieces
	 * @return Returns true if the queen can make the move
	 */
	public boolean isValidMove(int row, int col, ChessPiece[][] pieces) {

		if (pieces[col][row] != null
				&& pieces[col][row].getColor() == this.color) {
			return false;
		}

		ArrayList<ArrayList<PosTouple>> possibilities = this.findRange();
		for (int currDir = 0; currDir < possibilities.size(); currDir++) {
			ArrayList<PosTouple> currList = possibilities.get(currDir);
			boolean isBlocked = false;
			for (PosTouple pos : currList) {
				if (row == pos.row && col == pos.col) {
					if (pieces[pos.col][pos.row] == null && !isBlocked)
						return true;
					if (pieces[pos.col][pos.row] == null && isBlocked)
						return false;
					if (pieces[pos.col][pos.row].color != this.color) {
						if (!isBlocked)
							return true;
						return false;
					}
					if (pieces[pos.col][pos.row].color == this.color)
						return false;
				}
				if (pieces[pos.col][pos.row] != null)
					isBlocked = true;
			}
			isBlocked = false;
		}
		return false;
	}

	/**
	 * Holds row,col position for the board's squares.
	 */
	private class PosTouple {
		public int row;
		public int col;

		public PosTouple(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	/**
	 * Finds all the possible squares that the queen could move to IF the queen
	 * was the only piece on the board.
	 * 
	 * ArrayList 0: up ArrayList 1: down ArrayList 2: right ArrayList 3: left
	 * ArrayList 4: up and right ArrayList 5: down and right ArrayList 6: down
	 * and left ArrayList 7: up and left
	 * 
	 * @return Returns ArrayList of ArrayLists that hold Touples of the
	 *         positions that the queen could move to.
	 */
	private ArrayList<ArrayList<PosTouple>> findRange() {
		ArrayList<ArrayList<PosTouple>> possibilities = new ArrayList<ArrayList<PosTouple>>();
		PosTouple currPos = null;

		// get moves going up
		ArrayList<PosTouple> currDirectionList = new ArrayList<PosTouple>();
		for (int currRow = this.row + 1; currRow < 8; currRow++) {
			currPos = new PosTouple(currRow, this.col);
			currDirectionList.add(currPos);
		}
		possibilities.add(currDirectionList);

		// get moves going down
		currDirectionList = new ArrayList<PosTouple>();
		for (int currRow = this.row - 1; currRow >= 0; currRow--) {
			currPos = new PosTouple(currRow, this.col);
			currDirectionList.add(currPos);
		}
		possibilities.add(currDirectionList);

		// get moves going to right
		currDirectionList = new ArrayList<PosTouple>();
		for (int currCol = this.col + 1; currCol < 8; currCol++) {
			currPos = new PosTouple(this.row, currCol);
			currDirectionList.add(currPos);
		}
		possibilities.add(currDirectionList);

		// get moves going to left
		currDirectionList = new ArrayList<PosTouple>();
		for (int currCol = this.col - 1; currCol >= 0; currCol--) {
			currPos = new PosTouple(this.row, currCol);
			currDirectionList.add(currPos);
		}
		possibilities.add(currDirectionList);

		// get moves going up and to right
		currDirectionList = new ArrayList<PosTouple>();
		int currStep = 1;
		while (this.row + currStep < 8 && this.col + currStep < 8) {
			currPos = new PosTouple(this.row + currStep, this.col + currStep);
			currDirectionList.add(currPos);
			currStep++;
		}
		possibilities.add(currDirectionList);

		// get moves going down and to right
		currDirectionList = new ArrayList<PosTouple>();
		currStep = 1;
		while (this.row - currStep >= 0 && this.col + currStep < 8) {
			currPos = new PosTouple(this.row - currStep, this.col + currStep);
			currDirectionList.add(currPos);
			currStep++;
		}
		possibilities.add(currDirectionList);

		// get moves going down and to left
		currDirectionList = new ArrayList<PosTouple>();
		currStep = 1;
		while (this.row - currStep >= 0 && this.col - currStep >= 0) {
			currPos = new PosTouple(this.row - currStep, this.col - currStep);
			currDirectionList.add(currPos);
			currStep++;
		}
		possibilities.add(currDirectionList);

		// get moves going up and to left
		currDirectionList = new ArrayList<PosTouple>();
		currStep = 1;
		while (this.row + currStep < 8 && this.col - currStep >= 0) {
			currPos = new PosTouple(this.row + currStep, this.col - currStep);
			currDirectionList.add(currPos);
			currStep++;
		}
		possibilities.add(currDirectionList);
		return possibilities;
	}
}
