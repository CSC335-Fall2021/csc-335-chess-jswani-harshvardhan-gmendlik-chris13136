
/**
 * @filename PiecesTest.java
 * @author Garrison Mendlik 12/8/2021
 * TODO: Add your names
 * @purpose Tests the ChessPiece superclass and subclasses.
 */

package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import model.ChessModel;
import model.pieces.*;

class PiecesTest {
	private ChessPiece[][] pieces;

	@BeforeEach
	void setUp() {
		this.pieces = new ChessPiece[8][8];
	}

	@Test
	void bishopTest() {
		// sets it up in the middle
		Bishop white1 = new Bishop(4, 4, 0);
		Bishop cockBlocker1 = new Bishop(3, 3, 0);
		this.pieces[4][4] = white1;
		// down - left
		System.out.println("Bishop down - left");
		assertTrue(white1.isValidMove(0, 0, pieces));
		// up - right
		System.out.println("Bishop up - right");
		assertTrue(white1.isValidMove(7, 7, pieces));
		// up - left
		System.out.println("Bishop up - left");
		assertTrue(white1.isValidMove(1, 7, pieces));
		// down - right
		System.out.println("Bishop down - right");
		assertTrue(white1.isValidMove(7, 1, pieces));
		
		// down - left, blocked
		System.out.println("Bishop down - left, blocked");
		this.pieces[3][3] = cockBlocker1;
		assertFalse(white1.isValidMove(0, 0, pieces));
		// up - right, blocked
		System.out.println("Bishop up - right, blocked");
		this.pieces[5][5] = cockBlocker1;
		assertFalse(white1.isValidMove(7, 7, pieces));
		// up left, blocked
		System.out.println("Bishop up - left, blocked");
		this.pieces[5][3] = cockBlocker1;
		assertFalse(white1.isValidMove(1, 7, pieces));
		// down - right, blocked
		System.out.println("Bishop down - right, blocked");
		this.pieces[3][5] = cockBlocker1;
		assertFalse(white1.isValidMove(7, 1, pieces));
		
		//trivial test
		System.out.println("Bishop up one");
		assertFalse(white1.isValidMove(4, 5, pieces));
	}
	@Test
	void rookTest() {
		// sets it up in the middle
		Rook white1 = new Rook(4, 4, 0);
		Bishop cockBlocker1 = new Bishop(3, 3, 0);
		this.pieces[4][4] = white1;
		// left
		System.out.println("Rook left");
		assertTrue(white1.isValidMove(4, 0, pieces));
		// right
		System.out.println("Rook right");
		assertTrue(white1.isValidMove(4, 7, pieces));
		// up
		System.out.println("Rook up");
		assertTrue(white1.isValidMove(0, 4, pieces));
		// down
		System.out.println("Rook down");
		assertTrue(white1.isValidMove(7, 4, pieces));
		
		// left, blocked
		System.out.println("Rook left, blocked");
		this.pieces[3][4] = cockBlocker1;
		assertFalse(white1.isValidMove(4, 0, pieces));
		// right, blocked
		System.out.println("Rook right, blocked");
		this.pieces[5][4] = cockBlocker1;
		assertFalse(white1.isValidMove(4, 0, pieces));
		// up, blocked
		System.out.println("Rook up, blocked");
		this.pieces[4][3] = cockBlocker1;
		assertFalse(white1.isValidMove(0, 4, pieces));
		// down, blocked
		System.out.println("Rook down, blocked");
		this.pieces[4][5] = cockBlocker1;
		assertFalse(white1.isValidMove(7, 4, pieces));
		
		//trivial test
		System.out.println("Rook up one left one");
		assertFalse(white1.isValidMove(5, 5, pieces));
	}
	
	@Test
	void queenTest() {
		// sets it up in the middle
		Queen white1 = new Queen(4, 4, 0);
		Bishop cockBlocker1 = new Bishop(3, 3, 0);
		this.pieces[4][4] = white1;
		// left
		System.out.println("Queen left");
		assertTrue(white1.isValidMove(4, 0, pieces));
		// right
		System.out.println("Queen right");
		assertTrue(white1.isValidMove(4, 7, pieces));
		// up
		System.out.println("Queen up");
		assertTrue(white1.isValidMove(0, 4, pieces));
		// down
		System.out.println("Queen down");
		assertTrue(white1.isValidMove(7, 4, pieces));
		// down - left
		System.out.println("Queen down - left");
		assertTrue(white1.isValidMove(0, 0, pieces));
		// up - right
		System.out.println("Queen up - right");
		assertTrue(white1.isValidMove(7, 7, pieces));
		// up - left
		System.out.println("Queen up - left");
		assertTrue(white1.isValidMove(1, 7, pieces));
		// down - right
		System.out.println("Queen down - right");
		assertTrue(white1.isValidMove(7, 1, pieces));		
		
		
		// left, blocked
		System.out.println("Queen left, blocked");
		this.pieces[3][4] = cockBlocker1;
		assertFalse(white1.isValidMove(4, 0, pieces));
		// right, blocked
		System.out.println("Queen right, blocked");
		this.pieces[5][4] = cockBlocker1;
		assertFalse(white1.isValidMove(4, 0, pieces));
		// up, blocked
		System.out.println("Queen up, blocked");
		this.pieces[4][3] = cockBlocker1;
		assertFalse(white1.isValidMove(0, 4, pieces));
		// down, blocked
		System.out.println("Queen down, blocked");
		this.pieces[4][5] = cockBlocker1;
		assertFalse(white1.isValidMove(7, 4, pieces));
		// down - left, blocked
		System.out.println("Queen down - left, blocked");
		this.pieces[3][3] = cockBlocker1;
		assertFalse(white1.isValidMove(0, 0, pieces));
		// up - right, blocked
		System.out.println("Queen up - right, blocked");
		this.pieces[5][5] = cockBlocker1;
		assertFalse(white1.isValidMove(7, 7, pieces));
		// up left, blocked
		System.out.println("Queen up - left, blocked");
		this.pieces[5][3] = cockBlocker1;
		assertFalse(white1.isValidMove(1, 7, pieces));
		// down - right, blocked
		System.out.println("Queen down - right, blocked");
		this.pieces[3][5] = cockBlocker1;
		assertFalse(white1.isValidMove(7, 1, pieces));
	}
	@Test
	void pawnTest() {
		// sets it up in the middle
		Pawn white1 = new Pawn(	1, 1, 0);
		Pawn black1 = new Pawn( 6, 1, 1);
		Bishop cockBlocker1 = new Bishop(3, 3, 0);
		Bishop cockBlocker2 = new Bishop(3, 3, 1);
		this.pieces[1][1] = white1;
		this.pieces[0][2] = cockBlocker2;
		this.pieces[2][2] = cockBlocker2;
		this.pieces[1][6] = black1;
		this.pieces[0][5] = cockBlocker1;
		this.pieces[2][5] = cockBlocker1;
		// up one
		System.out.println("WPawn up one");
		assertTrue(white1.isValidMove(2, 1, pieces));
		// up two
		System.out.println("WPawn up two");
		assertTrue(white1.isValidMove(3, 1, pieces));
		// take left W
		System.out.println("WPawn take left");
		assertTrue(white1.isValidMove(2, 0, pieces));
		// take right W
		System.out.println("WPawn take right");
		assertTrue(white1.isValidMove(2, 2, pieces));
		System.out.println("WPawn take left and right, empty");
		this.pieces[0][2] = null;
		this.pieces[2][2] = null;
		assertFalse(white1.isValidMove(2, 0, pieces));
		assertFalse(white1.isValidMove(2, 2, pieces));
    
		// up two when not at start
		System.out.println("W up two when not at start");
		Pawn white2 = new Pawn(	2, 1, 0);
		this.pieces[2][1] = white2;
		assertFalse(white2.isValidMove(4, 1, pieces));
		
		// up one
		System.out.println("BPawn up one");
		assertTrue(black1.isValidMove(5, 1, pieces));
		// up two
		System.out.println("BPawn up two");
		assertTrue(black1.isValidMove(4, 1, pieces));
		// take left W
		System.out.println("BPawn take left");
		assertTrue(black1.isValidMove(5, 0, pieces));
		// take right W
		System.out.println("BPawn take right");
		assertTrue(black1.isValidMove(5, 2, pieces));
		System.out.println("BPawn take left and right, empty");
		this.pieces[0][5] = null;
		this.pieces[2][5] = null;
		assertFalse(black1.isValidMove(5, 0, pieces));
		assertFalse(black1.isValidMove(5, 2, pieces));
		// up two when not at start
		System.out.println("B up two when not at start");
		Pawn black2 = new Pawn(	5, 1, 0);
		this.pieces[2][1] = black2;
		assertFalse(black2.isValidMove(3, 1, pieces));
	}

	@Test
	void kingTest() {
	// sets it up in the middle
	King white1 = new King(4, 4, 0);
	Bishop cockBlocker1 = new Bishop(3, 3, 0);
	this.pieces[4][4] = white1;
	//up left
	System.out.println("King up left");
	assertTrue(white1.isValidMove(5, 3, pieces));
	//up
	System.out.println("King up");
	assertTrue(white1.isValidMove(5, 4, pieces));
	//up right
	System.out.println("King up right");
	assertTrue(white1.isValidMove(5, 5, pieces));
	//left
	System.out.println("King left");
	assertTrue(white1.isValidMove(4, 3, pieces));
	//right
	System.out.println("King right");
	assertTrue(white1.isValidMove(4, 5, pieces));
	//down left
	System.out.println("King down left");
	assertTrue(white1.isValidMove(3, 3, pieces));
	//down 
	System.out.println("King down");
	assertTrue(white1.isValidMove(3, 4, pieces));
	//down right
	System.out.println("King down right");
	assertTrue(white1.isValidMove(3, 5, pieces));
	
	}
	
	@Test
	void knightTest() {
	this.pieces = new ChessPiece[8][8];
	// sets it up in the middle
	Knight white1 = new Knight(4, 4, 0);
	Bishop cockBlocker1 = new Bishop(3, 3, 0);
	this.pieces[4][4] = white1;
	//up left
	System.out.println("Knight up left");
	assertTrue(white1.isValidMove(5, 2, pieces));
	//up
	System.out.println("Knight up");
	assertTrue(white1.isValidMove(6, 3, pieces));
	//up right
	System.out.println("Knight up right");
	assertTrue(white1.isValidMove(6, 5, pieces));
	//left
	System.out.println("Knight left");
	assertTrue(white1.isValidMove(5, 6, pieces));
	//right
	System.out.println("Knight right");
	assertTrue(white1.isValidMove(6, 3, pieces));
	//down left
	System.out.println("Knight down left");
	assertTrue(white1.isValidMove(5, 2, pieces));
	//down 
	System.out.println("Knight down");
	assertTrue(white1.isValidMove(3, 2, pieces));
	//down right
	System.out.println("Knight down right");
	assertTrue(white1.isValidMove(2, 3, pieces));
	
	}
}