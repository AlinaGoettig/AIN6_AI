package puzzle8;

import java.util.Deque;

/**
 * Hauptprogramm für 8-Puzzle-Problem.
 * @author Ihr Name
 */
public class Puzzle8 {
	
	public static void main(String[] args) {
		Board b = new Board(); // Loesbares Puzzle b zufällig generieren.
		// Board b = new Board(new int[]{7,2,4,5,0,6,8,3,1});
		// Board b = new Board(new int[]{3,7,6,0,4,8,2,1,5}); // Nicht lösbar
		System.out.println(b);
		System.out.println("Parity:" + b.parity());

		Deque<Board> res = A_Star.aStar(b);
		int n = res == null ? -1 : res.size();
		System.out.println("Anz.Zuege: " + n + ": " + res);

		res = IDFS.idfs(b);
		n = res == null ? -1 : res.size();
		System.out.println("Anz.Zuege: " + n + ": " + res);
	}
}
