public class Util {
	public static void typewriter(String text, int delay, boolean random, boolean newline){
		int actualDelay;
		for (char c : text.toCharArray()){
			if (random){
	        	actualDelay = (int) ((Math.random() * 101)) + delay;
	        } else {
				actualDelay = delay;
			}

        	try{Thread.sleep(actualDelay);} catch (Exception goofy){}
			
			System.out.print(c);
      	}

      	if (newline){
        	System.out.print("\n");
      	}
    }
	
	public static void divider(boolean useTypewriter, boolean newLine){
		String divider = Colors.CLEAR + "--------------------------------------------";
		if (useTypewriter){
			typewriter(divider, 500, false, newLine);
		} else {
			System.out.print(divider);
			if (newLine){
				System.out.print("\n");
			}
		}
	}

	public static void divider(int delay, boolean random, boolean newLine){
		String divider = Colors.CLEAR + "--------------------------------------------";
		typewriter(divider, delay, random, newLine);
	}

	public static void wait(double seconds){
		try{Thread.sleep((int) (seconds*1000));} catch (Exception really){}
	}

	public static void clear(){
		System.out.print(Colors.CLEAR + "\033[H\033[2J");
	  }
}
