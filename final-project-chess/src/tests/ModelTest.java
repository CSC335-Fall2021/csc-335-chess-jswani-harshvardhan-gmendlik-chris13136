
/**
 * @filename ModelTest.java
 * @author Garrison Mendlik 12/8/2021
 * TODO: Add your names
 * @purpose Tests the ChessModel class.
 */

package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ChessModel;
import model.pieces.Rook;

class ModelTest {
	private ChessModel model;

	@BeforeEach
	void setUp() {

	}

	@Test
	void loadGameTest() {
		try {
			this.model = new ChessModel(new File("saves/InitialBoard.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		assertEquals(ChessModel.WHITE, this.model.getBoard()[0][0].getColor());
		assertTrue(this.model.getBoard()[0][0] instanceof Rook);
		ChessModel.printBoard(this.model.getBoard(), ChessModel.WHITE);
		try {
			this.model.writeGame(new File("InitialBoardCopy.txt"));
		} catch (IOException e) {
			fail();
		}
	}

	// TODO: implement more ChessModel tests
}