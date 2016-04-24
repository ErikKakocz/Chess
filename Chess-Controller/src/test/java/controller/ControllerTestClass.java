package controller;
import static org.junit.Assert.*;

import org.junit.Test;


public class ControllerTestClass {
	
	@Test
	
	public void ColorTranslatorTest(){
		assertEquals(pieces.Color.WHITE,Controller.colorTranslator("White"));
		assertEquals(pieces.Color.BLACK,Controller.colorTranslator("Black"));
		assertEquals(pieces.Color.BLACK,Controller.colorTranslator("BlAck"));
	}
	
	@Test
	public void PieceTranslatorTest(){
		assertEquals(pieces.Type.ROOK,Controller.pieceTranslator("Rook"));
		assertEquals(pieces.Type.BISHOP,Controller.pieceTranslator("Bishop"));
		assertEquals(pieces.Type.KNIGHT,Controller.pieceTranslator("Knight"));
		assertEquals(pieces.Type.KING,Controller.pieceTranslator("King"));
		assertEquals(pieces.Type.QUEEN,Controller.pieceTranslator("Queen"));
		assertEquals(pieces.Type.PAWN,Controller.pieceTranslator("PAWN"));
		assertEquals(pieces.Type.PAWN,Controller.pieceTranslator("PWN"));
		assertEquals(pieces.Type.PAWN,Controller.pieceTranslator(""));
		assertEquals(pieces.Type.PAWN,Controller.pieceTranslator("ASD"));
	}
	
	
}
