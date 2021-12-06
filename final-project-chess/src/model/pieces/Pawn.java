package model.pieces;

public class Pawn extends ChessPiece {

	//TODO: For pawns we need to make a note if the piece moved 2 paces in the first move to check if an en-passant can happen. I think that a boolean would be best.
	public Pawn(int row, int col, int color) {
		super(row, col, color);
	}

	@Override
	boolean isValidMove(int row, int col, ChessPiece[][] pieces) {
		// TODO Auto-generated method stub
		return false;
	}

}
