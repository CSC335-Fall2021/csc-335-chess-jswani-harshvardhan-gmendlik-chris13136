package model.pieces;

public class Pawn extends ChessPiece {

	//TODO: For pawns we need to make a note if the piece moved 2 paces in the first move to check if an en-passant can happen. I think that a boolean would be best.
	public Pawn(int row, int col, int color) {
		super(row, col, color);
	}

	@Override
	boolean isValidMove(int row, int col, ChessPiece[][] pieces) {
		// up one check
		int offset = 1;
		if (this.color == 1) {
			offset = -1;
		}
		if (this.row + offset == row) {
			//if the square is empty then we all good
			if(pieces[col][row] == null) {
				setPosition(row, col);
				return true;
			}
			//else it has a piece so false
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
			if (pieces[col][row + offset] == null && pieces[col][row + offsetp1] == null) {
				setPosition(row, col);
				return true;
			}
			// else one of them was a piece so false
			return false;
		}

		// diagonal +1 check
		if (col + 1 < 8 && this.row + offset == row && 
				this.col + 1 == col && !(pieces[col][row] == null)) {
			setPosition(row, col);
			return true;
			
		}
		// diagonal -1 check
		if (col - 1 > 0 && this.row + offset == row && 
				this.col - 1 == col && !(pieces[col][row] == null)) {
			setPosition(row, col);
			return true;
			
		}
		// all 4 things a pawn can do failed so everything else is false
		return false;
	}

}
