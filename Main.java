import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main {
	private static Object[] settingVals = {false, 5, ""};
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
				Title.printTitleStatic();
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
					options();
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
		boolean whiteIsWinner = true, checkmate = false, whiteIsPlayer = true;

		Board.genBoard();

		if ((boolean) settingVals[0]){ // ChatGPT mode toggle check
			whiteIsPlayer = random.nextBoolean();

			while (!checkmate){ // While not in checkmate
				boolean validMove = false;

				while (!validMove){
					Util.clear();
					Board.printBoard();

					in = new Scanner(System.in);
					while(System.in.available() > 0) {
						System.in.read(new byte[System.in.available()]);
					}

					Util.unlock();
					if (whiteIsPlayer){
						System.out.print(Colors.WHITE+Colors.BOLD+"White to Move: "+Colors.CLEAR+Colors.UNDERLINE+Colors.RANDOM()+Colors.BOLD);
					} else {
						System.out.print(Colors.BLACK+Colors.BOLD+"Black to Move: "+Colors.CLEAR+Colors.UNDERLINE+Colors.RANDOM()+Colors.BOLD);
					}
					String move = in.nextLine().replace("x", "");
					Util.lock();

					if (Logic.checkMove(move, whiteIsPlayer)){
						if (move.equals("0-0-0")){
							Logic.castleStatusLong[0] = false;
							Board.castle(whiteIsPlayer, true);

						} else if (move.equals("0-0")){
							Logic.castleStatusShort[0] = false;
							Board.castle(whiteIsPlayer, false);

						} else {
							Board.updateBoard(Logic.oldVals[0], Logic.oldVals[1], Logic.newVals[0], Logic.newVals[1], whiteIsPlayer);
						}
						validMove = true;
					} else {
						System.out.println(Colors.CLEAR+Colors.RED+Colors.BOLD+"This is an invalid move!"+Colors.CLEAR);
						Util.wait(3.0);
					}
				}

				checkmate = Logic.checkForMate(true);

				if (checkmate){
					whiteIsWinner = true;
					break;
				}

				chatGPTMove(!whiteIsPlayer);

				checkmate = Logic.checkForMate(false);

				if (checkmate){
					whiteIsWinner = false;
				}
			}	
		} else {
			while (!checkmate){ // While not in checkmate
				boolean validMove = false;

				while (!validMove){
					Util.clear();
					Board.printBoard();

					in = new Scanner(System.in);
					while(System.in.available() > 0) {
						System.in.read(new byte[System.in.available()]);
					}

					Util.unlock();
					if (whiteIsPlayer){
						System.out.print(Colors.WHITE+Colors.BOLD+"White to Move: "+Colors.CLEAR+Colors.UNDERLINE+Colors.RANDOM()+Colors.BOLD);
					} else {
						System.out.print(Colors.BLACK+Colors.BOLD+"Black to Move: "+Colors.CLEAR+Colors.UNDERLINE+Colors.RANDOM()+Colors.BOLD);
					}
					String move = in.nextLine().replace("x", "");
					Util.lock();

					if (Logic.checkMove(move, whiteIsPlayer)){
						if (move.equals("0-0-0")){
							Logic.castleStatusLong[0] = false;
							Board.castle(whiteIsPlayer, true);

						} else if (move.equals("0-0")){
							Logic.castleStatusShort[0] = false;
							Board.castle(whiteIsPlayer, false);

						} else {
							Board.updateBoard(Logic.oldVals[0], Logic.oldVals[1], Logic.newVals[0], Logic.newVals[1], whiteIsPlayer);
						}
						validMove = true;
					} else {
						System.out.println(Colors.CLEAR+Colors.RED+Colors.BOLD+"This is an invalid move!"+Colors.CLEAR);
						Util.wait(3.0);
					}
				}

				checkmate = Logic.checkForMate(whiteIsPlayer);
				if (checkmate){
					whiteIsWinner = whiteIsPlayer;
					break;
				}
				whiteIsPlayer = !whiteIsPlayer; validMove = false;
			}	
		}

		Util.clear();
		Board.printBoard();

		String str = whiteIsWinner? Colors.WHITE+"WHITE wins! (1-0)" : "\033[47m"+Colors.BLACK+"BLACK wins! (0-1)";
		System.out.println(str);
		Util.wait(2.0);

		in = new Scanner(System.in);
		while(System.in.available() > 0) {
			System.in.read(new byte[System.in.available()]);
		}

		Util.unlock();
		System.out.print(Colors.BOLD+"Enter anything to continue >> ");
		in.nextLine();
		Util.lock();
	}

	private static void options() throws Exception {
		boolean running = true, firstShow = true;

		while (running){
			Util.clear();
			String[] settings = {"ChatGPT Mode", "Timer (Minutes)", "Quit"};
			if (firstShow){
				Util.typewriter(Colors.BOLD+Colors.RANDOM()+"--{{ Options }}--\n"+Colors.CLEAR,0.01, true, true);
				for (int i = 1; i <= settings.length; i++){
					Util.typewriter(Colors.BOLD + Colors.RANDOM() + "{" + i + "} " + settings[i-1] + ": ", 0.01, true, false);
					if ((boolean) settingVals[0] && i == 1){
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
					if ((boolean) settingVals[0] && i == 1){
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
					settingVals[0] = !(boolean) settingVals[0];
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

							settingVals[1] = Integer.parseInt(valueInput);
							System.out.println(Colors.CLEAR + Colors.BOLD + Colors.YELLOW +"[!!] Timer has been adjusted, but timer is not used lmao :P [!!]" + Colors.CLEAR);
							needValidTime = false;
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

	private static void chatGPTMove(boolean isWhite){
		boolean usePieces = random.nextBoolean();

		String sideCode = "";
		if (isWhite){
			sideCode = "w";
		} else {
			sideCode = "b";
		}
		
		int randomFile = (int) (Math.random() * 8), randomRow = (int) (Math.random() * 8);

		String codeValue = "";

		if (usePieces){
			ArrayList<int[]> chatGPTPieces = new ArrayList<int[]>();

			for (Square[] r : Board.currentBoard){
				for (Square sq : r){
					if (sq.piece.isWhite == isWhite){
						chatGPTPieces.add(new int[]{sq.index[0], sq.index[1]});
					}
				}
			}

			int randex = (int) (Math.random() * chatGPTPieces.size());
			
			codeValue = Board.currentBoard[chatGPTPieces.get(randex)[0]][chatGPTPieces.get(randex)[1]].piece.codeValue;

		} else {
			int randomPiece = (int) (Math.random() * 6);
			codeValue = Piece.pieceCodeValues.substring(randomPiece, randomPiece+1);
		}
		Board.currentBoard[randomRow][randomFile].piece = new Piece(new int[]{randomRow, randomFile}, sideCode+codeValue);
	}
}
