
/**
 * @filename ChessModel.java
 * @author Garrison Mendlik 12/8/2021
 * @author Jasnam Swani
 * @author Harshvardhan Bhatnagar
 * @author Chris Brinkley
 * @purpose Models a game of chess. Can create new games, as well as saving and
 * loading games.
 */

package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.pieces.Bishop;
import model.pieces.ChessPiece;
import model.pieces.King;
import model.pieces.Knight;
import model.pieces.Pawn;
import model.pieces.Queen;
import model.pieces.Rook;

public class ChessModel {

	public static final int WHITE = 0;
	public static final int BLACK = 1;

	private ChessPiece[][] pieces; // ChessPiece[col][row]
	private int turn; // next player to move -- 0=white 1=black
	private ArrayList<ChessPiece> white; // arr of white pieces
	private ArrayList<ChessPiece> black; // arr of black pieces

	/**
	 * Constructor method for the model. Builds an 8*8 array containing
	 * chessPiece objects.
	 */
	public ChessModel() {
		pieces = new ChessPiece[8][8];
		white = new ArrayList<ChessPiece>();
		black = new ArrayList<ChessPiece>();
		buildBoard();
		turn = WHITE;
	}

	/**
	 * Builds a Chess model from a given file.
	 * 
	 * @param toLoad File to load game from.
	 * @throws FileNotFoundException
	 * @throws NullPointerException
	 */
	public ChessModel(File toLoad)
			throws FileNotFoundException, NullPointerException {
		try {
			loadGame(toLoad);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Builds the board for a new game.
	 */
	private void buildBoard() {
		for (int row = 2; row <= 5; row++) {
			for (int col = 0; col <= 7; col++) {
				pieces[col][row] = null;
			}
		}
		addBackline(WHITE);
		addBackline(BLACK);
		int row = 1;
		int color = 0;
		for (int col = 0; col <= 7; col++) {
			pieces[col][row] = new Pawn(row, col, color);
		}

		row = 6;
		color = 1;
		for (int col = 0; col <= 7; col++) {
			pieces[col][row] = new Pawn(row, col, color);
		}
	}

	/**
	 * Adds back-line pieces (pieces other than pawns) for the color denoted by
	 * the parameter.
	 * 
	 * @param color 0 for white, 1 for black.
	 */
	private void addBackline(int color) {
		int row = 0;
		ArrayList<ChessPiece> currarray = white;
		if (color == 1) {
			row = 7;
			currarray = black;
		}

		// Adds rooks
		int col = 0;
		pieces[col][row] = new Rook(row, col, color);
		currarray.add(pieces[col][row]);
		col = 7;
		pieces[col][row] = new Rook(row, col, color);
		currarray.add(pieces[col][row]);

		// Adds knights
		col = 1;
		pieces[col][row] = new Knight(row, col, color);
		currarray.add(pieces[col][row]);
		col = 6;
		pieces[col][row] = new Knight(row, col, color);
		currarray.add(pieces[col][row]);

		// Adds bishops
		col = 2;
		pieces[col][row] = new Bishop(row, col, color);
		currarray.add(pieces[col][row]);
		col = 5;
		pieces[col][row] = new Bishop(row, col, color);
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
	 * 
	 * @return ChessPiece[][] representing the board.
	 */
	public ChessPiece[][] getBoard() {
		return pieces;
	}

	/**
	 * @return returns the list of white pieces
	 */
	public ArrayList<ChessPiece> getWhite() {
		return white;
	}

	/**
	 * @return returns the list of black pieces
	 */
	public ArrayList<ChessPiece> getBlack() {
		return black;
	}

	/**
	 * Returns the int representing the player that plays next.
	 * 
	 * @return 0 if white plays next, 1 otherwise.
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Sets the turn to the other color.
	 */
	public void setTurn() {
		if (turn == WHITE) {
			turn = BLACK;
			return;
		}
		turn = WHITE;
	}

	/**
	 * Removes a piece from play
	 * 
	 * @param pieces
	 */
	public void removePiece(int row, int col, ChessPiece[][] pieces) {
		// remove references for garbage collection
		pieces[col][row] = null;
	}

	/**
	 * Prints the model's current chess board to the console. White pieces
	 * printed in upper case, black pieces printed in lower case.
	 */
	public String printBoard() {
		String out = "";
		System.out.println("  a b c d e f g ");
		for (int i = 0; i < 8; i++) {
			System.out.print(Integer.toString(i) + " ");
			for (int j = 0; j < 8; j++) {
				ChessPiece currPiece = this.pieces[j][i];
				if (currPiece instanceof Knight) {
					if (currPiece.getColor() == 0) {
						System.out.print("H "); // h for horse cause k is taken
						out += 'H';
					} else {
						System.out.print("h ");
						out += 'h';
					}
				} else if (currPiece instanceof Pawn) {
					if (currPiece.getColor() == 0) {
						System.out.print("P ");
						out += 'P';
					} else {
						System.out.print("p ");
						out += 'p';
					}
				} else if (currPiece instanceof Queen) {
					if (currPiece.getColor() == 0) {
						System.out.print("Q ");
						out += 'Q';
					} else {
						System.out.print("q ");
						out += 'q';
					}
				} else if (currPiece instanceof King) {
					if (currPiece.getColor() == 0) {
						System.out.print("K ");
						out += 'K';
					} else {
						System.out.print("k ");
						out += 'k';
					}
				} else if (currPiece instanceof Rook) {
					if (currPiece.getColor() == 0) {
						System.out.print("R ");
						out += 'R';
					} else {
						System.out.print("r ");
						out += 'r';
					}
				} else if (currPiece instanceof Bishop) {
					if (currPiece.getColor() == 0) {
						System.out.print("B ");
						out += 'B';
					} else {
						System.out.print("b ");
						out += 'b';
					}
				} else {
					System.out.print(". ");
					out += '.';
				}
			}
			System.out.println();
			out += '\n';
		}
		System.out.println("Turn: " + Integer.toString(this.turn));
		out += Integer.toString(this.turn);
		return out;
	}

	/**
	 * Prints the given chess board to the console. White pieces printed in
	 * upper case, black pieces printed in lower case.
	 * 
	 * @param pieces ChessPiece[][] chess board to be printed
	 */
	public static void printBoard(ChessPiece[][] pieces, final int turn) {
		System.out.println("  a b c d e f g h ");
		for (int i = 0; i < 8; i++) {
			System.out.print(Integer.toString(i) + " ");
			for (int j = 0; j < 8; j++) {
				ChessPiece currPiece = pieces[j][i];
				if (currPiece instanceof Knight) {
					if (currPiece.getColor() == 0) {
						System.out.print("H "); // h for horse cause k is taken
					} else {
						System.out.print("h ");
					}
				} else if (currPiece instanceof Pawn) {
					if (currPiece.getColor() == 0) {
						System.out.print("P ");
					} else {
						System.out.print("p ");
					}
				} else if (currPiece instanceof Queen) {
					if (currPiece.getColor() == 0) {
						System.out.print("Q ");
					} else {
						System.out.print("q ");
					}
				} else if (currPiece instanceof King) {
					if (currPiece.getColor() == 0) {
						System.out.print("K ");
					} else {
						System.out.print("k ");
					}
				} else if (currPiece instanceof Rook) {
					if (currPiece.getColor() == 0) {
						System.out.print("R ");
					} else {
						System.out.print("r ");
					}
				} else if (currPiece instanceof Bishop) {
					if (currPiece.getColor() == 0) {
						System.out.print("B ");
					} else {
						System.out.print("b ");
					}
				} else {
					System.out.print(". ");
				}
			}
			System.out.println();
		}
		System.out.println("Turn: " + Integer.toString(turn));
	}

	/**
	 * Loads a game from the given file.
	 * 
	 * @param toLoad File to load game from
	 * @throws FileNotFoundException
	 * @throws NullPointerException
	 */
	public void loadGame(File toLoad)
			throws FileNotFoundException, NullPointerException {
		Scanner s = null;
		try {
			s = new Scanner(toLoad);
		} catch (Exception e) {
			throw e;
		}
		this.pieces = new ChessPiece[8][8];
		this.white = new ArrayList<ChessPiece>();
		this.black = new ArrayList<ChessPiece>();

		int currRow = 7;
		while (s.hasNextLine()) {
			String currLine = s.nextLine();
			if (currRow == -1) {
				this.turn = Integer
						.valueOf(Character.toString(currLine.charAt(0)));
				break;
			}

			for (int currCol = 0; currCol < currLine.length(); currCol++) {
				char currPiece = currLine.charAt(currCol);
				if (currPiece == '.') {
					this.pieces[currCol][currRow] = null;
					continue;
				}

				// Upper case is white, lower case is black
				if (currPiece == 'B') {
					this.pieces[currCol][currRow] = new Bishop(currRow,
							currCol, WHITE);
				} else if (currPiece == 'b') {
					this.pieces[currCol][currRow] = new Bishop(currRow,
							currCol, BLACK);
				} else if (currPiece == 'K') {
					this.pieces[currCol][currRow] = new King(currRow, currCol,
							WHITE);
				} else if (currPiece == 'k') {
					this.pieces[currCol][currRow] = new King(currRow, currCol,
							BLACK);
				} else if (currPiece == 'H') {
					this.pieces[currCol][currRow] = new Knight(currRow,
							currCol, WHITE);
				} else if (currPiece == 'h') { // h for horse cause k is taken
					this.pieces[currCol][currRow] = new Knight(currRow,
							currCol, BLACK);
				} else if (currPiece == 'P') {
					this.pieces[currCol][currRow] = new Pawn(currRow, currCol,
							WHITE);
				} else if (currPiece == 'p') {
					this.pieces[currCol][currRow] = new Pawn(currRow, currCol,
							BLACK);
				} else if (currPiece == 'Q') {
					this.pieces[currCol][currRow] = new Queen(currRow, currCol,
							WHITE);
				} else if (currPiece == 'q') {
					this.pieces[currCol][currRow] = new Queen(currRow, currCol,
							BLACK);
				} else if (currPiece == 'R') {
					this.pieces[currCol][currRow] = new Rook(currRow, currCol,
							WHITE);
				} else if (currPiece == 'r') {
					this.pieces[currCol][currRow] = new Rook(currRow, currCol,
							BLACK);
				}
			}
			currRow--;
		}
		s.close();
	}

	/**
	 * Writes the current state of the board to the given file. Will overwrite
	 * a file if it already exists.
	 * 
	 * @param toSave File to save to game to
	 * @throws IOException
	 */
	public void writeGame(File toSave) throws IOException {
		FileWriter fw = null;
		try {
			if (toSave.createNewFile()) {
				fw = new FileWriter(toSave);
				fw.write(this.boardToText());
				System.out.println("Successfully saved game to "
						+ toSave.getName() + "!");
			} else {
				fw = new FileWriter(toSave);
				fw.write(this.boardToText());
				System.out.println(
						"Overwrote game at " + toSave.getName() + ".");
			}
			fw.close();
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * Returns a string encryption of the current state of the board. The
	 * encryption is used for save files.
	 * 
	 * @return String encryption of board's state
	 */
	private String boardToText() {
		String out = "";
		for (int i = 7; i >= 0; i--) { // row
			for (int j = 0; j < 8; j++) { // col
				ChessPiece currPiece = pieces[j][i];
				if (currPiece instanceof Knight) {
					if (currPiece.getColor() == 0) {
						out += 'H'; // h for horse cause k is taken
					} else {
						out += 'h';
					}
				} else if (currPiece instanceof Pawn) {
					if (currPiece.getColor() == 0) {
						out += 'P';
					} else {
						out += 'p';
					}
				} else if (currPiece instanceof Queen) {
					if (currPiece.getColor() == 0) {
						out += 'Q';
					} else {
						out += 'q';
					}
				} else if (currPiece instanceof King) {
					if (currPiece.getColor() == 0) {
						out += 'K';
					} else {
						out += 'k';
					}
				} else if (currPiece instanceof Rook) {
					if (currPiece.getColor() == 0) {
						out += 'R';
					} else {
						out += 'r';
					}
				} else if (currPiece instanceof Bishop) {
					if (currPiece.getColor() == 0) {
						out += 'B';
					} else {
						out += 'b';
					}
				} else {
					out += '.';
				}
			}
			out += '\n';
		}
		out += Integer.toString(this.turn);
		return out;
	}
}