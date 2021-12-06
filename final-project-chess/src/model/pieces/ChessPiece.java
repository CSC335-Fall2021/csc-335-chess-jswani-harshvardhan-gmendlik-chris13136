package model.pieces;

public abstract class ChessPiece {

	private int row;
	private int col;
	private final int color; //TODO: Color needs to be to determine valid move. especially for pawns.

	public ChessPiece(int row, int col, int color) {
		this.setPosition(row, col);
		this.color = color;
	}

	/**
	 * Returns true if the move is valid.
	 * 
	 * @param row    Row of potential move
	 * @param col    Column of potential move
	 * @param pieces The board of pieces
	 * @return True if the move is valid.
	 */
	abstract boolean isValidMove(int row, int col, ChessPiece[][] pieces);

	/**
	 * Sets the position of the piece
	 * 
	 * @param row Row where the chess piece is located
	 * @param col Column where the chess piece is located
	 */
	void setPosition(int row, int col) {
		this.row = row;
		this.col = col;
	}
}
