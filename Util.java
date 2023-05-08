public class Util {
	public static void typewriter(String text, double delay, boolean random, boolean newline) throws Exception{
		double actualDelay;
		for (char c : text.toCharArray()){
			if (random){
	        	actualDelay = ((int)(Math.random() * 51) + (delay*1000))/1000;
	        } else {
				actualDelay = delay;
			}
			wait(actualDelay);
			System.out.print(c);
      	}

      	if (newline){
        	System.out.print("\n");
      	}
		lock();
    }
	
	public static void divider(boolean useTypewriter, boolean newLine) throws Exception{
		String divider = Colors.CLEAR + "--------------------------------------------";
		if (useTypewriter){
			typewriter(divider, 500, false, newLine);
		} else {
			System.out.print(divider);
			if (newLine){
				System.out.print("\n");
			}
		}
		lock();
	}

	public static void divider(int delay, boolean random, boolean newLine) throws Exception{
		String divider = Colors.CLEAR + "--------------------------------------------";
		typewriter(divider, delay, random, newLine);
		lock();
	}

	public static void wait(double seconds) throws Exception{
		try{Thread.sleep((int) (seconds*1000));} catch (Exception really){}
		lock();
	}

	public static void clear() throws Exception {
		System.out.print(Colors.CLEAR + "\033[H\033[2J");
		lock();
	}

	public static void lock() throws Exception {
		Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty -echo </dev/tty"});
	}

	public static void unlock() throws Exception {
		Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty echo </dev/tty"});
	}

}