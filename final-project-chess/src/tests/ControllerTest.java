
/**
 * @filename ControllerTest.java
 * @author Garrison Mendlik 12/8/2021
 * @author Jasnam Swani
 * @author Harshvardhan Bhatnagar
 * @author Chris Brinkley
 * @purpose Tests the ChessController class.
 */

package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import controller.ChessController;
import model.ChessModel;

class ControllerTest {
	private ChessController control;

	@Before
	void setUp() {
		this.control = new ChessController(new ChessModel());
	}

	@Test
	void test() {
		assertEquals(this.control.getBoard(), new ChessModel().getBoard());
		assertTrue(this.control.makeMove(0, 1, 0, 2));
		assertFalse(this.control.makeMove(0, 0, 0, 1));
		assertEquals(ChessModel.BLACK, this.control.getTurn());
		this.control.setTurn();
		assertEquals(ChessModel.WHITE, this.control.getTurn());
		assertFalse(this.control.isGameOver());
		this.control.setColor();
		assertEquals(ChessModel.BLACK, this.control.getTurn());
	}
}