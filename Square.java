public class Square extends GenericChessObject {
	public String background;
	public int[] index;
	public Piece piece;
	public static String fileChars = "abcdefgh";

	public Square(int[] i, boolean w, String p){
		super(i[0]+","+i[1]);
		background = w ? "\033[47m" : "\033[44m";
		index = i;
		piece = new Piece(index, p);
	}

	@Override
	public void delete(){
		background = "\033[0m";
		piece = null;
	}
}