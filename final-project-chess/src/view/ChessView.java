package view;

import java.util.Observable;
import java.util.Observer;

import controller.ChessController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.ChessModel;
import model.pieces.ChessPiece;

public class ChessView extends Application implements Observer {
	ChessModel model;
	ChessController control;
	Stage stage;

	/**
	 * Launches the application. Creates the new model and control and sets up the initial scene.
	 * @param stage the stage for the application
	 */
	@Override
	public void start(Stage stage){
		model = new ChessModel();
		control = new ChessController(model);
		this.stage = stage;
		stage.setHeight(820);
		stage.setWidth(800);
		stage.setTitle("Chess");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		GridPane pane = new GridPane();
		pane.setPrefSize(800,800);
		root.setBottom(pane);
		addMenu();
		stage.show();
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
