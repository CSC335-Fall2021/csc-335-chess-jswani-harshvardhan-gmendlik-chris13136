
/**
 * @filename Knight.java
 * @author Garrison Mendlik 12/8/2021
 * 		   Jasnam Swani
 * TODO: Add your names
 * @purpose Knight chess piece implementation.
 */

package model.pieces;

public class Knight extends ChessPiece {

	/**
	 * Creates a new knight piece.
	 * 
	 * @param row   Row to place knight at
	 * @param col   Column to place knight at
	 * @param color Team color of the knight
	 */
	public Knight(int row, int col, int color) {
		super(row, col, color);
		name = "Knight";
	}

	@Override
	/**
	 * Checks if the move is valid for the knight piece
	 * 
	 * @param row    Row to attempt to move to
	 * @param col    Column to attempt to move to
	 * @param pieces ChessPiece[][] array containing the board's pieces
	 * @return Returns true if the knight can make the move
	 */
	public boolean isValidMove(int row, int col, ChessPiece[][] pieces) {
		if (pieces[col][row] != null
				&& pieces[col][row].getColor() == this.color) {
			return false;
		}
		// row +1 col +2
		if (this.row + 1 == row && this.col + 2 == col) {

			return true;
		}
		// row +1 col -2
		if (this.row + 1 == row && this.col - 2 == col) {

			return true;
		}
		// row -1 col +2
		if (this.row - 1 == row && this.col + 2 == col) {

			return true;
		}
		// row -1 col -2
		if (this.row - 1 == row && this.col - 2 == col) {

			return true;
		}
		// row +2 col +1
		if (this.row + 2 == row && this.col + 1 == col) {

			return true;
		}
		// row +2 col -1
		if (this.row + 2 == row && this.col - 1 == col) {

			return true;
		}
		// row -2 col +1
		if (this.row - 2 == row && this.col + 1 == col) {
			return true;
		}
		// row -2 col -1
		if (this.row - 2 == row && this.col - 1 == col) {

			return true;
		}
		return false;
	}

}
