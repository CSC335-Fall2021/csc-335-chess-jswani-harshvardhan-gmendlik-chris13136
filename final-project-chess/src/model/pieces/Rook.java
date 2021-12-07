package model.pieces;

public class Rook extends ChessPiece {

	public Rook(int row, int col, int color) {
		super(row, col, color);
	}

	@Override
	public boolean isValidMove(int row, int col, ChessPiece[][] pieces) {
		if(pieces[col][row].getColor() == this.color) {
			return false;
		}
		//diagonal check 
		if(this.row != row && this.col != col) {
			return false;
		}
		//left right check
		if(this.row == row) {
			//right check
			for (int i = this.col; i < col; i++) {
				if (pieces[i][row] != null) {
					return false;
				}
			}
			//left check
			for (int i = this.col; i > col; i--) {
				if (pieces[i][row] != null) {
					return false;
				}
			}
			return true;
		}
		
		//up down check
		if(this.col == col) {
			//up check
			for (int i = this.row; i < row; i++) {
				if (pieces[col][i] != null) {
					return false;
				}
			}
			//down check
			for (int i = this.row; i > row; i--) {
				if (pieces[col][i] != null) {
					return false;
				}
			}
			return true;
		}
		
		return false;
	}

}
