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

import javafx.application.Application;
import javafx.stage.Stage;
import pieces.Color;
import pieces.Type;
import table.Table;

public class Controller extends Application{

	static Table table;
	static int BLACKPAWNSTART = 1, WHITEPAWNSTART = 6, BLACKPAWNJUMP = 3, WHITEPAWNJUMP = 4;
	static Stage stage;
	
	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}
	
	public static void main(String[] args) {
		table = new Table();
		JsonParser parser = new JsonParser();
		stage = new Stage();
		try {
			JsonArray array = parser.parse(new FileReader(getJsonFile("chesspieces.json"))).getAsJsonArray();
			for (JsonElement element : array) {
				JsonObject obj = element.getAsJsonObject();
				table.setPiece(obj.get("Row").getAsInt(), obj.get("Column").getAsInt(),
						pieceTranslator(obj.get("Type").getAsString()), colorTranslator(obj.get("Row").getAsString()));

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

	
	
	static File getJsonFile(String filename) {
		ClassLoader cl = Controller.class.getClassLoader();
		File file = new File(cl.getResource(filename).getFile());
		return file;
	}

	static Color colorTranslator(String c) {
		if (c.equalsIgnoreCase("white"))
			return Color.WHITE;
		else
			return Color.BLACK;
	}

	static Type pieceTranslator(String c) {
		if (c.equalsIgnoreCase("rook"))
			return Type.ROOK;
		else if (c.equalsIgnoreCase("knight"))
			return Type.KNIGHT;
		else if (c.equalsIgnoreCase("bishop"))
			return Type.BISHOP;
		else if (c.equalsIgnoreCase("king"))
			return Type.KING;
		else if (c.equalsIgnoreCase("queen"))
			return Type.QUEEN;
		else
			return Type.PAWN;
	}

	static void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
		if (validateMove(fromRow, fromCol, toRow, toCol) && notObstructed(fromRow, fromCol, toRow, toCol) && !(table
				.getPiece(toRow, toCol).getPieceColor().equals(table.getPiece(fromRow, fromCol).getPieceColor())))
			table.movePiece(fromRow, fromCol, toRow, toCol);
	}

	
	static boolean notObstructed(int fromRow, int fromCol, int toRow, int toCol) {
		if (table.getPiece(fromRow, fromCol).getPieceType().equals(Type.PAWN)
				&& !pawnAttack(fromRow, fromCol, toRow, toCol)
				&& !table.getPiece(toRow, toCol).getPieceType().equals(Type.NULLPIECE)) {
			return false;
		} else {
			int topLeftRow = Math.min(toRow, fromRow), topLeftCol = Math.min(toCol, fromCol),
					botRightRow = Math.max(toRow, fromRow), botRightCol = Math.max(toCol, fromCol);
			if (table.getPiece(fromRow, fromCol).getPieceColor().equals(table.getPiece(toRow, toCol).getPieceColor()))
				return false;
			while ((topLeftRow != botRightRow - 1) || (topLeftCol != botRightCol - 1)) {
				if (topLeftRow != botRightRow - 1)
					topLeftRow++;
				if (topLeftCol != botRightCol - 1)
					topLeftCol++;
				if (!table.getPiece(topLeftRow, topLeftCol).getPieceType().equals(Type.NULLPIECE))
					return false;
			}
		}
		return true;
	}

	static boolean validateMove(int fromRow, int fromCol, int toRow, int toCol) {
		boolean validPieceMove = false;
		pieces.Type type = table.getPiece(fromRow, fromCol).getPieceType();
		switch (type) {
		case ROOK: {
			validPieceMove = (horizontalMove(fromRow, fromCol, toRow, toCol)
					|| verticalMove(fromRow, fromCol, toRow, toCol));
			break;
		}
		case KNIGHT: {
			validPieceMove = (((Math.abs((fromRow + 1) - (toRow + 1)) == 2)
					&& (Math.abs((fromCol + 1) - (toCol + 1)) == 1))
					|| ((Math.abs((fromRow + 1) - (toRow + 1)) == 1) && (Math.abs((fromCol + 1) - (toCol + 1)) == 2)));
			break;
		}
		case BISHOP: {
			validPieceMove = diagonalMove(fromRow, fromCol, toRow, toCol);
			break;
		}
		case KING: {
			validPieceMove = (Math.abs((fromRow + 1) - (toRow + 1)) < 2 && Math.abs((fromCol + 1) - (toCol + 1)) < 2);
			break;
		}
		case QUEEN: {
			validPieceMove = (horizontalMove(fromRow, fromCol, toRow, toCol)
					|| verticalMove(fromRow, fromCol, toRow, toCol) || diagonalMove(fromRow, fromCol, toRow, toCol));
			break;
		}
		case PAWN: {
			validPieceMove = pawnJump(fromRow, fromCol, toRow, toCol) || pawnLeap(fromRow, fromCol, toRow, toCol)
					|| pawnAttack(fromRow, fromCol, toRow, toCol)
					|| pawnEnPassantAttack(fromRow, fromCol, toRow, toCol);
		}
		default:
			break;
		}
		return validPieceMove;
	}

	static boolean horizontalMove(int fromRow, int fromCol, int toRow, int toCol) {
		return (fromRow == toRow && fromCol != toCol);
	}

	static boolean verticalMove(int fromRow, int fromCol, int toRow, int toCol) {
		return (fromRow != toRow && fromCol == toCol);
	}

	static boolean diagonalMove(int fromRow, int fromCol, int toRow, int toCol) {
		return ((Math.abs((fromRow + 1) - (toRow + 1))) == (Math.abs((fromCol + 1) - (toCol + 1))));
	}

	static boolean pawnJump(int fromRow, int fromCol, int toRow, int toCol) {
		return (fromRow == WHITEPAWNSTART && toRow == WHITEPAWNJUMP)
				|| (fromRow == BLACKPAWNSTART && toRow == BLACKPAWNJUMP);
	}

	static boolean pawnLeap(int fromRow, int fromCol, int toRow, int toCol) {
		if (table.getPiece(fromRow, fromCol).getPieceColor().equals(Color.WHITE))
			return (toRow == (fromRow - 1)) && (fromCol == toCol);
		else
			return (toRow == (fromRow + 1)) && (fromCol == toCol);
	}

	static boolean pawnAttack(int fromRow, int fromCol, int toRow, int toCol) {
		if (table.getPiece(fromRow, fromCol).getPieceColor().equals(Color.WHITE))
			return (toRow == (fromRow - 1)) && (Math.abs(fromCol - toCol) == 1)
					&& (table.getPiece(toRow, toCol).getPieceColor().equals(Color.BLACK));
		else
			return (toRow == (fromRow + 1)) && (Math.abs(fromCol - toCol) == 1)
					&& (table.getPiece(toRow, toCol).getPieceColor().equals(Color.WHITE));
	}

	static boolean pawnEnPassantAttack(int fromRow, int fromCol, int toRow, int toCol) {
		if (table.getPiece(fromRow, fromCol).getPieceColor().equals(Color.WHITE))
			return (fromRow == BLACKPAWNJUMP) && (table.getPiece(fromRow, toCol).isSpecialMove());
		else
			return (fromRow == WHITEPAWNJUMP) && (table.getPiece(fromRow, toCol).isSpecialMove());
	}



	
}
