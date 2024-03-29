
/**
 * @filename Pawn.java
 * @author Garrison Mendlik 12/8/2021
 * @author Jasnam Swani
 * @author Harshvardhan Bhatnagar
 * @author Chris Brinkley
 * @purpose Pawn chess piece implementation.
 */

package model.pieces;

public class Pawn extends ChessPiece {

	// TODO: For pawns we need to make a note if the piece moved 2 paces in the
	// first move to check if an en-passant can happen. I think that a boolean
	// would be best.
	/**
	 * Creates a new pawn piece.
	 * 
	 * @param row   Row to place the pawn at
	 * @param col   Column to place the pawn at
	 * @param color Team color of the pawn
	 */
	public Pawn(int row, int col, int color) {
		super(row, col, color);
		name = "Pawn";
	}

	@Override
	/**
	 * Checks if the move is valid for the pawn piece
	 * 
	 * @param row    Row to attempt to move to
	 * @param col    Column to attempt to move to
	 * @param pieces ChessPiece[][] array containing the board's pieces
	 * @return Returns true if the pawn can make the move
	 */
	public boolean isValidMove(int row, int col, ChessPiece[][] pieces) {
		System.out.println("pawn moving to (" + col + "," + row + ")");
		if (pieces[col][row] != null
				&& pieces[col][row].getColor() == this.color) {
			return false;
		}
		// up one check
		int offset = 1;
		if (this.color == 1) {
			offset = -1;
		}
		if (this.row + offset == row) {
			// if the square is empty then we all good
			if (pieces[col][row] == null && this.col == col) {
				return true;
			} else if (pieces[col][row] != null
					&& pieces[col][row].color != this.color) {
				// diagonal take check
				return true;
			}
			// else it has a friendly piece so false
			return false;
		}

		// up two check
		int offsetp1 = 2;
		int startrow = 1;
		if (this.color == 1) {
			offsetp1 = -2;
			startrow = 6;
		}
		if (this.row + offsetp1 == row && this.row == startrow) {
			// if both squares are null
			if (pieces[col][row + offset] == null
					&& pieces[col][row + offsetp1] == null) {

				return true;
			}
			// else one of them was a piece so false
			return false;
		}

		// all 4 things a pawn can do failed so everything else is false
		return false;
	}

}
