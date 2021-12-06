package model;

import java.util.Observable;

import model.pieces.*;

public class ChessModel extends Observable {

	private ChessPiece[][] pieces; //ChessPiece[col][row]
	private int turn; //represents the player that plays next 0 for white 1 for black.


	/**
	 * Constructor method for the model. Builds an 8*8 array containing chessPiece objects.
	 */
	public ChessModel(){
		pieces = new ChessPiece[8][8];
		turn = 0;
	}

	/**
	 * Builds the board for a new game.
	 */
	private void buildBoard(){
		for (int row=2; row<=5; row++){
			for (int col=0; col<=7; col++){
				pieces[col][row] = null;
			}
		}
		addBackline(0);
		addBackline(1);

	}

	/**
	 *
	 * @param color 0 for white, 1 for black.
	 */
	private void addBackline(int color){
		int row = 0;
		if (color==1){
			row = 7;
		}

		// Adds rooks
		int col = 0;
		pieces[col][row] = new Rook(row, col, color);
		col = 7;
		pieces [col][row] = new Rook(row, col, color);

		//Adds knights
		col = 1;
		pieces[col][row] = new Knight(row, col, color);
		col = 6;
		pieces [col][row] = new Knight(row, col, color);

		//Adds bishops
		col = 2;
		pieces[col][row] = new Bishop(row, col, color);
		col = 5;
		pieces [col][row] = new Bishop(row, col, color);

		col = 3;
		pieces[col][row] = new Queen(row, col, color);

		col = 4;
		pieces[col][row] = new King(row, col, color);
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
