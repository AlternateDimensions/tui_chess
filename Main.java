import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main {
	private static boolean chatGPT = false;
	private static int timer = 5;
	private static Object[] settingVals = {chatGPT, timer, ""};
	private static Scanner in;
	private static Random random = new Random();

	public static void main (String[] args) throws Exception {
		String[] options = {"PLAY", "OPTIONS", "QUIT"};

		Util.lock();
		Util.clear();
		Title.printTitle();
		
		boolean running = true, repeat = false;

		while (running){
			if (repeat){
				for (int i = 1; i <= options.length; i++){
					System.out.println(Colors.BOLD + Colors.RANDOM() + "{" + i + "} " + options[i-1]);
				}
			} else {
				for (int i = 1; i <= options.length; i++){
					Util.typewriter(Colors.BOLD + Colors.RANDOM() + "{" + i + "} " + options[i-1], 0.01, true, true);
				}

			}
			repeat = true;
			Util.divider(false, true);
			
			System.out.print(Colors.RANDOM() + Colors.BOLD + "Select Option >>> | " + Colors.UNDERLINE);

			in = new Scanner(System.in);
			while(System.in.available() > 0) {
				System.in.read(new byte[System.in.available()]);
			}

			Util.unlock();
			String input = in.nextLine().toLowerCase();
			Util.lock();

			switch (input){
				case "play": case "1": case "p":
					play();
					break;
				
				case "2": case "o": case "options":
					options(in);
					break;

				case "3": case "exit": case "quit": case "q": case "x":
					running = false;
					break;

				default:
					System.out.println(Colors.RED + "[!!] Not a valid option! [!!]");
					Util.wait(3.0);
			}

			Util.clear();
		}
		in.close();
	}

	private static void play() throws Exception {
		if (chatGPT){
			boolean whiteIsPlayer = random.nextBoolean(), checkmate = false;
			ArrayList<int[]> chatGPTPieces;

			Board.genBoard();
			
			while (!checkmate){
				boolean whiteToMove = true;
				// White to move
				if (whiteIsPlayer){
					while (whiteToMove){
						Util.clear();
						Board.printBoard();

						in = new Scanner(System.in);
						while(System.in.available() > 0) {
							System.in.read(new byte[System.in.available()]);
						}

						Util.unlock();
						System.out.print(Colors.WHITE+Colors.BOLD+"White to Move: "+Colors.CLEAR+Colors.UNDERLINE+Colors.RANDOM()+Colors.BOLD);
						String move = in.nextLine().replace("x", "");
						Util.lock();
						if (Logic.checkMove(move, whiteToMove)){
							int[] oldVals = Logic.getOldVals();
							Board.updateBoard(oldVals[0], oldVals[1], move, whiteToMove);
							whiteToMove = false;
						} else {
							System.out.println(Colors.CLEAR+Colors.RED+Colors.BOLD+"This is an invalid move!"+Colors.CLEAR);
							Util.wait(3.0);
						}
					}
				} else {
					boolean useCurrentPieces = random.nextBoolean();
					String code = "";
			
					if (useCurrentPieces){
						chatGPTPieces = new ArrayList<int[]>();
						for (int i = 0; i < 8; i++){
							for (int j = 0; j < 8; j++){
								if (Board.currentBoard[i][j].piece.isWhite){
									chatGPTPieces.add(new int[]{i, j});
								}
							}
						}
						int index = (int) (Math.random()*chatGPTPieces.size());
						code = Board.currentBoard[chatGPTPieces.get(index)[0]][chatGPTPieces.get(index)[1]].piece.codeValue;
						Board.currentBoard[chatGPTPieces.get(index)[0]][chatGPTPieces.get(index)[1]].piece = new Piece("e", "e");
						
					} else {
						int randex = (int) (Math.random()*Piece.pieceCodeValues.length())+1;
						code = Piece.pieceCodeValues.substring(randex-1, randex);
					}

					int randRow = (int) (Math.random()*8), randCol = (int) (Math.random()*8);
					Board.currentBoard[randRow][randCol].piece = new Piece(code, "w");
				}

				checkmate = Logic.checkForMate();

				// Black to move
				if (whiteIsPlayer && !checkmate){
					boolean useCurrentPieces = random.nextBoolean();
					String code = "";
			
					if (useCurrentPieces){
						chatGPTPieces = new ArrayList<int[]>();
						for (int i = 0; i < 8; i++){
							for (int j = 0; j < 8; j++){
								if (!Board.currentBoard[i][j].piece.isWhite){
									chatGPTPieces.add(new int[]{i, j});
								}
							}
						}
						int index = (int) (Math.random()*chatGPTPieces.size());
						code = Board.currentBoard[chatGPTPieces.get(index)[0]][chatGPTPieces.get(index)[1]].piece.codeValue;
						Board.currentBoard[chatGPTPieces.get(index)[0]][chatGPTPieces.get(index)[1]].piece = new Piece("e", "e");
						
					} else {
						int randex = (int) (Math.random()*Piece.pieceCodeValues.length())+1;
						code = Piece.pieceCodeValues.substring(randex-1, randex);
					}
					
					int randRow = (int) (Math.random()*8), randCol = (int) (Math.random()*8);
					Board.currentBoard[randRow][randCol].piece = new Piece(code, "b");
				} else if (!checkmate){
					while (!whiteToMove){
						Util.clear();
						Board.printBoard();

						in = new Scanner(System.in);
						while(System.in.available() > 0) {
							System.in.read(new byte[System.in.available()]);
						}

						Util.unlock();
						System.out.print(Colors.BLACK+Colors.BOLD+"Black to Move: "+Colors.CLEAR+Colors.UNDERLINE+Colors.RANDOM()+Colors.BOLD);
						String move = in.nextLine().replace("x", "");
						Util.lock();
						if (Logic.checkMove(move, whiteToMove)){
							int[] oldVals = Logic.getOldVals();
							Board.updateBoard(oldVals[0], oldVals[1], move, whiteToMove);
							whiteToMove = true;
						} else {
							System.out.println(Colors.CLEAR+Colors.RED+Colors.BOLD+"This is an invalid move!"+Colors.CLEAR);
							Util.wait(3.0);
						}
					}
				}

				if (!checkmate){
					checkmate = Logic.checkForMate();
				}
			}

		} else {
			Board.genBoard();
			boolean whiteIsPlayer = true, checkmate = false;

			while (!checkmate){
				while (whiteIsPlayer){
					Util.clear();
					Board.printBoard();

					in = new Scanner(System.in);
					while(System.in.available() > 0) {
						System.in.read(new byte[System.in.available()]);
					}

					Util.unlock();
					System.out.print(Colors.WHITE+Colors.BOLD+"White to Move: "+Colors.CLEAR+Colors.UNDERLINE+Colors.RANDOM()+Colors.BOLD);
					String move = in.nextLine().replace("x", "");
					Util.lock();
					if (Logic.checkMove(move, whiteIsPlayer)){
						int[] oldVals = Logic.getOldVals();
						Board.updateBoard(oldVals[0], oldVals[1], move, whiteIsPlayer);
						whiteIsPlayer = false;
					} else {
						System.out.println(Colors.CLEAR+Colors.RED+Colors.BOLD+"This is an invalid move!"+Colors.CLEAR);
						Util.wait(3.0);
					}
				}

				checkmate = Logic.checkForMate();

				while (!whiteIsPlayer && !checkmate){
					Util.clear();
					Board.printBoard();

					in = new Scanner(System.in);
					while(System.in.available() > 0) {
						System.in.read(new byte[System.in.available()]);
					}

					Util.unlock();
					System.out.print(Colors.BLACK+Colors.BOLD+"Black to Move: "+Colors.CLEAR+Colors.UNDERLINE+Colors.RANDOM()+Colors.BOLD);
					String move = in.nextLine().replace("x", "");
					Util.lock();
					if (Logic.checkMove(move, whiteIsPlayer)){
						int[] oldVals = Logic.getOldVals();
						Board.updateBoard(oldVals[0], oldVals[1], move, whiteIsPlayer);
						whiteIsPlayer = true;
					} else {
						System.out.println(Colors.CLEAR+Colors.RED+Colors.BOLD+"This is an invalid move!"+Colors.CLEAR);
						Util.wait(3.0);
					}
				}

				if (!checkmate){
					checkmate = Logic.checkForMate();

				}
			}
		}
	}

	private static void options(Scanner in) throws Exception {
		boolean running = true, firstShow = true;

		while (running){
			Util.clear();
			String[] settings = {"ChatGPT Mode", "Timer (Minutes)", "Quit"};
			if (firstShow){
				Util.typewriter(Colors.BOLD+Colors.RANDOM()+"--{{ Options }}--\n"+Colors.CLEAR,0.01, true, true);
				for (int i = 1; i <= settings.length; i++){
					Util.typewriter(Colors.BOLD + Colors.RANDOM() + "{" + i + "} " + settings[i-1] + ": ", 0.01, true, false);
					if (chatGPT && i == 1){
						System.out.print(Colors.GREEN);
					} else if (i == 1){
						System.out.print(Colors.RED);
					} else {
						System.out.print(Colors.YELLOW);
					}
					System.out.println("["+settingVals[i-1]+"]");
				}
			} else {
				System.out.println(Colors.BOLD+Colors.RANDOM()+"--{{ Options }}--\n"+Colors.CLEAR);
				for (int i = 1; i <= settings.length; i++){
					System.out.print(Colors.BOLD + Colors.RANDOM() + "{" + i + "} " + settings[i-1] + ": ");
					if (chatGPT && i == 1){
						System.out.print(Colors.GREEN);
					} else if (i == 1){
						System.out.print(Colors.RED);
					} else {
						System.out.print(Colors.YELLOW);
					}
					System.out.println("["+settingVals[i-1]+"]");
				}
			}
			firstShow = false;
			
			Util.divider(false, true);
			System.out.print(Colors.RANDOM() + Colors.BOLD + "Edit Option >>> | " + Colors.UNDERLINE);

			in = new Scanner(System.in);
			while(System.in.available() > 0) {
				System.in.read(new byte[System.in.available()]);
			}

			Util.unlock();
			String optionInput = in.nextLine().toLowerCase();
			Util.lock();

			switch (optionInput){
				case "chatgpt": case "1": case "c": case "chat": case "gpt":
					System.out.println(Colors.CLEAR + Colors.BOLD + Colors.YELLOW +"[!!] ChatGPT mode has been toggled! [!!]" + Colors.CLEAR);
					chatGPT = !chatGPT;
					updateVals();
					Util.wait(3.0);
					break;
		
				case "timer": case "2": case "o":
					boolean needValidTime = true;
					while (needValidTime){
						try{
							System.out.print(Colors.CLEAR + Colors.RANDOM() + Colors.BOLD + "Input new timer value >>> | " + Colors.UNDERLINE);
							
							in = new Scanner(System.in);
							while(System.in.available() > 0) {
								System.in.read(new byte[System.in.available()]);
							}

							Util.unlock();
							String valueInput = in.nextLine().toLowerCase();
							Util.lock();

							timer = Integer.parseInt(valueInput);
							System.out.println(Colors.CLEAR + Colors.BOLD + Colors.YELLOW +"[!!] Timer has been adjusted, but timer is not used lmao :P [!!]" + Colors.CLEAR);
							needValidTime = false;
							updateVals();
							Util.wait(3.0);
						} catch (Exception realGoofy){
							System.out.println(Colors.CLEAR + Colors.BOLD + Colors.RED+"[!!] Not a valid value! [!!]");
							Util.wait(3.0);
							Util.clear();
						}
					}
					break;

				case "quit": case "3": case "exit": case "q": case "x":
					running = false;
					break;

				default:
					System.out.println(Colors.RED + "[!!] Not a valid option! [!!]");
					Util.wait(3.0);
			}
		}
	}

	private static void updateVals(){
		settingVals = new Object[]{chatGPT, timer, ""};
	}
}