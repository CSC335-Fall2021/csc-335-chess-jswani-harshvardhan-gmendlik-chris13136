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
			
			// find attacker
			for (ChessPiece p : pieceList) {
				if (p)
			}
		}
		else {
			return false;
		}

		return moveflag && attackFlag && blockFlag;
	}
}
