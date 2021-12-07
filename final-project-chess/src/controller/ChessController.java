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
		return false;
	}
}
