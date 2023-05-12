import java.util.ArrayList;

public class Piece extends GenericChessObject {
	private static String pieceDisplayValues = "♖♘♗♕♔♙♜♞♝♛♚♟︎";
	public static String pieceCodeValues = "RNBQKP";
	public ArrayList<int[]> captureRange = new ArrayList<int[]>();
	public int[] index;
	public String displayValue;
	public String codeValue;
	public boolean isWhite;
	public boolean enPassant;
	public boolean castleable;

	public Piece(int[] i, String p){
		super(p);
		String sideCode = p.substring(0, 1), codeValue = p.substring(1);

		index = i;

		int modifier = sideCode.equals("w") ? 6:0;

		displayValue = sideCode.equals("e")? "   ":" "+pieceDisplayValues.substring(pieceCodeValues.indexOf(codeValue)+modifier, pieceCodeValues.indexOf(codeValue)+modifier+1)+" ";

		castleable = codeValue.equals(pieceCodeValues.substring(0, 1));

		enPassant = false;

		isWhite = sideCode.equals("w");
	}

	public void updateCaptureRange() {
		captureRange = new ArrayList<int[]>();
		switch (pieceCodeValues.indexOf(codeValue)){
			case 0: // rook
				for (int r = index[0]+1; r <= 7; r++){
					if (!Board.currentBoard[r][index[1]].piece.codeValue.equals("e")){
						break;
					}
					try{captureRange.add(Board.currentBoard[r][index[1]].index);} catch (Exception ignore){}
				}

				for (int r = index[0]-1; r >= 0; r--){
					if (!Board.currentBoard[r][index[1]].piece.codeValue.equals("e")){
						break;
					}
					try{captureRange.add(Board.currentBoard[r][index[1]].index);} catch (Exception ignore){}
				}

				for (int f = index[1]+1; f <= 7; f++){
					if (!Board.currentBoard[index[0]][f].piece.codeValue.equals("e")){
						break;
					}
					try{captureRange.add(Board.currentBoard[index[0]][f].index);} catch (Exception ignore){}
				}

				for (int f = index[1]-1; f >= 0; f--){
					if (!Board.currentBoard[index[0]][f].piece.codeValue.equals("e")){
						break;
					}
					try{captureRange.add(Board.currentBoard[index[0]][f].index);} catch (Exception ignore){}
				}

				break;

			case 1: // Knight
				int[][] modifiers = new int[][]{{-2, -1}, {-1, -2}, {-2, 1}, {-1, 2}, {2, -1}, {1, -2}, {2, 1}, {1, 2}};
				for (int[] modifier : modifiers){
					try{captureRange.add(Board.currentBoard[index[0]+modifier[0]][index[1]+modifier[1]].index);} catch (Exception ignore){}
				}

				break;
	
			case 2: // Bishop
				for (int modifier = 1; index[0] + modifier <= 7 && index[1] + modifier <= 7; modifier++){ // down right
					try{captureRange.add(Board.currentBoard[index[0] + modifier][index[1] + modifier].index);} catch (Exception ignore){break;}
				}

				for (int modifier = 1; index[0] + modifier <= 7 && index[1] + (modifier*-1) >= 0; modifier++){ // down left
					try{captureRange.add(Board.currentBoard[index[0] + modifier][index[1] + (modifier*-1)].index);} catch (Exception ignore){break;}
				}

				for (int modifier = 1; index[0] + (modifier*-1) >= 0 && index[1] + modifier <= 7; modifier++){ // up right
					try{captureRange.add(Board.currentBoard[index[0] + (modifier*-1)][index[1] + modifier].index);} catch (Exception ignore){break;}
				}

				for (int modifier = 1; index[0] + (modifier*-1) >= 0 && index[1] + (modifier*-1) >= 0; modifier++){ // up left
					try{captureRange.add(Board.currentBoard[index[0] + (modifier*-1)][index[1] + (modifier*-1)].index);} catch (Exception ignore){break;}
				}

				break;

			case 3:// Queen
				for (int r = index[0]+1; r <= 7; r++){
					if (!Board.currentBoard[r][index[1]].piece.codeValue.equals("e")){
						break;
					}
					try{captureRange.add(Board.currentBoard[r][index[1]].index);} catch (Exception ignore){}
				}

				for (int r = index[0]-1; r >= 0; r--){
					if (!Board.currentBoard[r][index[1]].piece.codeValue.equals("e")){
						break;
					}
					try{captureRange.add(Board.currentBoard[r][index[1]].index);} catch (Exception ignore){}
				}

				for (int f = index[1]+1; f <= 7; f++){
					if (!Board.currentBoard[index[0]][f].piece.codeValue.equals("e")){
						break;
					}
					try{captureRange.add(Board.currentBoard[index[0]][f].index);} catch (Exception ignore){}
				}

				for (int f = index[1]-1; f >= 0; f--){
					if (!Board.currentBoard[index[0]][f].piece.codeValue.equals("e")){
						break;
					}
					try{captureRange.add(Board.currentBoard[index[0]][f].index);} catch (Exception ignore){}
				}

				for (int modifier = 1; index[0] + modifier <= 7 && index[1] + modifier <= 7; modifier++){ // down right
					try{captureRange.add(Board.currentBoard[index[0] + modifier][index[1] + modifier].index);} catch (Exception ignore){break;}
				}

				for (int modifier = 1; index[0] + modifier <= 7 && index[1] + (modifier*-1) >= 0; modifier++){ // down left
					try{captureRange.add(Board.currentBoard[index[0] + modifier][index[1] + (modifier*-1)].index);} catch (Exception ignore){break;}
				}

				for (int modifier = 1; index[0] + (modifier*-1) >= 0 && index[1] + modifier <= 7; modifier++){ // up right
					try{captureRange.add(Board.currentBoard[index[0] + (modifier*-1)][index[1] + modifier].index);} catch (Exception ignore){break;}
				}

				for (int modifier = 1; index[0] + (modifier*-1) >= 0 && index[1] + (modifier*-1) >= 0; modifier++){ // up left
					try{captureRange.add(Board.currentBoard[index[0] + (modifier*-1)][index[1] + (modifier*-1)].index);} catch (Exception ignore){break;}
				}

				break;

			case 4: // King
				for (int rowMod = -1; rowMod <= 1; rowMod++){
					for (int fileMod = -1; fileMod <= 1; fileMod++){
						if (rowMod != 0 || fileMod != 0){
							try{captureRange.add(Board.currentBoard[index[0]+rowMod][index[1]+fileMod].index);} catch (Exception ignore){}
						}
					}
				}

				break;

			case 5: // pawn
				if (isWhite){
					captureRange.add(new int[]{index[0]-1, index[1]-1});
					captureRange.add(new int[]{index[0]-1, index[1]+1});
				} else {
					captureRange.add(new int[]{index[0]+1, index[1]-1});
					captureRange.add(new int[]{index[0], index[1]+1});
				}
				
				break;
		}
	}

	@Override
	public void delete(){
		displayValue = "";
		codeValue = "";
		isWhite = false;
	}
}
