package model;

import java.util.Observable;

import model.pieces.ChessPiece;

public class ChessModel extends Observable {

	private ChessPiece[][] pieces;


	/**
	 * Constructor method for the model. Builds an 8*8 array containing chessPiece objects.
	 */
	public ChessModel(){
		pieces = new ChessPiece[8][8];
	}

	/**
	 * Getter for the array representing the chess board.
	 * @return ChessPiece[][] representing the board.
	 */
	public ChessPiece[][] getBoard(){
		return pieces;
	}

	/**
	 * Removes a piece from play
	 * 
	 * @param pieces
	 */

	void removePiece(int row, int col, ChessPiece[][] pieces) {
		// remove references for garbage collection
		pieces[col][row] = null;
	}

}
