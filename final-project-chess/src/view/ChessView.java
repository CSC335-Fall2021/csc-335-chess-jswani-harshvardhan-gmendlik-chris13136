package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import controller.ChessController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.ChessModel;
import model.pieces.*;

import javax.swing.*;

public class ChessView extends Application implements Observer {
	ChessModel model;
	ChessController control;
	Stage stage;

	/**
	 * Launches the application. Creates the new model and control and sets up the initial scene.
	 * @param stage the stage for the application
	 */
	@Override
	public void start(Stage stage) throws FileNotFoundException {
		model = new ChessModel();
		control = new ChessController(model);
		this.stage = stage;
		stage.setHeight(980);
		stage.setWidth(960);
		stage.setTitle("Chess");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		GridPane pane = new GridPane();
		pane.setPrefSize(960,960);
		root.setBottom(pane);
		addMenu();
		setScene("game");
		stage.show();
	}

	/**
	 * Sets the scene on the basis of the argument string provided.
	 * @param page
	 */
	public void setScene(String page) throws FileNotFoundException {
		Scene scene = stage.getScene();
		BorderPane root =(BorderPane) scene.getRoot();
		GridPane pane = (GridPane) root.getBottom();
		if (page.equals("game")){

			int rowCount = 10;
			int columnCount = 10;

			RowConstraints rc = new RowConstraints();

			rc.setPercentHeight(97.959184d / rowCount);

			for (int i = 0; i < rowCount; i++) {
				pane.getRowConstraints().add(rc);
			}

			ColumnConstraints cc = new ColumnConstraints();
			cc.setPercentWidth(100d / columnCount);

			for (int i = 0; i < columnCount; i++) {
				pane.getColumnConstraints().add(cc);
			}

			addLetters(pane, 9);
			addLetters(pane, 0);
			addNumbers(pane, 0);
			addNumbers(pane, 9);

			Color color = Color.WHITE;
			for (int row = 1; row<=8; row++){
				for (int col = 1; col<=8; col++){
					HBox square = new HBox();
					square.setPrefSize(80,80);
					square.setBackground(new Background(new BackgroundFill(color, null, null)));
					square.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
					pane.add(square, col, row);
					if (col!=8){
						if (color.equals(Color.WHITE)){
							color = Color.GRAY;
						}else{ color = Color.WHITE;}
					}
				}
			}
		}
		addPieces(pane);
	}


	public void addPieces(GridPane pane) throws FileNotFoundException {
		ChessPiece[][] pieces = control.getBoard();
		for (int row=0; row<=7; row++){
			for (int col=0; col<=7; col++){
				String imgStr="img/";
				ChessPiece piece= pieces[col][row];
				if (piece!=null){
					if (piece.getColor()==0){
						imgStr+="white";
					}else imgStr+="black";

					if (piece instanceof Pawn){
						imgStr+="Pawn";
					}else if (piece instanceof King){
						imgStr+="King";
					}else if(piece instanceof Queen){
						imgStr+="Queen";
					}else if (piece instanceof Bishop){
						imgStr+="Bishop";
					}else if (piece instanceof Knight){
						imgStr+="Knight";
					}else if (piece instanceof Rook){
						imgStr+="Rook";
					}
					imgStr+=".png";
					FileInputStream input = new FileInputStream(imgStr);
					Image img = new Image(input);
					ImageView imageView = new ImageView(img);
					imageView.setFitHeight(80);
					imageView.setFitWidth(80);
					HBox square = (HBox) getNodeByRowColumnIndex(8-row, 8-col, pane);
					square.getChildren().add(imageView);
				}
			}
		}
	}

	public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
		Node result = null;
		ObservableList<Node> children = gridPane.getChildren();
		for (Node node : children) {
			if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
				result = node;
				break;
			}
		}
		return result;
	}

	/**
	 * Adds numbers to both sides of the board.
	 * @param pane The GridPane
	 * @param col 0 for left, 9 for right.
	 */
	private void addNumbers(GridPane pane, int col){
		for (int row=1; row<=8; row++){
			HBox numBox = new HBox();
			Text text=new Text(Integer.toString(9-row));
			numBox.setDisable(true);
			numBox.getChildren().add(text);
			numBox.setPrefSize(80,80);
			numBox.setAlignment(Pos.CENTER);
			pane.add(numBox,col,row);
		}
	}

	/**
	 * Adds Letters to the top and bottom of the board.
	 * @param pane The grid pane.
	 * @param row 0 for the top, 9 for the bottom.
	 */
	private void addLetters(GridPane pane, int row) {
		char letter = 'a';
		for (int col=1; col<9; col++){
			HBox letterBox = new HBox();
			Text text=new Text(Character.toString(letter));
			letterBox.setDisable(true);
			letterBox.getChildren().add(text);
			letterBox.setPrefSize(80,80);
			letterBox.setAlignment(Pos.CENTER);
			pane.add(letterBox,col,row);
			letter++;
		}
	}

	/**
	 * This method adds the menu bar at the top of the scene.
	 */
	public void addMenu(){
		Scene scene = stage.getScene();
		MenuItem newGame = new MenuItem("New Game");
		MenuItem saveGame = new MenuItem("Save Game");
		MenuItem loadGame = new MenuItem("Load Game");
		MenuButton menu = new MenuButton("Options", null, newGame, saveGame, loadGame);
		HBox bar = new HBox(menu);
		bar.setFillHeight(true);
		bar.setPrefSize(800, 20);
		bar.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		BorderPane root = (BorderPane) scene.getRoot();
		root.setTop(bar);
	}



	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
}
