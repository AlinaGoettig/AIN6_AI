package puzzle8;

import java.util.*;

/**
 * Klasse IDFS für iterative deepening depth-first search
 * @author Ihr Name
 */
public class IDFS {

	private static Deque<Board> dfs(Board curBoard, Deque<Board> path, int limit) {
		if (curBoard.isSolved()){
			return path; // Lösung
		} else if (limit == 0) {
			return path; // Maximal zulässige Rekursionstiefe erreicht
		} else {
			boolean cutOffOccurred = false;
			List<Board> possibleMoves = curBoard.possibleActions();
			for (Board child: possibleMoves) {
				// System.out.println(child + "\n");
				if (path.contains(child)) continue;
				path.add(child);
				Deque<Board> result = dfs(child, path, limit - 1);
				if (result != null) {
					if (result.getLast().equals(curBoard.solution())) return result;
				}
				if (result == path) cutOffOccurred = true;
				path.removeLast();
			}
			return cutOffOccurred ? path : null;
		}
	}
	
	private static Deque<Board> idfs(Board curBoard, Deque<Board> path) {
		for (int limit = 1; limit < Integer.MAX_VALUE; limit++) {
			Deque<Board> result = dfs(curBoard,path,limit);
			if (result != null && result.contains(curBoard.solution()))
				return result;
		}
		return null;
	}
	
	public static Deque<Board> idfs(Board curBoard) {
		Deque<Board> path = new LinkedList<>();
		path.addLast(curBoard);
		Deque<Board> res =  idfs(curBoard, path); 
		return res;
	}
}
