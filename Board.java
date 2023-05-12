public class Board extends GenericChessObject {
	public static Square[][] currentBoard;
	public static Square[][] futureBoard;

	public Board(){ // DO NOT USE
		super("Board");
	}

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
				currentBoard[x][y] = new Square(new int[]{x, y}, w, pieceOrientation[x][y]);
				w = !w;	
			}
		}
	}

	public static void updateBoard(int oldRow, int oldCol, int newRow, int newCol, boolean isWhite) throws Exception{
		currentBoard[newRow][newCol].piece = new Piece(currentBoard[oldRow][oldCol].piece.index, currentBoard[oldRow][oldCol].piece.codeValue);
		currentBoard[oldRow][oldCol].piece = new Piece(new int[]{oldRow, oldCol}, "ee");
		
		for (Square[] row : currentBoard){
			for (Square sq : row){
				sq.piece.updateCaptureRange();
			}
		}
	}

	public static void castle(boolean isWhite, boolean longCastle){
		int row = isWhite? 7:0;
		int file = longCastle? 2:6;
		int mod = longCastle? 1:-1;
		String sideCode = isWhite? "w":"b";

		currentBoard[row][file].piece = new Piece(new int[]{row, file}, sideCode+"K");
		currentBoard[row][file+mod].piece = new Piece(new int[]{row, file+mod}, sideCode+"R");
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
			System.out.print(" "+Character.toString(Square.fileChars.charAt(i))+" ");
		}

		System.out.print("\n");
	}	
}