package controller;

import java.util.ArrayList;

import model.ChessModel;
import model.pieces.*;

public class ChessController {

    private ChessModel model;
    private ChessPiece[][] board;
    public ChessController(ChessModel model){
        //TODO: I have created this constructor just to begin working on the view. Feel free to modify as needed.
        this.model=model;
        board = model.getBoard();
        
    }

    public ChessPiece[][] getBoard(){
        return board;
    }

    public boolean makeMove(int curCol, int curRow, int newCol, int newRow) {
    	if(board[curCol][curRow] != null) {
    		ChessPiece cur = board[newCol][newRow];
    		if(cur instanceof King) {
    			// no moving into check rule
    			if(!canBeAttacked(newCol, newRow)) {
    				return false;
    			}
    		}
    		cur.isValidMove(newCol, newRow, board);
    	}
    	return false;
    }

	private boolean canBeAttacked(int newCol, int newRow) {
		// for each piece in opp list isVaildMove on newCol,newRow
		ArrayList<ChessPiece> oppList = model.getBlack();
		if (model.getTurn() == 1) {
			oppList = model.getWhite();
		}
		for (ChessPiece p : oppList) {
			if(p.isValidMove(newRow, newCol, board)) {
				return true;
			}
		}
		return false;
	}

    //TODO: implement a method that when moving the king makes sure it doesnt move into check.
	/**
	 * Ways to escape check
	 * 1. move king (aka check the 8 squares for is vaild  move)
	 * 2. attack attacker (aka canBeAttacked on attacker square
	 * 3. block (aka canBeAttacked or isValid move for each piece 
	 * 	  in opposite colors list on each of the in between squares)
	 * @return isGameOver?
	 */
	public boolean isGameOver() {
		boolean moveflag = false;
		boolean attackFlag = false;
		boolean blockFlag = false;
		// 1. move king (aka check the 8 squares for is vaild  move)
		// find correct king
		ArrayList<ChessPiece> pieceList = model.getBlack();
		ArrayList<ChessPiece> oppList   = model.getWhite();
		if (model.getTurn() == 1) {
			pieceList = model.getWhite();
		    oppList   = model.getBlack();
		}
		
		ChessPiece king = pieceList.get(0);
		for (ChessPiece p : pieceList) {
			if(p instanceof King) {
				king = p;
				break;
			}
		}
		int curRow = king.getRow();
		int curCol = king.getCol();
		if((
				//left up
				!king.isValidMove(curCol - 1, curRow - 1, board) &&
				// up
				!king.isValidMove(curCol - 0, curRow - 1, board) &&
				// right up
				!king.isValidMove(curCol + 1, curRow - 1, board) &&
				//left
				!king.isValidMove(curCol - 1, curRow + 0, board) &&
				//right
				!king.isValidMove(curCol + 1, curRow + 0, board) &&
				//left down
				!king.isValidMove(curCol - 1, curRow + 1, board) &&
				// down
				!king.isValidMove(curCol + 0, curRow + 1, board) &&
				// right down
				!king.isValidMove(curCol + 1, curRow + 1, board))) {
			moveflag = true;
			//2. attack attacker (aka canBeAttacked on attacker square)
			
			// find attacker/s
			ArrayList<ChessPiece> attackersList = new ArrayList<ChessPiece>();
			ArrayList<ChessPiece> needblockList = new ArrayList<ChessPiece>();
			ArrayList<ChessPiece> blockedList = new ArrayList<ChessPiece>();
			for (ChessPiece p : pieceList) {
				if (p.isValidMove(curRow, curCol, board)) {
					attackersList.add(p);
					attackFlag = true;
				}
			}
			
			//take out attackers
			for (ChessPiece p : attackersList) {
				curRow = p.getRow();
				curCol = p.getCol();
				boolean needBlock = true;
				for (ChessPiece x : pieceList) {
					if(x.isValidMove(curRow, curCol, board)) {
						needBlock = false;
					}
				}
				if (needBlock) {
					needblockList.add(p);
				}
			}
			if(needblockList.size() != 0) {
					class Touple{
						public int col;
						public int row;
						public Touple(int col, int row) {
							this.col = col;
							this.row = row;
						}
					}
					// 3. block (aka canBeAttacked or isValid move for each piece 
					// 	  in opposite colors list on each of the in between squares)
					
					for (ChessPiece b : needblockList) {
						if(b instanceof Pawn) {
							// cant move, cant take, pawn cant be blocked
							return true;
						}
						if(b instanceof Knight) {
							// cant move, cant take, Knight cant be blocked
							return true;
						}
						if(b instanceof Bishop) {
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
									if(f.isValidMove(c.col, c.row, board)) {
										blockedList.add(b);
										break;
									}
								}
							}
							
						}
						if(b instanceof Rook) {
							
						}
						if(b instanceof Queen) {
							
						}
					}
					needblockList.removeAll(blockedList);
					if(needblockList.size() !=0) {
						//cant move, cant take, cant block
						return true;
					}
					else {
						// cant move, cant take, can block
						// TODO check to see if the same move blocks all the list, else true
						return false;
					}
				}
				else {
					// can't move can take 
					return false;
				}
				
		}
		else {
			//can move out
			return false;
		}
	}
}
