import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Temp {

	public static void main(String[] args) throws Exception {
		ProcessBuilder builder = new ProcessBuilder("cmd.exe");
		builder.start();

		Square[][] board = new Square[8][8];
		for(int y = 0; y < board.length; y++){
			boolean w = y%2==0 ? true : false;
			for (int x = 0; x < board[y].length; x++){
				board[x][y] = new Square(w, y, Math.abs(x-7));
				w = !w;
				
			}
		}

		for (Square[] row : board){
			for (Square col : row){
				System.out.print(col.background+"["+col.name+"]"+Colors.CLEAR);
			}
			System.out.print("\n");
		}


		//----
        Runtime runtime = Runtime.getRuntime();
		runtime.exec("echo stty");



		Thread.sleep(100000);
	}
	
}
