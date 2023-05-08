import java.util.ArrayList;

public class Piece extends GenericChessObject {
	public static String pieceDisplayValues = "♖♘♗♕♔♙♜♞♝♛♚♟︎";
	public static String pieceCodeValues = "RNBQKP";
	public ArrayList<int[]> captureRange = new ArrayList<int[]>();
	public String displayValue;
	public String codeValue;
	public boolean isWhite;
	public boolean enPassant;

	public Piece(String code, String sideCode){
		enPassant = false;
		codeValue = code;
		isWhite = sideCode.equals("w");
		if (!code.equals("e")){
			int index = isWhite ? 0 : 6;
			displayValue = " "+Character.toString(pieceDisplayValues.charAt(pieceCodeValues.indexOf(code)) + index)+" ";
		} else {
			displayValue = "   ";
		}
	}

	public void getCaptureRange(){
		switch(codeValue){
			
		}
	}

	@Override
	public void delete(){
		displayValue = "";
		codeValue = "";
		isWhite = false;
	}
	
}
