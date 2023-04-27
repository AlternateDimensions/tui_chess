public class Square extends GenericChessObject {
	public String background;
	public Piece piece;
	public static String alpha = "abcdefgh";

	public Square(boolean w, Piece p){
		background = w ? "\033[47m" : "\033[44m";
		piece = p;
	}

	@Override
	public void delete(){
		background = "\033[0m";
		piece = null;
	}
}