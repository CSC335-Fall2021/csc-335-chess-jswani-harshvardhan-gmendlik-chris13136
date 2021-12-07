package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import controller.ChessController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ChessModel;
import model.pieces.*;

public class ChessView extends Application implements Observer {

	ChessModel model;
	ChessController control;
	Stage stage;
	GridPane pane;
	int rowClicked1;
	int colClicked1;
	int rowClicked2;
	int colClicked2;
	boolean selectedPiece;
	HashMap<String,Image> images;

	public class SquareClicked implements EventHandler<MouseEvent>{


		@Override
		public void handle(MouseEvent mouseEvent) {
			ChessPiece[][] board = control.getBoard();
			int turn = control.getTurn();
			HBox square = (HBox) mouseEvent.getSource();
			int rowIndex = GridPane.getRowIndex(square);
			int colIndex = GridPane.getColumnIndex(square);
			int boardCol = colIndex-1;
			int boardRow = -1*(rowIndex-8);
			//TODO: Remove these print statements.
			System.out.println("row: "+ boardRow);
			System.out.println("col: "+ boardCol);
			if (!selectedPiece){
				ChessPiece piece = board[boardCol][boardRow];
				if (piece==null){
					mouseEvent.consume();
					return;
				}else if (piece.getColor()==turn){
					selectedPiece = true;
					rowClicked1 = boardRow;
					colClicked1 = boardCol;
					System.out.println("Selected piece at "+colClicked1+","+rowClicked1);
					return;
				}else{
					System.out.println("Wrong player.");
					mouseEvent.consume();
					return;
				}
			}else{
				ChessPiece piece = board[boardCol][boardRow];
				//This condition means that the player reselected their piece.

				if (piece==null){
					moveImpl(mouseEvent, boardCol, boardRow);
				}
				if(piece.getColor()==turn){
					rowClicked1=boardRow;
					colClicked1=boardCol;
					System.out.println("Selected piece at "+colClicked1+","+rowClicked1);
					return;
				}
				//This condition means that the player clicked either on an empty or on an enemy piece.
				else{
					moveImpl(mouseEvent, boardCol, boardRow);
				}
			}

		}

		private void moveImpl(MouseEvent mouseEvent, int boardCol, int boardRow) {
			rowClicked2=boardRow;
			colClicked2=boardCol;
			if (control.makeMove(colClicked1,rowClicked1, colClicked2, rowClicked2)){
				selectedPiece=false;
				System.out.println("Made move from ("+colClicked1+","+rowClicked1+") to ("+colClicked2+","+rowClicked2);
				if (control.getTurn()==0){
					System.out.println("White plays next");
				}
				else {
					System.out.println("Black plays next");
				}
			}else{
				System.out.println("Invalid move");
				mouseEvent.consume();
			}
		}
	}


	/**
	 * Launches the application. Creates the new model and control and sets up the initial scene.
	 * @param stage the stage for the application
	 */
	@Override
	public void start(Stage stage) throws FileNotFoundException {

		images = new HashMap<>();
		getImages();

		//Arbitrarily chosen value that is not possible for a click.
		rowClicked1=100;
		colClicked1=100;
		rowClicked2=100;
		colClicked2=100;
		selectedPiece=false;

		model = new ChessModel();
		control = new ChessController(model);
		this.stage = stage;
		stage.setHeight(980);
		stage.setWidth(960);
		stage.setTitle("Chess");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		pane = new GridPane();
		pane.setPrefSize(960,960);
		root.setBottom(pane);
		addMenu();
		setScene("game");
		stage.show();
	}

	/**
	 * Fetches images from Wikimedia Commons and stores them in a hashmap for later use.
	 *
	 */
	public void getImages(){
		images.put("blackBishop", new Image("https://upload.wikimedia.org/wikipedia/commons/8/81/Chess_bdt60.png"));
		images.put("whiteBishop", new Image("https://upload.wikimedia.org/wikipedia/commons/9/9b/Chess_blt60.png"));
		images.put("blackKing", new Image("https://upload.wikimedia.org/wikipedia/commons/e/e3/Chess_kdt60.png"));
		images.put("whiteKing", new Image("https://upload.wikimedia.org/wikipedia/commons/3/3b/Chess_klt60.png"));
		images.put("blackKnight", new Image("https://upload.wikimedia.org/wikipedia/commons/f/f1/Chess_ndt60.png"));
		images.put("whiteKnight", new Image("https://upload.wikimedia.org/wikipedia/commons/2/28/Chess_nlt60.png"));
		images.put("blackPawn", new Image("https://upload.wikimedia.org/wikipedia/commons/c/cd/Chess_pdt60.png"));
		images.put("whitePawn", new Image("https://upload.wikimedia.org/wikipedia/commons/0/04/Chess_plt60.png"));
		images.put("blackQueen", new Image("https://upload.wikimedia.org/wikipedia/commons/a/af/Chess_qdt60.png"));
		images.put("whiteQueen", new Image("https://upload.wikimedia.org/wikipedia/commons/4/49/Chess_qlt60.png"));
		images.put("blackRook", new Image("https://upload.wikimedia.org/wikipedia/commons/a/a0/Chess_rdt60.png"));
		images.put("whiteRook", new Image("https://upload.wikimedia.org/wikipedia/commons/5/5c/Chess_rlt60.png"));
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
					square.setAlignment(Pos.CENTER);
					if (col!=8){
						if (color.equals(Color.WHITE)){
							color = Color.GRAY;
						}else{ color = Color.WHITE;}
					}
					square.setDisable(false);
					square.addEventHandler(MouseEvent.MOUSE_CLICKED,new SquareClicked());

				}
			}
		}
		addPieces(pane);
	}


	/**
	 * This method adds chess pieces to the board based on their positions.
	 * @param pane GridPane containing the chess board
	 * @throws FileNotFoundException
	 */
	public void addPieces(GridPane pane) throws FileNotFoundException {

		ChessPiece[][] pieces = control.getBoard();
		for (int row=0; row<=7; row++){
			for (int col=0; col<=7; col++){
				ChessPiece piece= pieces[col][row];
				if (piece!=null){
					String imgStr="";
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
					ImageView imageView = new ImageView(images.get(imgStr));
					imageView.setFitHeight(80);
					imageView.setFitWidth(80);
					HBox square = (HBox) getNodeByRowColumnIndex(8-row, col+1, pane);
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
