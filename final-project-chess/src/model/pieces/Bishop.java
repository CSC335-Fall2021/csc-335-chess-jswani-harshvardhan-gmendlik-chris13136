package model.pieces;

public class Bishop extends ChessPiece {

	public Bishop(int row, int col, int color) {
		super(row, col, color);
	}

	@Override
	public boolean isValidMove(int row, int col, ChessPiece[][] pieces) {
		if(pieces[col][row].getColor() == this.color) {
			return false;
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
		int curCol = this.col;
		int curRow = this.row;

		while (curCol != col) {
			if (pieces[curCol][curRow] != null) {
				System.out.println("This: " + this.toString());
				System.out.println(
						"Other: " + pieces[curCol][curRow].toString());
				if (pieces[curCol][curRow].color != this.color) // Enemy piece
					return true;
				return false;
			}
			curCol += colOff;
			curRow += rowOff;
		}
<<<<<<< HEAD

		// setPosition(row, col);
=======
>>>>>>> branch 'Testing' of https://github.com/CSC335-Fall2021/csc-335-chess-jswani-harshvardhan-gmendlik-chris13136
		return true;
	}

}
