package model;

import java.util.Observable;

import model.pieces.ChessPiece;

public class ChessModel extends Observable {

	private ChessPiece[][] pieces;
	private int turn; //represents the player that plays next 0 for white 1 for black.


	/**
	 * Constructor method for the model. Builds an 8*8 array containing chessPiece objects.
	 */
	public ChessModel(){
		pieces = new ChessPiece[8][8];
		turn = 0;
	}

	/**
	 * Getter for the array representing the chess board.
	 * @return ChessPiece[][] representing the board.
	 */
	public ChessPiece[][] getBoard(){
		return pieces;
	}


	/**
	 * Returns the int representing the player that plays next.
	 * @return 0 if white plays next, 1 otherwise.
	 */
	public int getTurn(){
		return turn;
	}

	public void setTurn(){
		if (turn==0){
			turn = 1;
			return;
		}
		turn = 0;
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
