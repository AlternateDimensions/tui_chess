public class Title {
	private static String one =   "████████╗██╗░░░██╗██╗░░░░░░░█████╗░██╗░░██╗███████╗░██████╗░██████╗";
	private static String two =   "╚══██╔══╝██║░░░██║██║░░░░░░██╔══██╗██║░░██║██╔════╝██╔════╝██╔════╝";
	private static String three = "░░░██║░░░██║░░░██║██║█████╗██║░░╚═╝███████║█████╗░░╚█████╗░╚█████╗░";
	private static String four =  "░░░██║░░░██║░░░██║██║╚════╝██║░░██╗██╔══██║██╔══╝░░░╚═══██╗░╚═══██╗";
	private static String five =  "░░░██║░░░╚██████╔╝██║░░░░░░╚█████╔╝██║░░██║███████╗██████╔╝██████╔╝";
	private static String six =   "░░░╚═╝░░░░╚═════╝░╚═╝░░░░░░░╚════╝░╚═╝░░╚═╝╚══════╝╚═════╝░╚═════╝░";
	
	public static void printTitle() throws Exception {
		Util.clear();
		System.out.print("\n");
		String sOne = Colors.RANDOM(), sTwo = Colors.RANDOM(), sThree = Colors.RANDOM(), sFour = Colors.RANDOM(), sFive = Colors.RANDOM(), sSix = Colors.RANDOM();

		for (int i = 0; i < one.length(); i++){
			sOne += one.charAt(i);
			sTwo += two.charAt(i);
			sThree += three.charAt(i);
			sFour += four.charAt(i);
			sFive += five.charAt(i);
			sSix += six.charAt(i);

			System.out.println(sOne);
			System.out.println(sTwo);
			System.out.println(sThree);
			System.out.println(sFour);
			System.out.println(sFive);
			System.out.println(sSix);
			
			System.out.print("\033[6A");
			Util.wait(0.05);
		}
		System.out.println("\n".repeat(6)+Colors.RANDOM()+Colors.ITALIC+Colors.UNDERLINE+"-| Yet another terrible port of Chess | By Frank Norris | v0.0.2 ALPHA |-\n"+Colors.CLEAR);
	}

	public static void printTitleStatic() throws Exception {
		Util.clear();
		System.out.print("\n");
		String sOne = Colors.RANDOM(), sTwo = Colors.RANDOM(), sThree = Colors.RANDOM(), sFour = Colors.RANDOM(), sFive = Colors.RANDOM(), sSix = Colors.RANDOM();

		for (int i = 0; i < one.length(); i++){
			sOne += one.charAt(i);
			sTwo += two.charAt(i);
			sThree += three.charAt(i);
			sFour += four.charAt(i);
			sFive += five.charAt(i);
			sSix += six.charAt(i);

			System.out.println(sOne);
			System.out.println(sTwo);
			System.out.println(sThree);
			System.out.println(sFour);
			System.out.println(sFive);
			System.out.println(sSix);
			
			System.out.print("\033[6A");
		}
		System.out.println("\n".repeat(6)+Colors.RANDOM()+Colors.ITALIC+Colors.UNDERLINE+"-| Yet another terrible port of Chess | By Frank Norris | v0.0.2 ALPHA |-\n"+Colors.CLEAR);
	}
}