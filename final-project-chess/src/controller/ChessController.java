package controller;

import model.ChessModel;
import model.pieces.ChessPiece;

public class ChessController {

    private ChessModel model;
    public ChessController(ChessModel model){
        //TODO: I have created this constructor just to begin working on the view. Feel free to modify as needed.
        this.model=model;
    }

    public ChessPiece[][] getBoard(){
        //TODO: return the chess piece array representing the board.
        return null;
    }

    //TODO: implement a method that validates and makes a move in the model.
}
