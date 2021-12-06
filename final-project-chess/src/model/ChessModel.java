package model;

import java.util.Observable;

import model.pieces.ChessPiece;

public class ChessModel extends Observable {

	private ChessPiece[][] pieces;


	/**
	 *
	 */
	public ChessModel(){
		pieces = new ChessPiece[8][8];

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
