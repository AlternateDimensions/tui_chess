public class Piece extends GenericChessObject {
	public static String pieceDisplayValues = "♖♘♗♕♔♙♜♞♝♛♚♟︎";
	public static String pieceCodeValues = "RNBQKP";
	public String displayValue;
	public String codeValue;
	private boolean isWhite;

	public Piece(String code, String sideCode){
		codeValue = code;
		isWhite = sideCode.equals("w");
		if (!code.equals("e")){
			int index = isWhite ? 0 : 6;
			displayValue = " "+Character.toString(pieceDisplayValues.charAt(pieceCodeValues.indexOf(code)) + index)+" ";
		} else {
			displayValue = "   ";
		}
	}

	@Override
	public void delete(){
		displayValue = "";
		codeValue = "";
		isWhite = false;
	}
	
}
