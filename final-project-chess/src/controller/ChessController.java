
/**
 * @filename ChessController.java
 * @author Garrison Mendlik 12/8/2021
 * TODO: Add your names
 * @purpose Controls the game state by asking things of the ChessModel.
 */

package controller;

import java.util.ArrayList;

import model.ChessModel;
import model.pieces.Bishop;
import model.pieces.ChessPiece;
import model.pieces.King;
import model.pieces.Knight;
import model.pieces.Pawn;
import model.pieces.Queen;
import model.pieces.Rook;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Platform;

public class ChessController {

	private ChessModel model;
	private ChessPiece[][] board;
	
	private Socket connection;
	private boolean isServer = false;
	private boolean isConnected = false;
	
	ObjectOutputStream oos;
	ObjectInputStream ois;

	/**
	 * Create controller with given model.
	 * 
	 * @param model ChessModel to link controller to.
	 */
	public ChessController(ChessModel model) {
		// TODO: I have created this constructor just to begin working on the
		// view. Feel free to modify as needed.
		this.model = model;
		board = model.getBoard(); 
	}
	
	public void startServer() {
		try {
			ServerSocket server = new ServerSocket(4000);

			connection = server.accept();
			
			oos = new ObjectOutputStream(connection.getOutputStream());
			ois = new ObjectInputStream(connection.getInputStream());
			
			isServer = true;
			isConnected = true;
			model.setTurn();
		}
		catch(IOException e) {
			System.err.println("Something went wrong with the network! " + e.getMessage());
		}
	}
	
	public void startClient() {
		try {
			connection = new Socket("localhost", 4000);
			
			isServer = false;
			isConnected = true;
			model.setTurn();
			oos = new ObjectOutputStream(connection.getOutputStream());
			ois = new ObjectInputStream(connection.getInputStream());


			Thread t = new Thread(() -> {
				try {
					CheckMessage otherMsg = (CheckMessage)ois.readObject();
					
					Platform.runLater(() -> {
						model.setTurn();
						//model.addCircle(otherMsg.getX(), otherMsg.getY(), otherMsg.getColor()); FIX THIS	 
	
					});

				}
				catch(IOException | ClassNotFoundException e) {
					System.err.println("Something went wrong with serialization: " + e.getMessage());
				}
			});
			t.start();

		}
		catch(IOException e) {
			System.err.println("Something went wrong with the network! " + e.getMessage());
		}
	}
	
	public void makeCheckMove(int x, int y) {
		//model.addCircle(x, y, (isServer) ? CheckMessage.WHITE : CheckMessage.BLACK); FIX THIS
		
		CheckMessage msg = new CheckMessage(x, y, (isServer) ? CheckMessage.WHITE : CheckMessage.BLACK);
		sendMessage(msg);
	}
	
	private void sendMessage(CheckMessage msg) {
		if(!isConnected) { return; }
		
		Thread t = new Thread(() -> {
			try {
				oos.writeObject(msg);
				oos.flush();
				
				CheckMessage otherMsg = (CheckMessage)ois.readObject();
				
				Platform.runLater(() -> {
					model.setTurn();
					//model.addCircle(otherMsg.getX(), otherMsg.getY(), otherMsg.getColor()); FIX THIS
				});
			}
			catch(IOException | ClassNotFoundException e) {
				System.err.println("Something went wrong with serialization: " + e.getMessage());
			}
			
		});
		t.start();
		
	}

	/**
	 * Returns the board of pieces.
	 * 
	 * @return The board of pieces
	 */
	public ChessPiece[][] getBoard() {
		return board;
	}

	/**
	 * Attempts to make a move on the board.
	 * 
	 * @param curCol Column of piece to move
	 * @param curRow Row of piece to move
	 * @param newCol Column of place to move to
	 * @param newRow Row of place to move to
	 * @return Returns true if the move was possible and executed
	 */
	public boolean makeMove(int curCol, int curRow, int newCol, int newRow) {
		if (board[curCol][curRow] != null) {
			ChessPiece cur = board[curCol][curRow];
			System.out.println(model.getBoard()[curCol][curRow]);
			if (cur instanceof King) {
				// no moving into check rule
				if (!canBeAttacked(newCol, newRow)) {
					return false;
				}
			}
			if (cur.isValidMove(newRow, newCol, board)) {
				board[curCol][curRow]=null;
				board[newCol][newRow]=cur;
				cur.setPosition(newRow, newCol);
				return true;
			}

		}
		return false;
	}

	/**
	 * This method returns which player gets to play in the current turn.
	 * 
	 * @return 0 for white, 1 for black.
	 */
	public int getTurn() {
		return model.getTurn();
	}

	public void setTurn(){
		model.setTurn();
	}

	/**
	 * Returns if the piece can be attacked.
	 * 
	 * @param newCol Column of piece to check
	 * @param newRow Row of piece to check
	 * @return Returns true if the piece can be attacked
	 */
	private boolean canBeAttacked(int newCol, int newRow) {
		// for each piece in opp list isVaildMove on newCol,newRow
		ArrayList<ChessPiece> oppList = model.getBlack();
		if (model.getTurn() == 1) {
			oppList = model.getWhite();
		}
		for (ChessPiece p : oppList) {
			if (p.isValidMove(newRow, newCol, board)) {
				return true;
			}
		}
		return false;
	}

	// TODO: implement a method that when moving the king makes sure it doesnt
	// move into check.
	/**
	 * Ways to escape check 1. move king (aka check the 8 squares for is vaild
	 * move) 2. attack attacker (aka canBeAttacked on attacker square 3. block
	 * (aka canBeAttacked or isValid move for each piece in opposite colors
	 * list on each of the in between squares)
	 * 
	 * @return Returns true if the game is over
	 */
	public boolean isGameOver() {
		// 1. move king (aka check the 8 squares for is vaild move)
		// find correct king
		ArrayList<ChessPiece> pieceList = model.getBlack();
		ArrayList<ChessPiece> oppList = model.getWhite();
		if (model.getTurn() == 1) {
			pieceList = model.getWhite();
			oppList = model.getBlack();
		}

		ChessPiece king = pieceList.get(0);
		for (ChessPiece p : pieceList) {
			if (p instanceof King) {
				king = p;
				break;
			}
		}
		int curRow = king.getRow();
		int curCol = king.getCol();
		if ((
		// left up
		!king.isValidMove(curCol - 1, curRow - 1, board) &&
		// up
				!king.isValidMove(curCol - 0, curRow - 1, board) &&
				// right up
				!king.isValidMove(curCol + 1, curRow - 1, board) &&
				// left
				!king.isValidMove(curCol - 1, curRow + 0, board) &&
				// right
				!king.isValidMove(curCol + 1, curRow + 0, board) &&
				// left down
				!king.isValidMove(curCol - 1, curRow + 1, board) &&
				// down
				!king.isValidMove(curCol + 0, curRow + 1, board) &&
				// right down
				!king.isValidMove(curCol + 1, curRow + 1, board))) {
			// 2. attack attacker (aka canBeAttacked on attacker square)

			// find attacker/s
			ArrayList<ChessPiece> attackersList = new ArrayList<ChessPiece>();
			ArrayList<ChessPiece> needblockList = new ArrayList<ChessPiece>();
			ArrayList<ChessPiece> blockedList = new ArrayList<ChessPiece>();
			class Touple {
				public int col;
				public int row;

				public Touple(int col, int row) {
					this.col = col;
					this.row = row;
				}
			}
			for (ChessPiece p : pieceList) {
				if (p.isValidMove(curRow, curCol, board)) {
					attackersList.add(p);
				}
			}

			// take out attackers
			for (ChessPiece p : attackersList) {
				curRow = p.getRow();
				curCol = p.getCol();
				boolean needBlock = true;
				for (ChessPiece x : pieceList) {
					if (x.isValidMove(curRow, curCol, board)) {
						needBlock = false;
					}
				}
				if (needBlock) {
					needblockList.add(p);
				}
			}
			if (needblockList.size() != 0) {
				// 3. block (aka canBeAttacked or isValid move for each piece
				// in opposite colors list on each of the in between squares)

				for (ChessPiece b : needblockList) {
					if (b instanceof Pawn) {
						// cant move, cant take, pawn cant be blocked
						return true;
					}
					if (b instanceof Knight) {
						// cant move, cant take, Knight cant be blocked
						return true;
					}
					if (b instanceof Bishop) {
						ArrayList<Touple> oneNeedBlock = new ArrayList<Touple>();
						int bRow = b.getRow();
						int bCol = b.getCol();

						int rowIdd = 1;
						int colIdd = 1;

						if (Integer.signum(bRow - king.getRow()) == 1) {
							rowIdd = -1;
						}

						if (Integer.signum(bCol - king.getCol()) == 1) {
							colIdd = -1;
						}

						while (bCol != king.getCol()) {
							Touple cord = new Touple(bCol, bRow);
							oneNeedBlock.add(cord);
							bRow += rowIdd;
							bCol += colIdd;
						}
						for (Touple c : oneNeedBlock) {
							for (ChessPiece f : pieceList) {
								if (f.isValidMove(c.col, c.row, board)) {
									blockedList.add(b);
									break;
								}
							}
						}

					}
					if (b instanceof Rook) {
						ArrayList<Touple> oneNeedBlock = new ArrayList<Touple>();
						int idd = 1;
						if (b.getRow() == king.getRow()) {
							if (b.getRow() > king.getRow()) {
								idd = -1;
							}
							for (int i = b.getRow(); i != king
									.getRow(); i += idd) {
								Touple cord = new Touple(b.getCol(), i);
								oneNeedBlock.add(cord);
							}
						}

						if (b.getCol() == king.getCol()) {
							if (b.getCol() > king.getCol()) {
								idd = -1;
							}
							for (int i = b.getCol(); i != king
									.getCol(); i += idd) {
								Touple cord = new Touple(i, b.getRow());
								oneNeedBlock.add(cord);
							}
						}
						for (Touple c : oneNeedBlock) {
							for (ChessPiece f : pieceList) {
								if (f.isValidMove(c.col, c.row, board)) {
									blockedList.add(b);
									break;
								}
							}
						}
					}
					if (b instanceof Queen) {
						ArrayList<Touple> oneNeedBlock = new ArrayList<Touple>();
						int idd = 1;
						if (b.getRow() == king.getRow()) {
							if (b.getRow() > king.getRow()) {
								idd = -1;
							}
							for (int i = b.getRow(); i != king
									.getRow(); i += idd) {
								Touple cord = new Touple(b.getCol(), i);
								oneNeedBlock.add(cord);
							}
						}

						else if (b.getCol() == king.getCol()) {
							if (b.getCol() > king.getCol()) {
								idd = -1;
							}
							for (int i = b.getCol(); i != king
									.getCol(); i += idd) {
								Touple cord = new Touple(i, b.getRow());
								oneNeedBlock.add(cord);
							}
						} else {
							int bRow = b.getRow();
							int bCol = b.getCol();

							int rowIdd = 1;
							int colIdd = 1;

							if (Integer.signum(bRow - king.getRow()) == 1) {
								rowIdd = -1;
							}

							if (Integer.signum(bCol - king.getCol()) == 1) {
								colIdd = -1;
							}

							while (bCol != king.getCol()) {
								Touple cord = new Touple(bCol, bRow);
								oneNeedBlock.add(cord);
								bRow += rowIdd;
								bCol += colIdd;
							}
						}

						for (Touple c : oneNeedBlock) {
							for (ChessPiece f : pieceList) {
								if (f.isValidMove(c.col, c.row, board)) {
									blockedList.add(b);
									break;
								}
							}
						}
					}
				}
				needblockList.removeAll(blockedList);
				if (needblockList.size() != 0) {
					// cant move, cant take, cant block
					return true;
				} else {
					// cant move, cant take, can block
					// TODO check to see if the same move blocks all the list,
					// else true
					return false;
				}
			} else {
				// can't move can take
				return false;
			}

		} else {
			// can move out
			return false;
		}
	}
	public void setColor() {
		model.setTurn();
	}
}
