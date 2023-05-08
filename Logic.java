import java.util.ArrayList;

public class Logic {
	private static int[] oldVals;
	public static boolean[] castleStatus = new boolean[]{true, true}; // white | black

	public static boolean checkMove(String move, boolean isWhite){

		//return true;
		/* */
		try{
			// Check for valid character sizes
			char[] chars = move.toCharArray();
			int denotedType = 0; // 0 - none / 1 - rank / 2 - file
			int file = -1, row = -1, denotedValue = -1;
			String pieceType = "";

			// CHECK: Validity of input
			switch (move.length()){
				case 2: // moving a pawn
					if (!Square.alpha.contains(Character.toString(chars[0])) || // File is invalid
						Integer.parseInt(Character.toString(chars[1])) >= 9 || // Row is greater than 8
						Integer.parseInt(Character.toString(chars[1])) <= 0 // Row is less than 1
					){
						return false;
					}

					pieceType = "P";
					file = Square.alpha.indexOf(Character.toString(chars[0]));
					row = 8 - Integer.parseInt(Character.toString(chars[1]));
					break;
				
				case 3: // moving a specific piece or castling
					if (move.equals("0-0")){ // is a castle
						return canCastle(false, isWhite);
					}

					if (Square.alpha.contains(Character.toString(chars[0])) && // Start File is valid
						Square.alpha.contains(Character.toString(chars[1])) && // move file is valid
						Integer.parseInt(Character.toString(chars[2])) <= 8 && // Row <= 8
						Integer.parseInt(Character.toString(chars[2])) >= 1 // Row >= 1
					){
						pieceType = "P";
						denotedType = 2;
						denotedValue = Square.alpha.indexOf(Character.toString(chars[0]));
						file = Square.alpha.indexOf(Character.toString(chars[1]));
						row = 8 - Integer.parseInt(Character.toString(chars[2]));
						break;
					}

					if (!Square.alpha.contains(Character.toString(chars[1])) || // File is invalid
					Integer.parseInt(Character.toString(chars[2])) >= 9 || // Row is greater than 8
					Integer.parseInt(Character.toString(chars[2])) <= 0 // Row is less than 1
					){
						return false;
					}

					pieceType = Character.toString(chars[0]);
					file = Square.alpha.indexOf(Character.toString(chars[1]));
					row = 8 - Integer.parseInt(Character.toString(chars[2]));
					break;
				
				case 4: // moving a specific piece, same file/rank
					if (Piece.pieceCodeValues.contains(Character.toString(chars[0])) && // pieceType is valid
						Square.alpha.contains(Character.toString(chars[1])) && // Start File is valid
						Square.alpha.contains(Character.toString(chars[2])) && // move file is valid
						Integer.parseInt(Character.toString(chars[3])) <= 8 && // Row <= 8
						Integer.parseInt(Character.toString(chars[3])) >= 1 // Row >= 1
					){
						pieceType = Character.toString(chars[0]);
						denotedType = 2;
						denotedValue = Square.alpha.indexOf(Character.toString(chars[0]));
						file = Square.alpha.indexOf(Character.toString(chars[1]));
						row = 8 - Integer.parseInt(Character.toString(chars[2]));
						break;
					}

					if (Piece.pieceCodeValues.contains(Character.toString(chars[0])) && // pieceType is valid
						Integer.parseInt(Character.toString(chars[1])) <= 8 && // Start Row <= 8
						Integer.parseInt(Character.toString(chars[1])) >= 1 &&// Start Row >= 1
						Square.alpha.contains(Character.toString(chars[2])) && // move file is valid
						Integer.parseInt(Character.toString(chars[3])) <= 8 && // Row <= 8
						Integer.parseInt(Character.toString(chars[3])) >= 1 // Row >= 1
					){
						pieceType = Character.toString(chars[0]);
						denotedType = 1;
						denotedValue = 8 - Integer.parseInt(Character.toString(chars[1]));
						file = Square.alpha.indexOf(Character.toString(chars[2]));
						row = 8 - Integer.parseInt(Character.toString(chars[3]));
						break;
					}

					return false; // if it gets here, conditions for either above did not work

				case 5:
					if (!move.equals("0-0-0")){ // isn't a long castle
						return false;
					}
					return canCastle(true, isWhite);
				
				default:
					return false;
			}

			// CHECK: validity of move
			int[] start = getValidStart(denotedType, denotedValue, file, row, pieceType, isWhite);

			if (start == null){
				return false;
			}

			// CHECK: self discovered mate TODO

	
			return false;
		} catch (Exception e){return false;}
		/* */
	}

	public static boolean checkForCMate(){ // TO DO
		return false;
	}

	public static int[] getOldVals(){
		oldVals = new int[]{0,0};
		return oldVals;
	}

	private static boolean canCastle(boolean longCastle, boolean isWhite){
		if ((isWhite && !castleStatus[0]) || (!isWhite && !castleStatus[1])){ // ultimate castle check
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

	private static int[] getValidStart(int denotedType, int denotedValue, int file, int row, String pieceType, boolean isWhite) {
		ArrayList<int[]> validStarts = new ArrayList<int[]>();
		Square moveToSquare = Board.currentBoard[row][file];
		Piece squarePiece = moveToSquare.piece;

		switch (pieceType){
			case "P":
				if (denotedType == 0){ // no denoted value
					if ((squarePiece.codeValue.equals("e") && 
						!Board.currentBoard[row+1][file].piece.isWhite &&
						Board.currentBoard[row+1][file].piece.enPassant)
						||
						(!squarePiece.codeValue.equals("e"))
					){ // en passant requirements
						validStarts.add(new int[]{row+1, file+1});
						validStarts.add(new int[]{row+1, file-1});
					}

					if (squarePiece.codeValue.equals("e")){ // piece can move forward
						validStarts.add(new int[]{row+1, file});
					}

					if (row == 4){ // piece can move 1 or 2 squares
						validStarts.add(new int[]{row+2, file});
					}
				}

				if (denotedType == 2){ // given file
					if ((squarePiece.codeValue.equals("e") && 
						!Board.currentBoard[row+1][file].piece.isWhite &&
						Board.currentBoard[row+1][file].piece.enPassant)
						||
						(!squarePiece.codeValue.equals("e"))
					){ // en passant requirements
						if (file+1 == denotedValue){
							validStarts.add(new int[]{row+1, file+1});

						} else {
							validStarts.add(new int[]{row+1, file-1});
						}
					}
				}

				// DUPE CHECK
				validStarts = checkForDupes(validStarts);
				break;
				
			case "R":
				validStarts = getSpecificValidStarts(1, denotedType, denotedValue, file, row, isWhite);
				break;
			
			case "N":
				int[][] modifiers = new int[][]{{-2, 1},{-2, -1}, {-1, 2},{-1, -2}, {1, 2},{1, -2}, {2, 1},{2, -1}};
				for (int[] modifier : modifiers){
					Square reference = Board.currentBoard[row+modifier[0]][file+modifier[1]];
					
					if (denotedType == 0 && (reference.piece.codeValue.equals("e") || reference.piece.codeValue.equals("N"))){
						validStarts.add(new int[]{row+modifier[0], file+modifier[1]});
					} else if (denotedType == 1 &&
							   (reference.piece.codeValue.equals("e") || reference.piece.codeValue.equals("N")) &&
						       denotedValue == row+modifier[0]
					){
						validStarts.add(new int[]{row+modifier[0], file+modifier[1]});
					} else if (denotedType == 2 &&
						       (reference.piece.codeValue.equals("e") || reference.piece.codeValue.equals("N")) && 
							   denotedValue == file+modifier[1]
				    ){
						validStarts.add(new int[]{row+modifier[0], file+modifier[1]});
					}
				}
			case "B":
				validStarts = getSpecificValidStarts(2, denotedType, denotedValue, file, row, isWhite);
				break;
			case "Q":
				validStarts = checkForDupes(getSpecificValidStarts(3, denotedType, denotedValue, file, row, isWhite));
				break;
			case "K":
				for (int rowMod = -1; rowMod <= 1; rowMod++){
					for (int fileMod = -1; fileMod <= 1; fileMod++){
						if (!(rowMod == 0 && fileMod == 0) &&
							(Board.currentBoard[row+rowMod][file+fileMod].piece.codeValue.equals("e") ||
							 Board.currentBoard[row+rowMod][file+fileMod].piece.codeValue.equals("K")
							) &&
							Board.currentBoard[row+rowMod][file+fileMod].piece.isWhite == isWhite
						){
							validStarts.add(new int[]{row+rowMod,file+fileMod});
						}
					}
				}
				break;
		}

		int[] validStart = validStarts.size() > 0 ? validStarts.get(0) : null;
		return validStart;
	}

	private static ArrayList<int[]> getSpecificValidStarts(int pieceType, int denotedType, int denotedValue, int file, int row, boolean isWhite){
		ArrayList<int[]> validStarts = new ArrayList<int[]>();
		if (pieceType == 1 || pieceType == 3){
			if (denotedType == 0 || (denotedValue == 2 && denotedValue == file)){
				for (int i = row+1; i <= 7; i++){
					if ((Board.currentBoard[i][file].piece.codeValue.equals("e") ||
						 Board.currentBoard[i][file].piece.codeValue.equals("R") ||
						 (Board.currentBoard[i][file].piece.codeValue.equals("Q") && pieceType == 3)
						) &&
						 Board.currentBoard[i][file].piece.isWhite == isWhite
					){ // empty square or is rook
						validStarts.add(new int[]{i, file});
					} else {
						break;
					}
				}

				for (int i = row-1; i >= 1; i--){
					if ((Board.currentBoard[i][file].piece.codeValue.equals("e") ||
					     Board.currentBoard[i][file].piece.codeValue.equals("R") ||
						 (Board.currentBoard[i][file].piece.codeValue.equals("Q") && pieceType == 3)
				        ) &&
					     Board.currentBoard[i][file].piece.isWhite == isWhite
			        ){ // empty square or is rook
						validStarts.add(new int[]{i, file});
					} else {
						break;
					}
				}
			}
			
			if (denotedType == 0 || (denotedType == 1 && denotedValue == row)){
				for (int i = file+1; i <= 7; i++){
					if ((Board.currentBoard[row][i].piece.codeValue.equals("e") ||
						 Board.currentBoard[row][i].piece.codeValue.equals("R") ||
						 (Board.currentBoard[i][file].piece.codeValue.equals("Q") && pieceType == 3)
						) &&
						 Board.currentBoard[row][i].piece.isWhite == isWhite
					){ // empty square or is rook
						validStarts.add(new int[]{row, i});
					} else {
						break;
					}
				}

				for (int i = file-1; i >= 1; i--){
					if ((Board.currentBoard[row][i].piece.codeValue.equals("e") ||
						 Board.currentBoard[row][i].piece.codeValue.equals("R") ||
						 (Board.currentBoard[i][file].piece.codeValue.equals("Q") && pieceType == 3)
				   		) &&
						 Board.currentBoard[row][i].piece.isWhite == isWhite
			   		){ // empty square or is rook
						validStarts.add(new int[]{row, i});
					} else {
						break;
					}
				}
			}
			
		} else if (pieceType == 2 || pieceType == 3){ // bishop
			int newFile = file, newRow = row;
			
			while (newFile < 7 && newRow < 7){ // diagonal down right
				newFile++; newRow++;
				if ((Board.currentBoard[newRow][newFile].piece.codeValue.equals("e") ||
					Board.currentBoard[newRow][newFile].piece.codeValue.equals("R") ||
					(Board.currentBoard[newRow][newFile].piece.codeValue.equals("Q") && pieceType == 3)
				  	) &&
					Board.currentBoard[newRow][newFile].piece.isWhite == isWhite
				){
					if (denotedType == 0){
						validStarts.add(new int[]{newRow, newFile});
					} else if (denotedType == 1 && newRow == denotedValue){
						validStarts.add(new int[]{newRow, newFile});
					} else if (denotedType == 2 && newFile == denotedValue){
						validStarts.add(new int[]{newRow, newFile});
					}
				}
			}

			newFile = file; newRow = row;

			while (newFile < 7 && newRow > 0){ // diagonal up right
				newFile++; newRow--;
				if ((Board.currentBoard[newRow][newFile].piece.codeValue.equals("e") ||
					Board.currentBoard[newRow][newFile].piece.codeValue.equals("R") ||
					(Board.currentBoard[newRow][newFile].piece.codeValue.equals("Q") && pieceType == 3)
				  	) &&
					Board.currentBoard[newRow][newFile].piece.isWhite == isWhite
				){
					if (denotedType == 0){
						validStarts.add(new int[]{newRow, newFile});
					} else if (denotedType == 1 && newRow == denotedValue){
						validStarts.add(new int[]{newRow, newFile});
					} else if (denotedType == 2 && newFile == denotedValue){
						validStarts.add(new int[]{newRow, newFile});
					}
				}
			}

			newFile = file; newRow = row;

			while (newFile > 0 && newRow < 7){ // diagonal down left
				newFile--; newRow++;
				if ((Board.currentBoard[newRow][newFile].piece.codeValue.equals("e") ||
					Board.currentBoard[newRow][newFile].piece.codeValue.equals("R") ||
					(Board.currentBoard[newRow][newFile].piece.codeValue.equals("Q") && pieceType == 3)
				  	) &&
					Board.currentBoard[newRow][newFile].piece.isWhite == isWhite
				){
					if (denotedType == 0){
						validStarts.add(new int[]{newRow, newFile});
					} else if (denotedType == 1 && newRow == denotedValue){
						validStarts.add(new int[]{newRow, newFile});
					} else if (denotedType == 2 && newFile == denotedValue){
						validStarts.add(new int[]{newRow, newFile});
					}
				}
			}

			newFile = file; newRow = row;

			while (newFile > 0 && newRow > 0){ // diagonal up left
				newFile--; newRow--;
				if ((Board.currentBoard[newRow][newFile].piece.codeValue.equals("e") ||
					Board.currentBoard[newRow][newFile].piece.codeValue.equals("R") ||
					(Board.currentBoard[newRow][newFile].piece.codeValue.equals("Q") && pieceType == 3)
				  	) &&
					Board.currentBoard[newRow][newFile].piece.isWhite == isWhite
				){
					if (denotedType == 0){
						validStarts.add(new int[]{newRow, newFile});
					} else if (denotedType == 1 && newRow == denotedValue){
						validStarts.add(new int[]{newRow, newFile});
					} else if (denotedType == 2 && newFile == denotedValue){
						validStarts.add(new int[]{newRow, newFile});
					}
				}
			}
		}
		return validStarts;
	}

	private static ArrayList<int[]> checkForDupes(ArrayList<int[]> original){
		ArrayList<int[]> cleaned = new ArrayList<int[]>();

		for (int[] point : original){
			boolean matched = false;
			for (int i = 0; i < cleaned.size(); i++){
				if (cleaned.get(i)[0] == point[0] && cleaned.get(i)[1] == point[1]){
					matched = true;
					break;
				}
			}

			if (!matched){
				cleaned.add(point);
			}
		}

		return cleaned;
	}
}

