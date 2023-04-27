public class Board extends GenericChessObject {
	public static Square[][] currentBoard;

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

	public static void updateBoard(String move) throws Exception{
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