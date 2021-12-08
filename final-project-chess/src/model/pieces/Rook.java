
/**
 * @filename Rook.java
 * @author Garrison Mendlik 12/8/2021
 * TODO: Add your names
 * @purpose Rook chess piece implementation.
 */

package model.pieces;

public class Rook extends ChessPiece {

	/**
	 * Creates a new rook piece.
	 * 
	 * @param row   Row to place the rook at
	 * @param col   Column to place the rook at
	 * @param color Team color of the rook
	 */
	public Rook(int row, int col, int color) {
		super(row, col, color);
		name = "Rook";
	}

	@Override
	/**
	 * Checks if the move is valid for the rook piece
	 * 
	 * @param row    Row to attempt to move to
	 * @param col    Column to attempt to move to
	 * @param pieces ChessPiece[][] array containing the board's pieces
	 * @return Returns true if the rook can make the move
	 */
	public boolean isValidMove(int row, int col, ChessPiece[][] pieces) {
		if (pieces[col][row] != null
				&& pieces[col][row].getColor() == this.color) {
			return false;
		}
		// diagonal check
		if (this.row != row && this.col != col) {
			return false;
		}
		//left right check
		if(this.row == row) {
			//right check
			for (int i = this.col + 1; i < col; i++) {
				if (pieces[i][row] != null) {
					return false;
				}
			}
			//left check
			for (int i = this.col - 1; i > col; i--) {
				if (pieces[i][row] != null) {
					return false;
				}
			}
			return true;
		}
		
		//up down check
		if(this.col == col) {
			//up check
			for (int i = this.row + 1; i < row; i++) {
				if (pieces[col][i] != null) {
					return false;
				}
			}
			//down check
			for (int i = this.row - 1; i > row; i--) {
				if (pieces[col][i] != null) {
					return false;
				}
			}
			return true;
		}

		return false;
	}

}
