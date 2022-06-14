package puzzle8;

import java.util.*;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Ihr Name
 */
public class A_Star {
	// cost ordnet jedem Board die Aktuellen Pfadkosten (g-Wert) zu.
	// pred ordnet jedem Board den Elternknoten zu. (siehe Skript S. 2-25). 
	// In cost und pred sind genau alle Knoten der closedList und openList enthalten!
	// Nachdem der Zielknoten erreicht wurde, lässt sich aus cost und pred der Ergebnispfad ermitteln.
	private static HashMap<Board,Integer> cost = new HashMap<>();
	private static HashMap<Board,Board> pred = new HashMap<>();
	
	// openList als Prioritätsliste.
	// Die Prioritätswerte sind die geschätzen Kosten f = g + h (s. Skript S. 2-66)
	private static IndexMinPQ<Board, Integer> openList = new IndexMinPQ<Board, Integer>();

	private static Deque<Board> getPath(Board board) {
		Deque<Board> path = new LinkedList<>();
		path.push(board);
		for (Board parent = pred.get(board); parent != null; parent = pred.get(parent)) {
			path.push(parent);
		}
		return path;
	}

	public static Deque<Board> aStar(Board startBoard) {
		Board start = new Board(startBoard.board);
		if (start.isSolved())
			return getPath(start); // new LinkedList<>();
		openList.add(start, start.h2());
		pred.put(start, null);
		cost.put(start, start.h2());
		Deque<Board> closedList = new ArrayDeque<>();

		while (!openList.isEmpty()) {
			Board n = openList.removeMin();
			if (n.isSolved()) return getPath(n);
			closedList.add(n);
			int aktuelleKosten = cost.get(n);
			for (Board child: n.possibleActions()){
				int childKosten = aktuelleKosten + 1;
				int h = child.h2();
				if (openList.get(child) == null && !closedList.contains(child)) {
					openList.add(child, h + childKosten); // Gesamt Vorherige Kosten dazurechnen!!
					pred.put(child, n);
					cost.put(child, childKosten);
				} else if (childKosten < cost.get(child)) {
					openList.change(child, childKosten + h);
					pred.replace(child, n);
					cost.replace(child, childKosten);
				}
			}
		}
		
		return null; // Keine Lösung
	}
}
