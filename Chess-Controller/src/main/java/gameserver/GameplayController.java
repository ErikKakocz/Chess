/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import controller.Controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import pieces.Color;
import pieces.Type;
import table.Table;

/**
 *
 * @author User
 */
public class GameplayController implements ControllerInterfaces.GameplayControllerInterface {

    Table table;
    static int BLACKPAWNSTART = 1, WHITEPAWNSTART = 6, BLACKPAWNJUMP = 3, WHITEPAWNJUMP = 4;

    public GameplayController() {
        table = new Table();
        setupTable();

    }

    public final void setupTable() {
        JsonParser parser = new JsonParser();
        try {
            JsonArray array = parser.parse(new FileReader(getJsonFile("chesspieces.json"))).getAsJsonObject().get("pieces").getAsJsonArray();
            for (JsonElement element : array) {
                JsonObject obj = element.getAsJsonObject();
                table.setPiece(obj.get("Row").getAsInt(), obj.get("Column").getAsInt(),
                        pieces.Piece.pieceTranslator(obj.get("Type").getAsString()), pieces.Piece.colorTranslator(obj.get("Row").getAsString()));

            }

        } catch (JsonIOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } catch (JsonSyntaxException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }

    static File getJsonFile(String filename) {
        ClassLoader cl = Controller.class.getClassLoader();
        File file = new File(cl.getResource(filename).getFile());
        return file;
    }

    @Override
    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        if (validateMove(fromRow, fromCol, toRow, toCol) && notObstructed(fromRow, fromCol, toRow, toCol) && !(table
                .getPiece(toRow, toCol).getPieceColor().equals(table.getPiece(fromRow, fromCol).getPieceColor()))) {
            table.movePiece(fromRow, fromCol, toRow, toCol);
        }
    }

    boolean notObstructed(int fromRow, int fromCol, int toRow, int toCol) {
        if (table.getPiece(fromRow, fromCol).getPieceType().equals(Type.PAWN)
                && !pawnAttack(fromRow, fromCol, toRow, toCol)
                && !table.getPiece(toRow, toCol).getPieceType().equals(Type.NULLPIECE)) {
            return false;
        } else {
            int topLeftRow = Math.min(toRow, fromRow), topLeftCol = Math.min(toCol, fromCol),
                    botRightRow = Math.max(toRow, fromRow), botRightCol = Math.max(toCol, fromCol);
            if (table.getPiece(fromRow, fromCol).getPieceColor().equals(table.getPiece(toRow, toCol).getPieceColor())) {
                return false;
            }
            while ((topLeftRow != botRightRow - 1) || (topLeftCol != botRightCol - 1)) {
                if (topLeftRow != botRightRow - 1) {
                    topLeftRow++;
                }
                if (topLeftCol != botRightCol - 1) {
                    topLeftCol++;
                }
                if (!table.getPiece(topLeftRow, topLeftCol).getPieceType().equals(Type.NULLPIECE)) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean validateMove(int fromRow, int fromCol, int toRow, int toCol) {
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
                                 || ((Math.abs((fromRow + 1) - (toRow + 1)) == 1) 
                                 && (Math.abs((fromCol + 1) - (toCol + 1)) == 2)));
                break;
            }
            case BISHOP: {
                validPieceMove = diagonalMove(fromRow, fromCol, toRow, toCol);
                break;
            }
            case KING: {
                validPieceMove = (Math.abs((fromRow + 1) - (toRow + 1)) < 2 
                                 && Math.abs((fromCol + 1) - (toCol + 1)) < 2);
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

    boolean horizontalMove(int fromRow, int fromCol, int toRow, int toCol) {
        return (fromRow == toRow && fromCol != toCol);
    }

    boolean verticalMove(int fromRow, int fromCol, int toRow, int toCol) {
        return (fromRow != toRow && fromCol == toCol);
    }

    boolean diagonalMove(int fromRow, int fromCol, int toRow, int toCol) {
        return ((Math.abs((fromRow + 1) - (toRow + 1))) == (Math.abs((fromCol + 1) - (toCol + 1))));
    }

    boolean pawnJump(int fromRow, int fromCol, int toRow, int toCol) {
        return (fromRow == WHITEPAWNSTART && toRow == WHITEPAWNJUMP)
                || (fromRow == BLACKPAWNSTART && toRow == BLACKPAWNJUMP);
    }

    boolean pawnLeap(int fromRow, int fromCol, int toRow, int toCol) {
        if (table.getPiece(fromRow, fromCol).getPieceColor().equals(Color.WHITE)) {
            return (toRow == (fromRow - 1)) && (fromCol == toCol);
        } else {
            return (toRow == (fromRow + 1)) && (fromCol == toCol);
        }
    }

    boolean pawnAttack(int fromRow, int fromCol, int toRow, int toCol) {
        if (table.getPiece(fromRow, fromCol).getPieceColor().equals(Color.WHITE)) {
            return (toRow == (fromRow - 1)) && (Math.abs(fromCol - toCol) == 1)
                    && (table.getPiece(toRow, toCol).getPieceColor().equals(Color.BLACK));
        } else {
            return (toRow == (fromRow + 1)) && (Math.abs(fromCol - toCol) == 1)
                    && (table.getPiece(toRow, toCol).getPieceColor().equals(Color.WHITE));
        }
    }

    boolean pawnEnPassantAttack(int fromRow, int fromCol, int toRow, int toCol) {
        if (table.getPiece(fromRow, fromCol).getPieceColor().equals(Color.WHITE)) {
            return (fromRow == BLACKPAWNJUMP) && (table.getPiece(fromRow, toCol).isSpecialMove());
        } else {
            return (fromRow == WHITEPAWNJUMP) && (table.getPiece(fromRow, toCol).isSpecialMove());
        }
    }
}
