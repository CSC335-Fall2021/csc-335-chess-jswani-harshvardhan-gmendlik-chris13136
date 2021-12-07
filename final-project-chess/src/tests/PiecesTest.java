package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ChessModel;
import model.pieces.Bishop;
import model.pieces.ChessPiece;

class PiecesTest {
	private ChessPiece[][] pieces;

	@BeforeEach
	void setUp() {
		this.pieces = new ChessPiece[8][8];
	}

	@Test
	void bishopTest() {
		// test moving -up and right- onto/past friendly piece
		Bishop white1 = new Bishop(0, 0, 0);
		this.pieces[0][0] = white1;
		Bishop white2 = new Bishop(1, 1, 0);
		this.pieces[1][1] = white2;
		assertFalse(white1.isValidMove(2, 2, this.pieces));

		// test moving -up and right- onto enemy piece (capturing enemy piece)
		Bishop black1 = new Bishop(1, 1, 1);
		this.pieces[1][1] = black1;
		assertTrue(white1.isValidMove(1, 1, pieces));
		// and past enemy piece
		assertFalse(white1.isValidMove(2, 2, pieces));
		ChessModel.printBoard(pieces, ChessModel.WHITE);
	}
}