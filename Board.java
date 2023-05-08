public class Board extends GenericChessObject {
	public static Square[][] currentBoard;
	public static Square[][] futureBoard;

	public static void genBoard() throws Exception {
		String[][] pieces = new String[][]{
			{"bR","bN","bB","bQ","bK","bB","bN","bR"},
			{"bP","bP","bP","bP","bP","bP","bP","bP"},
			{"ee","ee","ee","ee","ee","ee","ee","ee"},
			{"ee","ee","ee","ee","ee","ee","ee","ee"},
			{"ee","ee","ee","ee","ee","ee","ee","ee"},
			{"ee","ee","ee","ee","ee","ee","ee","ee"},	
			{"wP","wP","wP","wP","wP","wP","wP","wP"},		
			{"wR","wN","wB","wQ","wK","wB","wN","wR"},
		};
		setBoard(pieces);
	}

	public static void setBoard(String[][] pieceOrientation) throws Exception {
		currentBoard = new Square[8][8];
		for(int y = 0; y < currentBoard.length; y++){
			boolean w = y%2==0 ? true : false; // generates checkered board pattern
			for (int x = 0; x < currentBoard[y].length; x++){
				String code = pieceOrientation[x][y].substring(1), sideCode = pieceOrientation[x][y].substring(0,1);
				currentBoard[x][y] = new Square(w, new Piece(code, sideCode));
				w = !w;	
			}
		}
	}

	public static void updateBoard(int oldRow, int oldCol, String move, boolean isWhite) throws Exception{
		currentBoard[oldRow][oldCol].piece = new Piece("e", "e");
		if (Character.isAlphabetic(move.charAt(1))){ // not a pawn!
			int col = Square.alpha.indexOf(move.substring(1,2).toLowerCase());
			int row = (8-Integer.parseInt(move.substring(2)));
			String code = move.substring(0, 1), sideCode = isWhite ? "w" : "b";
			currentBoard[row][col].piece = new Piece(code, sideCode);
		} else { // is a pawn!
			int col = Square.alpha.indexOf(move.substring(0,1).toLowerCase());
			int row = (8-Integer.parseInt(move.substring(1)));
			String code = "P", sideCode = isWhite ? "w" : "b";
			currentBoard[row][col].piece = new Piece(code, sideCode);
		}
	}

	@Override
	public void delete(){
		currentBoard = new Square[0][0];
	}


	public static void printBoard(){
		for (int i = 0; i < currentBoard.length; i++){
			System.out.print((8-i)+" ");
			for (int j = 0; j < currentBoard[i].length; j++){
				System.out.print(currentBoard[i][j].background+currentBoard[i][j].piece.displayValue+Colors.CLEAR);
			}
			System.out.print("\n");
		}
		
		System.out.print("  ");

		for (int i = 0; i < 8; i++){
			System.out.print(" "+Character.toString(Square.alpha.charAt(i))+" ");
		}

		System.out.print("\n");
	}	
}