package controller;

import pieces.Color;
import pieces.Type;
import table.Table;
public class Controller {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private Color colorTranslator(String c){
		if(c.equalsIgnoreCase("white"))
			return Color.WHITE;
		else 
			return Color.BLACK;
	}
	
	private Type pieceTranslator(String c){
		if(c.equalsIgnoreCase("rook"))
			return Type.ROOK;
		else if(c.equalsIgnoreCase("knight"))
			return Type.KNIGHT;
		else if(c.equalsIgnoreCase("bishop"))
			return Type.BISHOP;
		else if(c.equalsIgnoreCase("king"))
			return Type.KING;
		else if(c.equalsIgnoreCase("queen"))
			return Type.QUEEN;
		else 
			return Type.PAWN;
	}
	
}
