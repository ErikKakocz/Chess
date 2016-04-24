package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import pieces.Color;
import pieces.Type;
import table.Table;

public class Controller {

	static Table table;
	
	public static void main(String[] args) {
		table=new Table();
		JsonParser parser=new JsonParser();
		try {
		JsonArray array=parser.parse(new FileReader(getJsonFile("chesspieces.json"))).getAsJsonArray();
			for(JsonElement element:array) 
			{
				JsonObject obj=element.getAsJsonObject();
				table.setPiece(obj.get("Row").getAsInt(),
								obj.get("Column").getAsInt(),
								pieceTranslator(obj.get("Type").getAsString()), 
								colorTranslator(obj.get("Row").getAsString()));
				
			}
				
				
				
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static File getJsonFile(String filename){
		ClassLoader cl=Controller.class.getClassLoader();
		File file=new File(cl.getResource(filename).getFile());
		return file;
	} 
	
	static Color colorTranslator(String c){
		if(c.equalsIgnoreCase("white"))
			return Color.WHITE;
		else 
			return Color.BLACK;
	}
	
	static Type pieceTranslator(String c){
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
	
	static void movePiece(){
		
	
	}
	
	static boolean validateMove(pieces.Type type,int fromRow,int fromCol,int toRow,int toCol){
		boolean validPieceMove=false;
		switch(type){
			case ROOK:{validPieceMove=( horizontalMove(fromRow,fromCol,toRow,toCol)||
										verticalMove(fromRow,fromCol,toRow,toCol) );
										break;}
			case KNIGHT:{validPieceMove=(((Math.abs((fromRow+1)-(toRow+1))==2)&&(Math.abs((fromCol+1)-(toCol+1))==1))||((Math.abs((fromRow+1)-(toRow+1))==1)&&(Math.abs((fromCol+1)-(toCol+1))==2)));
										break;}
			case BISHOP:{validPieceMove=diagonalMove(fromRow,fromCol,toRow,toCol);
										break;}
			case KING:{validPieceMove=(Math.abs((fromRow+1)-(toRow+1))<2&&Math.abs((fromCol+1)-(toCol+1))<2);
										break;}
			case QUEEN:{validPieceMove=(horizontalMove(fromRow,fromCol,toRow,toCol)||
										verticalMove(fromRow,fromCol,toRow,toCol)|| 
										diagonalMove(fromRow,fromCol,toRow,toCol));
										break;}
			case PAWN:
		} 
		return validPieceMove;
	}
	
	static boolean horizontalMove(int fromRow,int fromCol,int toRow,int toCol){
		return (fromRow==toRow&&fromCol!=toCol);
	}
	
	static boolean verticalMove(int fromRow,int fromCol,int toRow,int toCol){
		return (fromRow!=toRow&&fromCol==toCol);
	}
	
	static boolean diagonalMove(int fromRow,int fromCol,int toRow,int toCol){
		
		return ((Math.abs((fromRow+1)-(toRow+1)))==(Math.abs((fromCol+1)-(toCol+1))));
	}
}
