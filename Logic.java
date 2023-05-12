import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.Set;
import java.util.HashSet; 

public class Logic {
    public static int[] oldVals, newVals;
    public static boolean[] castleStatusLong = new boolean[]{true, true}; // white | black
	public static boolean[] castleStatusShort = new boolean[]{true, true}; // white | black

    private static int NONE = 0;
    private static int RANK = 1;
    private static int FILE = 2;


    public static boolean checkMove(String move, boolean isWhite){
        try {
            // CHECK: Character validity
            char[] chars = move.toCharArray();
            int denotedType = NONE, file = NONE, row = NONE, denotedValue = NONE, pieceType = NONE;

            switch (move.length()){
                case 2: // Moving a pawn
                    if (!Square.fileChars.contains(Character.toString(chars[0])) || // file is invalid
                        Character.getNumericValue(chars[1]) >= 9 || // Row is greater than 8
                        Character.getNumericValue(chars[1]) <= 0 // Row is less than 1
                    ){
                        return false;
                    }

                    pieceType = Piece.pieceCodeValues.indexOf("P");
                    row = 8 - Character.getNumericValue(chars[1]);
                    file = Square.fileChars.indexOf(chars[0]);

                    break;
                
                case 3: // Moving a specific pawn, moving a non-pawn, or castling
                    if (move.equals("0-0")){
                        return canCastle(false, isWhite);
                    }
 
                    if (Square.fileChars.contains(Character.toString(chars[0])) && // Start file is valid
                        Square.fileChars.contains(Character.toString(chars[1])) && // move file is good
                        Character.getNumericValue(chars[1]) <= 8 &&
                        Character.getNumericValue(chars[1]) >= 1 // Row is between 1-8
                    ){
                        pieceType = Piece.pieceCodeValues.indexOf("P");
                        row = 8 - Character.getNumericValue(chars[2]);
                        file = Square.fileChars.indexOf(chars[1]);

                        denotedType = FILE;
                        denotedValue = Square.fileChars.indexOf(chars[0]);
                        break;
                    }

                    if (!Piece.pieceCodeValues.contains(String.valueOf(chars[0])) || // pieceType is valid
                    !Square.fileChars.contains(Character.toString(chars[1])) || // move file is invalid
                    Character.getNumericValue(chars[2]) >= 9 || // Row is greater than 8
                    Character.getNumericValue(chars[2]) <= 0 // Row is less than 1
                    ){
                       return false;
                    }

                    pieceType = Piece.pieceCodeValues.indexOf(chars[0]);
                    row = 8 - Character.getNumericValue(chars[2]);
                    file = Square.fileChars.indexOf(chars[1]);  
                    break;

                
                case 4: // Moving a specifc non-pawn
                    if (Square.fileChars.contains(String.valueOf(chars[1]))){ // Valid start file
                        denotedType = FILE;
                        denotedValue = Square.fileChars.indexOf(chars[1]);
                    } else if (Character.getNumericValue(chars[1]) <= 8 && Character.getNumericValue(chars[1]) >= 1){ // valid start rank
                        denotedType = RANK;
                        denotedValue = 8 - Character.getNumericValue(chars[1]);
                    }

                    if (!Piece.pieceCodeValues.contains(String.valueOf(chars[0])) || // pieceType is valid
                    !Square.fileChars.contains(Character.toString(chars[2])) || // move file is invalid
                    Character.getNumericValue(chars[3]) >= 9 || // Row is greater than 8
                    Character.getNumericValue(chars[3]) <= 0 // Row is less than 1
                    ){
                       return false;
                    }

                    pieceType = Piece.pieceCodeValues.indexOf(chars[0]);
                    row = 8 - Character.getNumericValue(chars[3]);
                    file = Square.fileChars.indexOf(chars[2]);  

                    break;
                case 5: // long castle
                if (move.equals("0-0")){
                    return canCastle(true, isWhite);
                }
                return false;
            }

            // CHECK: get the valid start
            int[] validStart = getValidStart(denotedType, denotedValue, file, row, pieceType, isWhite);

            if (validStart == null){
                return false;
            }

            // CHECK: move results in check
            /* Make move on futureBoard */
            if (checkForCheck(isWhite, true)){
                return false;
            }
            
            oldVals = validStart;
            newVals = new int[]{file, row};
            return true;

        } catch (Exception e){return false;}
    }

    private static boolean canCastle(boolean longCastle, boolean isWhite){
        if (checkForCheck(isWhite, false)){
            return false;
        }

        int rowInvalid = isWhite? 7:0;
		int[] filesInvalid = longCastle? new int[]{1, 2, 3, 4}: new int[]{4, 5, 6};

        for (Square[] row : Board.currentBoard){
			for (Square sq : row){
				if (sq.piece.isWhite != isWhite){
					for (int[] coordinate : sq.piece.captureRange){
						if (coordinate[0] == rowInvalid && IntStream.of(filesInvalid).anyMatch(x -> x == coordinate[1])){
                            return false;
                        }
					}
				}
			}
		}

        if ((longCastle && ((isWhite && !castleStatusLong[0]) || (!isWhite && !castleStatusLong[1]))) || 
			(!longCastle && ((isWhite && !castleStatusShort[0]) || (!isWhite && !castleStatusShort[1])))	
		){ // ultimate castle check - if you move the king or rook, it's over
			return false;
		}
        if (isWhite && longCastle){ // long castle white
			return (Board.currentBoard[7][0].piece.codeValue.equals("R") &&
					Board.currentBoard[7][1].piece.codeValue.equals("e") &&
					Board.currentBoard[7][2].piece.codeValue.equals("e") &&
					Board.currentBoard[7][3].piece.codeValue.equals("e") &&
					Board.currentBoard[7][4].piece.codeValue.equals("K") 
					);
		}

		if (isWhite){ // short castle white
			return (Board.currentBoard[7][4].piece.codeValue.equals("K") &&
					Board.currentBoard[7][5].piece.codeValue.equals("e") &&
					Board.currentBoard[7][6].piece.codeValue.equals("e") &&
					Board.currentBoard[7][7].piece.codeValue.equals("R")
					);
		}
		
		if (longCastle){ // long castle black
			return (Board.currentBoard[0][0].piece.codeValue.equals("R") &&
					Board.currentBoard[0][1].piece.codeValue.equals("e") &&
					Board.currentBoard[0][2].piece.codeValue.equals("e") &&
					Board.currentBoard[0][3].piece.codeValue.equals("e") &&
					Board.currentBoard[0][4].piece.codeValue.equals("K") 
					);
		}

		// short castle black
		return (Board.currentBoard[0][4].piece.codeValue.equals("K") &&
					Board.currentBoard[0][5].piece.codeValue.equals("e") &&
					Board.currentBoard[0][6].piece.codeValue.equals("e") &&
					Board.currentBoard[0][7].piece.codeValue.equals("R")
				);
    }

    private static boolean checkForCheck(boolean isWhite, boolean futureBoard){
        return false;
    }

    public static boolean checkForMate(boolean isWhite){
        // TEMP Looks for king capture
        for (Square[] row : Board.currentBoard){
            for (Square sq : row){
                if (sq.piece.codeValue.equals("K") && sq.piece.isWhite == isWhite){
                    return false;
                }
            }
        }
        return true;
    }

    public static int[] getValidStart(int denotedType, int denotedValue, int file, int row, int pieceType, boolean isWhite){
        ArrayList<int[]> validStarts = new ArrayList<int[]>();
        Square moveToSquare = Board.currentBoard[row][file];
        Piece squarePiece = moveToSquare.piece;

        switch (pieceType){
            case 5: // Pawn
                if ((Board.currentBoard[row+1][file].piece.isWhite != isWhite && // piece under is opponent
                    Board.currentBoard[row+1][file].piece.enPassant) // piece can be en passant'd
                    ||
                    (!squarePiece.codeValue.equals("e") && // piece occupies space
                    squarePiece.isWhite != isWhite // piece is opponent
                    ) 
                ){  // capture or en passant rule passes
                    if (Board.currentBoard[row+1][file+1].piece.codeValue.equals("P")){
                        validStarts.add(new int[]{row+1, file+1});

                    }
                    if (Board.currentBoard[row+1][file-1].piece.codeValue.equals("P")){
                        validStarts.add(new int[]{row+1, file-1});

                    }
                }
                
                break;
            
            case 4: // King
                for (int rowMod = -1; rowMod <= 1; rowMod++){
                    for (int fileMod = -1; fileMod <= 1; fileMod++){
                        if ((rowMod != 0 || fileMod != 0) && Board.currentBoard[row+rowMod][file+fileMod].piece.codeValue.equals("K")){
                            validStarts.add(new int[]{row+rowMod, file+fileMod});
                            break;
                        }
                    }
                }
            
            case 3: // Queen
                for (int modifier = 1; row + modifier <= 7 && file + modifier <= 7; modifier++){ // down right
                    if (Board.currentBoard[row+modifier][file+modifier].piece.codeValue.equals("Q")){
                        validStarts.add(new int[]{row+modifier, file+modifier});
                    }
                }

                for (int modifier = 1; row + modifier <= 7 && file + (modifier*-1) >= 0; modifier++){ // down left
                    if (Board.currentBoard[row+modifier][file+(modifier*-1)].piece.codeValue.equals("Q")){
                        validStarts.add(new int[]{row+modifier, file+(modifier*-1)});
                    }
                }

                for (int modifier = 1; row + (modifier*-1) >= 0 && file + modifier <= 7; modifier++){ // up right
                    if (Board.currentBoard[row+(modifier*-1)][file+modifier].piece.codeValue.equals("Q")){
                        validStarts.add(new int[]{row+(modifier*-1), file+modifier});
                    }
                }

                for (int modifier = 1; row + (modifier*-1) >= 0 && file + (modifier*-1) >= 0; modifier++){ // up left
                    if (Board.currentBoard[row+(modifier*-1)][file+(modifier*-1)].piece.codeValue.equals("Q")){
                        validStarts.add(new int[]{row+(modifier*-1), file+(modifier*-1)});
                    }
                }

                for (int r = row+1; r <= 7; r++){
                    if (Board.currentBoard[r][file].piece.codeValue.equals("Q")){
                        validStarts.add(new int[]{r, file});
                    }
                }

                for (int r = row-1; r >= 0; r--){
                    if (Board.currentBoard[r][file].piece.codeValue.equals("Q")){
                        validStarts.add(new int[]{r, file});
                    }
                }

                for (int f = file+1; f <= 7; f++){
                    if (Board.currentBoard[row][f].piece.codeValue.equals("Q")){
                        validStarts.add(new int[]{row, f});
                    }
                }

                for (int f = file-1; f >= 0; f--){
                    if (Board.currentBoard[row][f].piece.codeValue.equals("Q")){
                        validStarts.add(new int[]{row, f});
                    }
                }
                
                break;

            case 2: // Bishop
                for (int modifier = 1; row + modifier <= 7 && file + modifier <= 7; modifier++){ // down right
                    if (Board.currentBoard[row+modifier][file+modifier].piece.codeValue.equals("B")){
                        validStarts.add(new int[]{row+modifier, file+modifier});
                    }
                }

                for (int modifier = 1; row + modifier <= 7 && file + (modifier*-1) >= 0; modifier++){ // down left
                    if (Board.currentBoard[row+modifier][file+(modifier*-1)].piece.codeValue.equals("B")){
                        validStarts.add(new int[]{row+modifier, file+(modifier*-1)});
                    }
                }

                for (int modifier = 1; row + (modifier*-1) >= 0 && file + modifier <= 7; modifier++){ // up right
                    if (Board.currentBoard[row+(modifier*-1)][file+modifier].piece.codeValue.equals("B")){
                        validStarts.add(new int[]{row+(modifier*-1), file+modifier});
                    }
                }

                for (int modifier = 1; row + (modifier*-1) >= 0 && file + (modifier*-1) >= 0; modifier++){ // up left
                    if (Board.currentBoard[row+(modifier*-1)][file+(modifier*-1)].piece.codeValue.equals("B")){
                        validStarts.add(new int[]{row+(modifier*-1), file+(modifier*-1)});
                    }
                }

                break;

            case 1: // Knight
                int[][] modifiers = new int[][]{{-2, -1}, {-1, -2}, {-2, 1}, {-1, 2}, {2, -1}, {1, -2}, {2, 1}, {1, 2}};
				for (int[] modifier : modifiers){
                    if (Board.currentBoard[row+modifier[0]][file+modifier[1]].piece.codeValue.equals("N")){
                        validStarts.add(new int[]{row+modifier[0], file+modifier[1]});
                    }
				}

				break;

            case 0: // rook
                for (int r = row+1; r <= 7; r++){
                    if (Board.currentBoard[r][file].piece.codeValue.equals("R")){
                        validStarts.add(new int[]{r, file});
                    }
                }

                for (int r = row-1; r >= 0; r--){
                    if (Board.currentBoard[r][file].piece.codeValue.equals("R")){
                        validStarts.add(new int[]{r, file});
                    }
                }

                for (int f = file+1; f <= 7; f++){
                    if (Board.currentBoard[row][f].piece.codeValue.equals("R")){
                        validStarts.add(new int[]{row, f});
                    }
                }

                for (int f = file-1; f >= 0; f--){
                    if (Board.currentBoard[row][f].piece.codeValue.equals("R")){
                        validStarts.add(new int[]{row, f});
                    }
                }

                break;
        }
        Set<int[]> set = new HashSet<int[]>(validStarts);
        if (set.size() > 1){
            return null;
        }
        return new ArrayList<int[]>(set).get(0);

    }
}
