package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.pieces.*;

public class ChessModel extends Observable {

	private ChessPiece[][] pieces; //ChessPiece[col][row]
	private int turn; //represents the player that plays next 0 for white 1 for black.
	private ArrayList<ChessPiece> white; // arr of white peaces
	private ArrayList<ChessPiece> black; // arr of black peaces


	/**
	 * Constructor method for the model. Builds an 8*8 array containing chessPiece objects.
	 */
	public ChessModel(){
		pieces = new ChessPiece[8][8];
		white  = new ArrayList<ChessPiece>();
		black  = new ArrayList<ChessPiece>();
		buildBoard();
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
		int row = 1;
		int color = 0;
		for (int col=0; col<=7; col++){
			pieces[col][row]= new Pawn(row,col,color);
		}

		row = 6;
		color = 1;
		for (int col=0; col<=7; col++){
			pieces[col][row]= new Pawn(row,col,color);
		}
	}

	/**
	 *Adds back-line pieces (pieces other than pawns) for the color denoted by the parameter.
	 * @param color 0 for white, 1 for black.
	 */
	private void addBackline(int color){
		int row = 0;
		ArrayList<ChessPiece> currarray = white;
		if (color==1){
			row = 7;
			currarray = black;
		}

		// Adds rooks
		int col = 0;
		pieces[col][row] = new Rook(row, col, color);
		currarray.add(pieces[col][row]);
		col = 7;
		pieces [col][row] = new Rook(row, col, color);
		currarray.add(pieces[col][row]);

		//Adds knights
		col = 1;
		pieces[col][row] = new Knight(row, col, color);
		currarray.add(pieces[col][row]);
		col = 6;
		pieces [col][row] = new Knight(row, col, color);
		currarray.add(pieces[col][row]);

		//Adds bishops
		col = 2;
		pieces[col][row] = new Bishop(row, col, color);
		currarray.add(pieces[col][row]);
		col = 5;
		pieces [col][row] = new Bishop(row, col, color);
		currarray.add(pieces[col][row]);

		col = 3;
		pieces[col][row] = new Queen(row, col, color);
		currarray.add(pieces[col][row]);

		col = 4;
		pieces[col][row] = new King(row, col, color);
		currarray.add(pieces[col][row]);
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
