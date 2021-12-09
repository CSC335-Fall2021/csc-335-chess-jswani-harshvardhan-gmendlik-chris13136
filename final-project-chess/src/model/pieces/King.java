
/**
 * @filename King.java
 * @author Garrison Mendlik 12/8/2021
 * @author Jasnam Swani
 * @author Harshvardhan Bhatnagar
 * @author Chris Brinkley
 * @purpose King chess piece implementation.
 */

package model.pieces;

public class King extends ChessPiece {

	/**
	 * Creates a new king.
	 * 
	 * @param row   Row to place king at
	 * @param col   Column to place king at
	 * @param color Team color of king
	 */
	public King(int row, int col, int color) {
		super(row, col, color);
		name = "King";
	}

	@Override
	/**
	 * Checks if the move is valid for the king piece
	 * 
	 * @param row    Row to attempt to move to
	 * @param col    Column to attempt to move to
	 * @param pieces ChessPiece[][] array containing the board's pieces
	 * @return Returns true if the king can make the move
	 */
	public boolean isValidMove(int row, int col, ChessPiece[][] pieces) {
		if (pieces[col][row] != null
				&& pieces[col][row].getColor() == this.color) {
			return false;
		}
		if (this.row == row && this.col == col) {
			return false;
		}
		if ((this.row + 1 == row || this.row - 1 == row || this.row == row)
				&& (this.col + 1 == col || this.row - 1 == col
						|| this.row == col)) {
			return true;
		}
		return false;
	}

}
