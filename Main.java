import java.util.Scanner;

public class Main {
	private static boolean chatGPT = false;
	private static int timer = 5;
	private static Object[] settingVals = {chatGPT, timer, ""};
	private static Scanner in;


	public static void main (String[] args) {
		String[] options = {"PLAY", "OPTIONS", "QUIT"};

		Util.clear();
		Title.printTitle();
		
		boolean running = true;

		while (running){
			for (int i = 1; i <= options.length; i++){
				Util.typewriter(Colors.BOLD + Colors.RANDOM() + "{" + i + "} " + options[i-1], 10, true, true);
			}
			Util.divider(false, true);
			
			System.out.print(Colors.RANDOM() + Colors.BOLD + "Select Option >>> | " + Colors.UNDERLINE);

			try{
				in = new Scanner(System.in);
				while(System.in.available() > 0) {
					System.in.read(new byte[System.in.available()]);
				}
			} catch (Exception aaaaa){}

			String input = in.nextLine().toLowerCase();

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

	private static void play(){
		
	}

	private static void options(Scanner in){
		boolean running = true;

		while (running){
			Util.clear();
			String[] settings = {"ChatGPT Mode", "Timer (Minutes)", "Quit"};
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
			
			Util.divider(false, true);
			System.out.print(Colors.RANDOM() + Colors.BOLD + "Edit Option >>> | " + Colors.UNDERLINE);

			try{
				in = new Scanner(System.in);
				while(System.in.available() > 0) {
					System.in.read(new byte[System.in.available()]);
				}
			} catch (Exception aaaaa){}

			String optionInput = in.nextLine().toLowerCase();
			
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
							
							try{
								in = new Scanner(System.in);
								while(System.in.available() > 0) {
									System.in.read(new byte[System.in.available()]);
								}
							} catch (Exception aaaaa){}

							String valueInput = in.nextLine().toLowerCase();

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
			in.close();
			}
		}
	}

	private static void updateVals(){
		settingVals = new Object[]{chatGPT, timer, ""};
	}
}