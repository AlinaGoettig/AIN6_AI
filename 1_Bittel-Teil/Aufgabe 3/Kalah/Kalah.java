package kalah;

/**
 * Hauptprogramm für KalahMuster.
 * @since 29.3.2021
 * @author oliverbittel
 */
public class Kalah {
	
	private static final String ANSI_BLUE = "\u001B[34m";

	/**
	 *
	 * @param args wird nicht verwendet.
	 */
	public static void main(String[] args) {
		//testExample();
		testHHGame();
	}
	
	/**
	 * Beispiel von https://de.wikipedia.org/wiki/Kalaha
	 */
	public static void testExample() { 
		KalahBoard kalahBd = new KalahBoard(new int[]{5,3,2,1,2,0,0,4,3,0,1,2,2,0}, 'B');
		kalahBd.print();
		
		System.out.println("B spielt Mulde 11");
		kalahBd.move(11);
		kalahBd.print();
		
		System.out.println("B darf nochmals ziehen und spielt Mulde 7");
		kalahBd.move(7);
		kalahBd.print();
	}

	/*
		count b): 123351, 136182, 88951
			Spiele: 5
			AI Siege: 2

		count c): 2139, 2837, 2565
			Spiele: 3
			AI Siege: 0

		count d):
			Durchschnittliche Counts: 1935, 2422, 2255
			Spiele: 14
			AI Siege: 6
	 */
	/**
	 * Mensch gegen Mensch
	 */
	public static void testHHGame() {
		KalahBoard kalahBd = new KalahBoard();
		kalahBd.setVersion(3);
		kalahBd.print();

		while (!kalahBd.isFinished()) {
			int action = 0;
			if (kalahBd.getCurPlayer() == kalahBd.APlayer) {
				action = kalahBd.readAction();
			} else {
				KalahBoard brett = kalahBd.MaxAction(7);
				action = brett.getLastPlay();
				System.out.print(ANSI_BLUE + kalahBd.getCurPlayer() + " spielt Mulde: " + action + "\n");
			}
			kalahBd.move(action);
			kalahBd.print();
		}

		System.out.println("\n" + ANSI_BLUE + "GAME OVER - Player " + kalahBd.winner() + " wins!");
		System.out.println("\n Es wurden durchschnittlich " + kalahBd.countCalcs() + " Züge berechnet.");
	}
}
