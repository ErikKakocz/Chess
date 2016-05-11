package pieces;

public class Piece {

	Color pieceColor;
	Type pieceType;
	boolean specialMove;
	
	
	public Piece(){
		this.pieceColor = Color.NULLCOLOR;
		this.pieceType = Type.NULLPIECE;
	}
	
	public Piece(Color pieceColor, Type pieceType) {
		super();
		this.pieceColor = pieceColor;
		this.pieceType = pieceType;
	}

	public Color getPieceColor() {
		return pieceColor;
	}

	public void setPieceColor(Color pieceColor) {
		this.pieceColor = pieceColor;
	}

	public Type getPieceType() {
		return pieceType;
	}

	public void setPieceType(Type pieceType) {
		this.pieceType = pieceType;
	}

	@Override
	public String toString() {
		return pieceColor.toString()+" "+pieceType.toString();
	}

	public boolean isSpecialMove() {
		return specialMove;
	}

	public void setSpecialMove(boolean specialMove) {
		this.specialMove = specialMove;
	}
	
	
}
