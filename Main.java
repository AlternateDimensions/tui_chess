import java.util.Scanner;

public class Main {
	private static boolean chatGPT = false;
	private static int timer = 5;
	private static Object[] settingVals = {chatGPT, timer, ""};
	private static Scanner in;


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
					Util.typewriter(Colors.BOLD + Colors.RANDOM() + "{" + i + "} " + options[i-1], 10, true, true);
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
		chatGPT = true;
		if (chatGPT){
			boolean whiteIsPlayer = (int) (Math.random()*2) == 1 ? true:false, checkmate = false;
			whiteIsPlayer = true; // TEMP
			Util.clear();
			Board.genBoard();
			
			while (!checkmate){
				boolean whiteToMove = true;
				// White to move
				if (whiteIsPlayer){
					while (whiteToMove){
						Board.printBoard();

						in = new Scanner(System.in);
						while(System.in.available() > 0) {
							System.in.read(new byte[System.in.available()]);
						}

						Util.unlock();
						System.out.print(Colors.RANDOM()+Colors.BOLD+"White to Move: "+Colors.CLEAR+Colors.UNDERLINE+Colors.RANDOM()+Colors.BOLD);
						String move = in.nextLine();
						Util.lock();
						if (Logic.checkMove(move, true)){
							Board.updateBoard(move);
							whiteToMove = false;
						} else {
							System.out.println(Colors.CLEAR+Colors.RED+Colors.BOLD+"This is an invalid move!"+Colors.CLEAR);
							Util.wait(3.0);
							Util.clear();
						}
					}
				} else {
					// Gather all pieces
					// Pick between creating a piece
					// Pick random square
				}

				// Black to move
				if (whiteIsPlayer){
					// Gather all pieces
					// Pick between creating a piece
					// Pick random square
					// Randomly decide to castle?
				} else {
					while (!whiteToMove){
						Board.printBoard();

						in = new Scanner(System.in);
						while(System.in.available() > 0) {
							System.in.read(new byte[System.in.available()]);
						}

						Util.unlock();
						System.out.print(Colors.RANDOM()+Colors.BOLD+"Black to Move: "+Colors.CLEAR+Colors.UNDERLINE+Colors.RANDOM()+Colors.BOLD);
						String move = in.nextLine();
						Util.lock();
						if (Logic.checkMove(move, false)){
							Board.updateBoard(move);
							whiteToMove = true;
						} else {
							System.out.println(Colors.CLEAR+Colors.RED+Colors.BOLD+"This is an invalid move!"+Colors.CLEAR);
							Util.wait(3.0);
							Util.clear();
						}
					}
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
				Util.typewriter(Colors.BOLD+Colors.RANDOM()+"--{{ Options }}--\n"+Colors.CLEAR,10, true, true);
				for (int i = 1; i <= settings.length; i++){
					Util.typewriter(Colors.BOLD + Colors.RANDOM() + "{" + i + "} " + settings[i-1] + ": ", 10, true, false);
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
							System.out.println(Colors.CLEAR + Colors.BOLD + Colors.YELLOW +"[!!] Timer has been adjusted! [!!]" + Colors.CLEAR);
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