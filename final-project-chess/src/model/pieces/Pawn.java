package model.pieces;

public class Pawn extends ChessPiece {

	public Pawn(int row, int col, int color) {
		super(row, col, color);
	}

	@Override
	boolean isValidMove(int row, int col, ChessPiece[][] pieces) {
		// TODO Auto-generated method stub
		return false;
	}

}
