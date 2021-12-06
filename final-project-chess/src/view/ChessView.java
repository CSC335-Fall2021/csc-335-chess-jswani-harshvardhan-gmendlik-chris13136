package view;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.ChessModel;
import model.pieces.ChessPiece;

public class ChessView extends Application implements Observer {
	private ChessPiece[][] pieces;
	ChessModel model;

	@Override
	public void start(Stage arg0) throws Exception {
		model = new ChessModel();


	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
}
