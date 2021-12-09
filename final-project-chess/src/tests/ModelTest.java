
/**
 * @filename ModelTest.java
 * @author Garrison Mendlik 12/8/2021
 * @author Jasnam Swani
 * @author Harshvardhan Bhatnagar
 * @author Chris Brinkley
 * @purpose Tests the ChessModel class.
 */

package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import model.ChessModel;
import model.pieces.Rook;

class ModelTest {
	private ChessModel model;

	@Test
	void modelTest() {
		this.model = new ChessModel();
		assertEquals(ChessModel.WHITE, this.model.getBoard()[0][0].getColor());
		assertTrue(this.model.getBoard()[0][0] instanceof Rook);
		ChessModel.printBoard(this.model.getBoard(), ChessModel.WHITE);
		try {
			this.model.writeGame(new File("InitialBoardCopy.txt"));
		} catch (IOException e) {
			fail();
		}

		this.model.getBlack();
		this.model.getWhite();
		assertEquals(ChessModel.WHITE, this.model.getTurn());
		this.model.setTurn();
		assertEquals(ChessModel.BLACK, this.model.getTurn());
		this.model.removePiece(0, 0, this.model.getBoard());
		this.model.printBoard();
		ChessModel.printBoard(this.model.getBoard(), ChessModel.BLACK);
	}
}