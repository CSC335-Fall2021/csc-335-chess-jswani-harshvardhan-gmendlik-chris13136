package model.pieces;

public class Bishop extends ChessPiece {

	public Bishop(int row, int col, int color) {
		super(row, col, color);
		name = "Biship";
	}

	@Override
	public boolean isValidMove(int row, int col, ChessPiece[][] pieces) {
		if(pieces[col][row] != null) {
			if( pieces[col][row].getColor() == this.color) {
				return false;
			}
		}

		// cant move like this
		if (this.row == row || this.col == col) {
			return false;
		}
		// default to +,+
		int colOff = 1;
		int rowOff = 1;

		if (Integer.signum(this.row - row) == 1) {
			rowOff = -1;
		}

		if (Integer.signum(this.col - col) == 1) {
			colOff = -1;
		}
		int curCol = this.col+colOff;
		int curRow = this.row+rowOff;

		while (curCol != col) {
			if (pieces[curCol][curRow] != null) {	
				return false;
			}
			curCol += colOff;
			curRow += rowOff;
		}
		if (pieces[col][row] != null && pieces[curCol][curRow].color != this.color) {
			return true;// Enemy piece
		}
		return true;
	}

}
