public class Square {
	public String background;
	public String name;
	private String alpha = "abcdefgh";

	public Square(boolean w, int x, int y){
		background = w ? "\033[47m" : "\033[40m";
		name = Character.toString(alpha.charAt(x))+(y+1);
		
	}
}
