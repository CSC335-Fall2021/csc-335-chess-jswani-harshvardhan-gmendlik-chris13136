
/**
 * @filename ChessView.java
 * @author Garrison Mendlik 12/8/2021
 * @author Jasnam Swani
 * @author Harshvardhan Bhatnagar
 * @author Chris Brinkley
 * @purpose Initializes the application GUI.
 */

package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import controller.ChessController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.ChessModel;
import model.pieces.Bishop;
import model.pieces.ChessPiece;
import model.pieces.King;
import model.pieces.Knight;
import model.pieces.Pawn;
import model.pieces.Queen;
import model.pieces.Rook;

public class ChessView extends Application {

	ChessModel model;
	ChessController control;
	Stage stage;
	GridPane pane;
	int rowClicked1;
	int colClicked1;
	int rowClicked2;
	int colClicked2;
	boolean selectedPiece;
	HashMap<String, Image> images;

	/**
	 * Implements a square with an EventHandler to capture mouse clicks.
	 */
	public class SquareClicked implements EventHandler<MouseEvent> {

		@Override
		/**
		 * Handles a click on a square.
		 * 
		 * @param mouseEvent Mouse event captured
		 */
		public void handle(MouseEvent mouseEvent) {
			ChessPiece[][] board = control.getBoard();
			int turn = control.getTurn();
			HBox square = (HBox) mouseEvent.getSource();
			int rowIndex = GridPane.getRowIndex(square);
			int colIndex = GridPane.getColumnIndex(square);
			int boardCol = colIndex - 1;
			int boardRow = -1 * (rowIndex - 8);
			// TODO: Remove these print statements.
			System.out.println("row: " + boardRow);
			System.out.println("col: " + boardCol);
			if (!selectedPiece) {
				ChessPiece piece = board[boardCol][boardRow];
				if (piece == null) {
					mouseEvent.consume();

				} else if (piece.getColor() == turn) {
					selectedPiece = true;
					rowClicked1 = boardRow;
					colClicked1 = boardCol;
					System.out.println("Selected piece at " + colClicked1 + ","
							+ rowClicked1);
					System.out.println(
							model.getBoard()[colClicked1][rowClicked1]);

				} else {
					System.out.println("Wrong player.");
					mouseEvent.consume();

				}
			} else {
				ChessPiece piece = board[boardCol][boardRow];
				// This condition means that the player reselected their piece.

				if (piece == null) {
					moveImpl(mouseEvent, boardCol, boardRow);
				} else if (piece.getColor() == turn) {
					rowClicked1 = boardRow;
					colClicked1 = boardCol;
					System.out.println("Selected piece at " + colClicked1 + ","
							+ rowClicked1);
					System.out.println(
							model.getBoard()[colClicked1][rowClicked1]);

				}
				// This condition means that the player clicked either on an
				// empty or on an enemy piece.
				else {
					moveImpl(mouseEvent, boardCol, boardRow);
				}
			}

			model.printBoard();
		}

		/**
		 * Attempts to make the move from the mouse event.
		 * 
		 * @param mouseEvent Mouse event captured
		 * @param boardCol   Column of the board clicked
		 * @param boardRow   Row of the board clicked
		 */
		private void moveImpl(MouseEvent mouseEvent, int boardCol,
				int boardRow) {
			rowClicked2 = boardRow;
			colClicked2 = boardCol;
			if (control.makeMove(colClicked1, rowClicked1, colClicked2,
					rowClicked2)) {
				selectedPiece = false;
				System.out.println(
						"Made move from (" + colClicked1 + "," + rowClicked1
								+ ") to (" + colClicked2 + "," + rowClicked2);
				if (control.getTurn() == 0) {
					System.out.println("White plays next");
				} else {
					System.out.println("Black plays next");
				}
			} else {
				System.out.println("Invalid move");
				mouseEvent.consume();
			}
			HBox oldSquare = (HBox) getNodeByRowColumnIndex(8 - rowClicked1,
					colClicked1 + 1, pane);
			HBox newSquare = (HBox) getNodeByRowColumnIndex(8 - rowClicked2,
					colClicked2 + 1, pane);

			ChessPiece[][] board = control.getBoard();

			if (board[colClicked1][rowClicked1] == null) {
				oldSquare.getChildren().clear();
			}

			newSquare.getChildren().clear();

			ChessPiece piece = board[boardCol][boardRow];

			if (piece != null) {
				String imgStr = "";
				if (piece.getColor() == 0) {
					imgStr += "white";
				} else
					imgStr += "black";

				if (piece instanceof Pawn) {
					imgStr += "Pawn";
				} else if (piece instanceof King) {
					imgStr += "King";
				} else if (piece instanceof Queen) {
					imgStr += "Queen";
				} else if (piece instanceof Bishop) {
					imgStr += "Bishop";
				} else if (piece instanceof Knight) {
					imgStr += "Knight";
				} else if (piece instanceof Rook) {
					imgStr += "Rook";
				}
				ImageView imageView = new ImageView(images.get(imgStr));
				imageView.setFitHeight(80);
				imageView.setFitWidth(80);
				newSquare.getChildren().add(imageView);
				control.setTurn();
			}

		}
	}

	/**
	 * Launches the application. Creates the new model and control and sets up
	 * the initial scene.
	 * 
	 * @param stage the stage for the application
	 */
	@Override
	public void start(Stage stage) throws FileNotFoundException {

		images = new HashMap<>();
		getImages();

		// Arbitrarily chosen value that is not possible for a click.
		rowClicked1 = 100;
		colClicked1 = 100;
		rowClicked2 = 100;
		colClicked2 = 100;
		selectedPiece = false;

		this.stage = stage;
		stage.setHeight(980);
		stage.setWidth(960);
		stage.setTitle("Chess");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		pane = new GridPane();
		pane.setPrefSize(960, 960);
		root.setBottom(pane);
		addMenu();
		stage.show();
	}

	/**
	 * Fetches images from Wikimedia Commons and stores them in a hashmap for
	 * later use.
	 */
	public void getImages() {
		images.put("blackBishop", new Image(
				"https://upload.wikimedia.org/wikipedia/commons/8/81/Chess_bdt60.png"));
		images.put("whiteBishop", new Image(
				"https://upload.wikimedia.org/wikipedia/commons/9/9b/Chess_blt60.png"));
		images.put("blackKing", new Image(
				"https://upload.wikimedia.org/wikipedia/commons/e/e3/Chess_kdt60.png"));
		images.put("whiteKing", new Image(
				"https://upload.wikimedia.org/wikipedia/commons/3/3b/Chess_klt60.png"));
		images.put("blackKnight", new Image(
				"https://upload.wikimedia.org/wikipedia/commons/f/f1/Chess_ndt60.png"));
		images.put("whiteKnight", new Image(
				"https://upload.wikimedia.org/wikipedia/commons/2/28/Chess_nlt60.png"));
		images.put("blackPawn", new Image(
				"https://upload.wikimedia.org/wikipedia/commons/c/cd/Chess_pdt60.png"));
		images.put("whitePawn", new Image(
				"https://upload.wikimedia.org/wikipedia/commons/0/04/Chess_plt60.png"));
		images.put("blackQueen", new Image(
				"https://upload.wikimedia.org/wikipedia/commons/a/af/Chess_qdt60.png"));
		images.put("whiteQueen", new Image(
				"https://upload.wikimedia.org/wikipedia/commons/4/49/Chess_qlt60.png"));
		images.put("blackRook", new Image(
				"https://upload.wikimedia.org/wikipedia/commons/a/a0/Chess_rdt60.png"));
		images.put("whiteRook", new Image(
				"https://upload.wikimedia.org/wikipedia/commons/5/5c/Chess_rlt60.png"));
	}

	/**
	 * Sets the scene on the basis of the argument string provided.
	 * 
	 * @param page
	 */
	public void setScene(String page) {
		Scene scene = stage.getScene();
		BorderPane root = (BorderPane) scene.getRoot();
		GridPane pane = (GridPane) root.getBottom();
		pane.getChildren().clear();
		if (page.equals("game")) {

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
			for (int row = 1; row <= 8; row++) {
				for (int col = 1; col <= 8; col++) {
					HBox square = new HBox();
					square.setPrefSize(80, 80);
					square.setBackground(new Background(
							new BackgroundFill(color, null, null)));
					square.setBorder(new Border(new BorderStroke(Color.BLACK,
							BorderStrokeStyle.SOLID, null, null)));
					pane.add(square, col, row);
					square.setAlignment(Pos.CENTER);
					if (col != 8) {
						if (color.equals(Color.WHITE)) {
							color = Color.GRAY;
						} else {
							color = Color.WHITE;
						}
					}
					square.setDisable(false);
					square.addEventHandler(MouseEvent.MOUSE_CLICKED,
							new SquareClicked());

				}
			}
		}
		addPieces(pane);
	}

	/**
	 * This method adds chess pieces to the board based on their positions.
	 * 
	 * @param pane GridPane containing the chess board
	 * @throws FileNotFoundException
	 */
	public void addPieces(GridPane pane) {

		ChessPiece[][] pieces = control.getBoard();
		for (int row = 0; row <= 7; row++) {
			for (int col = 0; col <= 7; col++) {
				ChessPiece piece = pieces[col][row];
				if (piece != null) {
					String imgStr = "";
					if (piece.getColor() == 0) {
						imgStr += "white";
					} else
						imgStr += "black";

					if (piece instanceof Pawn) {
						imgStr += "Pawn";
					} else if (piece instanceof King) {
						imgStr += "King";
					} else if (piece instanceof Queen) {
						imgStr += "Queen";
					} else if (piece instanceof Bishop) {
						imgStr += "Bishop";
					} else if (piece instanceof Knight) {
						imgStr += "Knight";
					} else if (piece instanceof Rook) {
						imgStr += "Rook";
					}
					ImageView imageView = new ImageView(images.get(imgStr));
					imageView.setFitHeight(80);
					imageView.setFitWidth(80);
					HBox square = (HBox) getNodeByRowColumnIndex(8 - row,
							col + 1, pane);
					square.getChildren().add(imageView);
				}
			}
		}
	}

	/**
	 * Returns a node at row, col index
	 * 
	 * @param row      Row of node
	 * @param column   Column of node
	 * @param gridPane GridPane of the nodes
	 * @return
	 */
	public Node getNodeByRowColumnIndex(final int row, final int column,
			GridPane gridPane) {
		Node result = null;
		ObservableList<Node> children = gridPane.getChildren();
		for (Node node : children) {
			if (GridPane.getRowIndex(node) == row
					&& GridPane.getColumnIndex(node) == column) {
				result = node;
				break;
			}
		}
		return result;
	}

	/**
	 * Adds numbers to both sides of the board.
	 * 
	 * @param pane The GridPane
	 * @param col  0 for left, 9 for right.
	 */
	private void addNumbers(GridPane pane, int col) {
		for (int row = 1; row <= 8; row++) {
			HBox numBox = new HBox();
			Text text = new Text(Integer.toString(9 - row));
			numBox.setDisable(true);
			numBox.getChildren().add(text);
			numBox.setPrefSize(80, 80);
			numBox.setAlignment(Pos.CENTER);
			pane.add(numBox, col, row);
		}
	}

	/**
	 * Adds Letters to the top and bottom of the board.
	 * 
	 * @param pane The grid pane.
	 * @param row  0 for the top, 9 for the bottom.
	 */
	private void addLetters(GridPane pane, int row) {
		char letter = 'a';
		for (int col = 1; col < 9; col++) {
			HBox letterBox = new HBox();
			Text text = new Text(Character.toString(letter));
			letterBox.setDisable(true);
			letterBox.getChildren().add(text);
			letterBox.setPrefSize(80, 80);
			letterBox.setAlignment(Pos.CENTER);
			pane.add(letterBox, col, row);
			letter++;
		}
	}

	/**
	 * This method adds the menu bar at the top of the scene.
	 */
	public void addMenu() {
		Scene scene = stage.getScene();

		// New game menu item
		MenuItem newGame = new MenuItem("_New Game");
		newGame.setOnAction(event -> {
			this.model = new ChessModel();
			this.control = new ChessController(this.model);
			this.setScene("game");
		});

		SeparatorMenuItem seperator = new SeparatorMenuItem();

		Path currentDirectory = Paths
				.get(Paths.get("").toAbsolutePath().toString() + "\\saves");
		try {
			Files.createDirectory(currentDirectory); // Create saves folder
		} catch (IOException e) {
			// We don't care that the saves folder exists already, good!
		}

		// Save game menu item
		MenuItem saveGame = new MenuItem("_Save Game As...");
		saveGame.setOnAction(event -> {
			if (this.model == null)
				return;

			FileChooser saveGameFC = new FileChooser();
			saveGameFC.setInitialDirectory(currentDirectory.toFile());
			saveGameFC.setTitle("Save Game As");
			saveGameFC.getExtensionFilters()
					.addAll(new ExtensionFilter("Text Files", "*.txt"));
			File saveTo = saveGameFC.showSaveDialog(stage);
			try {
				if (saveTo == null)
					return;

				this.model.writeGame(saveTo);
			} catch (IOException e) {
				// Ignore
			}
			saveGameFC = null; // trash
		});

		// load game menu item
		MenuItem loadGame = new MenuItem("_Load Game...");
		loadGame.setOnAction(event -> {
			FileChooser loadGameFC = new FileChooser();
			loadGameFC.setInitialDirectory(currentDirectory.toFile());
			loadGameFC.setTitle("Load Game");
			loadGameFC.getExtensionFilters()
					.addAll(new ExtensionFilter("Text Files", "*.txt"));
			File toLoad = loadGameFC.showOpenDialog(stage);
			try {
				this.model = new ChessModel(toLoad);
				this.control = new ChessController(this.model);
				this.setScene("game");
			} catch (FileNotFoundException e) {
			} catch (NullPointerException e) { // Cancel or force close, ignore
			}
			loadGameFC = null; // trash
		});

		// options menu
		MenuButton menu = new MenuButton("Options", null, newGame, seperator,
				saveGame, loadGame);
		HBox bar = new HBox(menu);
		bar.setFillHeight(true);
		bar.setPrefSize(800, 20);
		bar.setBackground(
				new Background(new BackgroundFill(Color.GRAY, null, null)));
		BorderPane root = (BorderPane) scene.getRoot();
		root.setTop(bar);
	}
}
