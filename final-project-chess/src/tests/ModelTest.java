package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
		this.model = new ChessModel("saves/InitialBoard.txt");
		assertEquals(ChessModel.WHITE, this.model.getBoard()[0][0].getColor());
		assertTrue(this.model.getBoard()[0][0] instanceof Rook);
		ChessModel.printBoard(this.model.getBoard(), ChessModel.WHITE);
		try {
			this.model.writeGame("InitialBoardCopy.txt");
		} catch (IOException e) {
			fail();
		}
	}
}