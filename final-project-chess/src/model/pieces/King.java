package model.pieces;

public class King extends ChessPiece {

	public King(int row, int col, int color) {
		super(row, col, color);
	}

	@Override
	public boolean isValidMove(int row, int col, ChessPiece[][] pieces) {
		if( this.row == row && this.col == col) {
			return false;
		}
		if ((this.row + 1 == row || this.row - 1 == row || this.row == row) && 
				(this.col + 1 == col || this.row - 1 == col || this.row == col)) {
			setPosition(row, col);
			return true;
		}
		return false;
	}

}
